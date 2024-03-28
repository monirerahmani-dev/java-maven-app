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
    environment {
        IMAGE_NAME = 'monirerahmani/tech-app:jma-4.0'
    }

    stages{
        stage('build app') {
            steps {
                echo 'building application jar...'
                buildJar()
            }
        }
        stage('build  and push image') {
            steps {
                script {
                    echo 'building the docker image...'
                    buildImage (env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush (env.IMAGE_NAME)
                }

            }
        }
        stage("deploy") {
            steps {
                script {
                    echo 'deploying docker image to EC2...'
                    def dockerCmd = "docker run -p 8080:8080 -d ${IMAGE_NAME}"
                    sshagent(['ec2-server-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.41.162.83 ${dockerCmd}"
                    }
                }
            }               
        }

    }
}
