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

        stage('Unit Tests') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh """
                    ./mvnw sonar:sonar \
                      -Dsonar.projectKey=patient-api \
                      -Dsonar.login=${SONAR_TOKEN} \
                      -Dsonar.host.url=http://sonarqube:9000 \
                      -Dsonar.java.coveragePlugin=jacoco \
                      -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                      -Dsonar.junit.reportPaths=target/surefire-reports/
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé'
        }
        success {
            echo 'Build réussi !'
        }
        failure {
            echo 'Build échoué :('
        }
    }
}