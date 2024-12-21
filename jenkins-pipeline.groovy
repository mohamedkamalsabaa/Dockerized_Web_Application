pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'mohamedkamalsabaa/final-project:latest' // Adjust the Docker image name if needed
        GITHUB_REPO = 'https://github.com/mohamedkamalsabaa/ODC-final-project.git'
        DOCKERHUB_CREDENTIALS = 'dckr_pat_In9lkDf0N7S39MP7f3jR4o0p2LY' // Jenkins credential ID for Docker Hub
        GITHUB_CREDENTIALS = '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' // Jenkins credential ID for GitHub
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Pull code from the new GitHub repository using credentials
                git credentialsId: "${GITHUB_CREDENTIALS}", url: "${GITHUB_REPO}"
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
