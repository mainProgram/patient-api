pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6' // Nom défini dans Jenkins > Global Tool Configuration
    }

    environment {
        SONARQUBE_ENV = 'SonarQube' // Nom défini dans Jenkins > Configure System
        SONAR_TOKEN = credentials('sonar-token') // ID du Secret Text contenant le token Sonar
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/mainProgram/patient-api.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh "./mvnw sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }
    }
}
