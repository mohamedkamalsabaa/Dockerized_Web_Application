pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'mohamedk15/final-project:latest' // Updated Docker image name
        GITHUB_REPO = 'https://github.com/mohamedkamalsabaa/ODC-final-project.git' // GitHub repository URL
        DOCKERHUB_CREDENTIALS = 'docker-hub-creds' // Jenkins credential ID for Docker Hub
        GITHUB_CREDENTIALS = 'github-credentials' // Jenkins credential ID for GitHub
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Pull code from GitHub repository using Jenkins credentials
                git credentialsId: "${GITHUB_CREDENTIALS}", url: "${GITHUB_REPO}", branch: 'main'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image using the Dockerfile in the repository
                    sh '''
                    docker build -t ${DOCKER_IMAGE} .
                    '''
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Push Docker image to Docker Hub with credentials
                    withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                        echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                        docker push ${DOCKER_IMAGE}
                        """
                    }
                }
            }
        }

        stage('Run Ansible Playbook') {
            steps {
                script {
                    // Run the Ansible playbook to install Docker on Vagrant machines and deploy the container
                    sh '''
                    ansible-playbook -i vagrant_inventory.ini deploy-docker.yml
                    '''
                }
            }
        }
    }
}
