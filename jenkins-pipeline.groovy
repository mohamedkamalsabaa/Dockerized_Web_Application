pipeline {
    agent any  // This specifies that the pipeline can run on any available Jenkins agent

    environment {
        // Define environment variables used in the pipeline
        DOCKER_IMAGE = 'mohamedk15/final-project:latest' // Your Docker image name and tag
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials'  // Jenkins credentials ID for Docker Hub login
        GITHUB_REPO = 'git@github.com:mohamedk15/final-project.git'  // GitHub repository URL
    }

    stages {
        // Stage to clone the code from GitHub
        stage('Clone Code') {
            steps {
                // Use Git SCM to pull the code
                git credentialsId: 'github-credentials', url: "${GITHUB_REPO}"
            }
        }

        // Stage to build the Docker image
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the Dockerfile in the repository
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }

        // Stage to push the Docker image to Docker Hub
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Push the built Docker image to Docker Hub
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_HUB_CREDENTIALS}") {
                        docker.image("${DOCKER_IMAGE}").push()
                    }
                }
            }
        }

        // Stage to run the Ansible playbook
        stage('Run Ansible Playbook') {
            steps {
                script {
                    // Run the Ansible playbook to deploy the Docker container on Vagrant machines
                    sh 'ansible-playbook -i inventory.ini deploy-docker.yml'
                }
            }
        }
    }

    post {
        success {
            // Actions to perform if the pipeline is successful
            echo "Pipeline executed successfully!"
        }

        failure {
            // Actions to perform if the pipeline fails
            echo "Pipeline execution failed!"
        }
    }
}
