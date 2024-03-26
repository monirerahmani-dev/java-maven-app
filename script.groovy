def buildJar() {
     echo 'building the application...'
     sh 'mvn package'
}

def buildImage() {
    echo 'building the docker image...'
     withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]){
        sh 'docker build -t monirerahmani/tech-app:jma-3.0 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push monirerahmani/tech-app:jma-3.0'
    }   
}
def deployApp() {
    echo "deploy the app"
}
return this
