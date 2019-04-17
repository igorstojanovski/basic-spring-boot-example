pipeline {
  agent any
  stages {
    stage('Say Hello!') {
      parallel {
        stage('Say Hello!') {
          steps {
            echo 'Hello World!'
          }
        }
        stage('Check env') {
          steps {
            sh 'echo ${JAVA_HOME}'
          }
        }
      }
    }
  }
}