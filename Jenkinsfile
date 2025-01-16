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
                    echo 'Building Docker images using Dockerfiles...'

                    sh "docker build -t ${HARBOR_URL}/kft-lab/notification-service -f notification-service/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/parking-lot-service -f parking-lot-service/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/payment-service -f payment-service/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/auth-service -f auth-service/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/geo-location-service -f geo-location-service/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/service-discovery -f service-discovery/Dockerfile ."
                    sh "docker build -t ${HARBOR_URL}/kft-lab/api-gateway -f api-gateway/Dockerfile ."
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
                    sh "echo \"${HARBOR_PASSWORD}\" | docker login ${HARBOR_URL} -u ${HARBOR_USERNAME} --password-stdin"
                    sh "docker-compose down"
                    sh "docker-compose pull"
                    sh "docker-compose up -d"
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
