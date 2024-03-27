def gv

pipeline{
    agent any
    tools {
        maven "maven-3.9"
    }
    
    stages{
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("increment version") {
             steps {
                 script {
                      echo 'incrementing app version'
                      sh 'mvn build-helper:parse-version versions:set \
                          -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                          versions:commit'
                      def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                      def version = matcher[0][1]
                      env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                 }
             }
        }

        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
                
            }   
        }
        stage("build image") {
            steps {
                script {
                     echo 'building the docker image...'
                     withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                         sh "docker build -t monirerahmani/tech-app:${IMAGE_NAME} ."
                          sh 'echo $PASS | docker login -u $USER --password-stdin'
                          sh "docker push monirerahmani/tech-app:${IMAGE_NAME}"
                     }
                }
            }   
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()             
                }   
            }   
        }
    }
}
