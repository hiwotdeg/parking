pipeline {
    agent any

    environment {
        HARBOR_URL = credentials('harbor-registry-url')        // Harbor registry URL from Jenkins credentials store
        HARBOR_USERNAME = credentials('harbor-credentials-username')  // Username for Harbor
        HARBOR_PASSWORD = credentials('harbor-credentials-password')  // Password for Harbor
        DOCKER_TAG = 'latest'   // Default tag for the images
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm  // Checkout the code from the repository
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo 'Building Docker images using Jib...'
                    // Build all Docker images for the project using Jib
                    sh 'mvn compile jib:dockerBuild'  // Builds images for all services configured in the project
                }
            }
        }

        stage('Tag and Push to Harbor') {
            steps {
                script {
                    echo 'Tagging and pushing Docker images to Harbor registry...'
                    // Explicitly tag and push each service to Harbor
                    sh "docker tag api-gateway ${HARBOR_URL}/kft-lab/api-gateway:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/api-gateway:${DOCKER_TAG}"

                    sh "docker tag auth-service ${HARBOR_URL}/kft-lab/auth-service:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/auth-service:${DOCKER_TAG}"

                    sh "docker tag geo-location-service ${HARBOR_URL}/kft-lab/geo-location-service:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/geo-location-service:${DOCKER_TAG}"

                    sh "docker tag notification-service ${HARBOR_URL}/kft-lab/notification-service:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/notification-service:${DOCKER_TAG}"

                    sh "docker tag parking-lot-service ${HARBOR_URL}/kft-lab/parking-lot-service:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/parking-lot-service:${DOCKER_TAG}"

                    sh "docker tag payment-service ${HARBOR_URL}/kft-lab/payment-service:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/payment-service:${DOCKER_TAG}"

                    sh "docker tag service-discovery ${HARBOR_URL}/kft-lab/service-discovery:${DOCKER_TAG}"
                    sh "docker push ${HARBOR_URL}/kft-lab/service-discovery:${DOCKER_TAG}"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
            // Any cleanup tasks can go here
        }
    }
}
