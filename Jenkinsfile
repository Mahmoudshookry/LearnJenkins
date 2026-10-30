pipeline {
    agent any

    environment {
        
        IMAGE_NAME = 'mahmoudshokry/node-app'
    }

    parameters {
        choice(name: 'VERSION', choices: ['1.0', '2.0', '3.0'], description: 'Version to build and push')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests?')
    }

    stages {
        stage('Build') {
            steps {
                echo "Building ${IMAGE_NAME}:${params.VERSION}..."
                sh "docker build -t ${IMAGE_NAME}:${params.VERSION} ."
            }
        }

        stage('Test') {
            when {
                expression { params.RUN_TESTS == true }
            }
            steps {
                echo "Testing version ${params.VERSION}..."
                sh "docker run --rm ${IMAGE_NAME}:${params.VERSION} node --check app.js"
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo "Pushing ${IMAGE_NAME}:${params.VERSION} to Docker Hub..."
                withCredentials([usernamePassword(credentialsId: 'Docker-Hub credentials',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${IMAGE_NAME}:${params.VERSION}"
                    sh 'docker logout'
                }
            }
        }
    }
}
