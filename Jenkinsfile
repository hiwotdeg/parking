pipeline {
    agent any

    environment {
        HARBOR_URL = credentials('harbor-registry-url')
        HARBOR_USERNAME = credentials('harbor-credentials-username')
        HARBOR_PASSWORD = credentials('harbor-credentials-password')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo 'Building and pushing Docker images using Jib...'
                    sh 'mvn compile jib:dockerBuild'
                }
            }
        }

        stage('Push Images to Harbor') {
            steps {
                script {
                    echo 'Logging in to Harbor and pushing Docker images...'
                    sh "echo ${HARBOR_PASSWORD} | docker login ${HARBOR_URL} --username ${HARBOR_USERNAME} --password-stdin"

                    sh "docker push ${HARBOR_URL}/kft-lab/notification-service"
                    sh "docker push ${HARBOR_URL}/kft-lab/parking-lot-service"
                    sh "docker push ${HARBOR_URL}/kft-lab/payment-service"
                    sh "docker push ${HARBOR_URL}/kft-lab/auth-service"
                    sh "docker push ${HARBOR_URL}/kft-lab/geo-location-service"
                    sh "docker push ${HARBOR_URL}/kft-lab/service-discovery"
                    sh "docker push ${HARBOR_URL}/kft-lab/api-gateway"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo 'Deploying Parking services using Docker Compose...'
                    sh '''
                    cd parking

                    # Login to Harbor to allow Docker Compose to pull images
                    echo "${HARBOR_PASSWORD}" | docker login ${HARBOR_URL} -u ${HARBOR_USERNAME} --password-stdin

                    # Stop and remove any existing containers
                    docker-compose down

                    # Pull the latest images
                    docker-compose pull

                    # Start the application in detached mode
                    docker-compose up -d
                    '''
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
    }
}
