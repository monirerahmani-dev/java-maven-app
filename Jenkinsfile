#!/user/bin/env groovy
library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
        remote: 'https://github.com/monirerahmani-dev/jenkins-shared-library.git',
        credentialsId: 'github-credentials'])
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
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }

            }
        }
        stage("build  and push image") {
            steps {
                script {
                    buildImage 'monirerahmani/tech-app:jma-3.0'
                    dockerLogin()
                    dockerPush 'monirerahmani/tech-app:jma-3.0'
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