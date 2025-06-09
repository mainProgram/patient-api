pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'
    }

    environment {
        SONARQUBE_ENV = 'SonarQube'
        SONAR_TOKEN = credentials('sonar-token')
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
                sh './mvnw clean verify'  // Cela compile, exécute les tests et génère les rapports JaCoCo
                sh 'ls -la target/site/jacoco/'
                sh 'cat target/site/jacoco/jacoco.xml | head -20 || echo "Le fichier jacoco.xml n\'existe pas"'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
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
                      -Dsonar.junit.reportPaths=target/surefire-reports/ \
                      -Dsonar.test.inclusions=**/*Test.java \
                      -Dsonar.java.binaries=target/classes \
                      -Dsonar.sources=src/main/java \
                      -Dsonar.tests=src/test/java \
                      -Dsonar.verbose=true
                    """
                }
            }
        }
//         stage('Quality Gate') {
//             steps {
//                 timeout(time: 3, unit: 'MINUTES'){
//                     waitForQualityGate abortPipeline: true
//                 }
//             }
//         }
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