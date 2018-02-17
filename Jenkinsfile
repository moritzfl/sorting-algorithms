pipeline {
  agent any
  stages {
    stage('package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('error') {
      steps {
        archiveArtifacts 'target/*.exe, target/*.dmg, target/*-shaded.jar'
      }
    }
  }
}