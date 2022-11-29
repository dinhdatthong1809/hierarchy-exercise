pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-17-slim'
            args '-v /home/thong/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
    }
}