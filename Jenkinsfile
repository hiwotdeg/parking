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
    }

    post {
        always {
            echo 'Pipeline finished.'
            
        }
    }
}
