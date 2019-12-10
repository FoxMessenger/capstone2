pipeline {
    agent any
    stages {
        stage('build') {
            steps {
              sh '''
                 cd config-server
                 ./mvnw -DskipTests clean compile
                 cd ../eureka-service-registry
                 ./mvnw -DskipTests clean compile
                 cd ../admin-api-service
                 ./mvnw -DskipTests clean compile
                 cd ../customer-service
                 ./mvnw -DskipTests clean compile
                 cd ../product-service
                 ./mvnw -DskipTests clean compile
                 cd ../invoice-service
                 ./mvnw -DskipTests clean compile
                 cd ../level-up-service
                 ./mvnw -DskipTests clean compile
                cd ../retail-api-service
                 ./mvnw -DskipTests clean compile
              '''
            }
        }
        stage('test') {
            steps {
              sh '''
                 cd config-server
                     ./mvnw test
                 cd ../eureka-service-registry
                     ./mvnw test
                  cd ../admin-api-service
                     ./mvnw test                    
                 cd ../customer-service
                     ./mvnw test
                 cd ../product-service
                     ./mvnw test
                 cd ../invoice-service
                     ./mvnw test
                 cd ../level-up-service
                     ./mvnw test
                cd ../retail-api-service
                     ./mvnw test
              '''
            }
        }
        stage('deliver') {
            steps {
              sh '''
                cd config-server
                    ./mvnw -DskipTests install
                cd ../eureka-service-registry
                     ./mvnw -DskipTests install
                cd ../admin-api-service
                     ./mvnw -DskipTests install                
                cd ../customer-service
                    ./mvnw -DskipTests install
                cd ../product-service
                     ./mvnw -DskipTests install
                cd ../invoice-service
                     ./mvnw -DskipTests install
                cd ../level-up-service
                     ./mvnw -DskipTests install
                cd ../retail-api-service
                     ./mvnw -DskipTests install
              '''
            }
        }
    }
}