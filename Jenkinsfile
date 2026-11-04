def gv
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

        stage('init'){
            steps{

                script {
                    gv = load "script.groovy"
                    echo "test great"
                }
            }
            
        }
        stage('Build') {
            steps {
                script {
                   gv.buildApp() 
                }
                
            }
        }

        stage('Test') {
            when {
                expression { params.RUN_TESTS == true }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    gv.deployApp()
                }
                }
            }
        }
    }
