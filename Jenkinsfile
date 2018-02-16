pipeline {
  agent any
  stages {
    stage('package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('') {
      steps {
        archiveArtifacts '*.exe, *.dmg, *-shaded.jar'
      }
    }
  }
}