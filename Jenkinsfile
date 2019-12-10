pipeline {
    agent any
    stages {
        stage('build') {
            steps {
              sh '''
                 cd ./config-server
                    ./mvnw -DskipTests clean compile
              '''
            }
        }
        stage('test') {
            steps {
              sh '''
                 cd config-server
                     ./mvnw test
              '''
            }
        }
        stage('deliver') {
            steps {
              sh '''
                cd config-server
                    ./mvnw -DskipTests install
                
              '''
            }
        }
    }
}