def buildApp() {
echo "Building ${IMAGE_NAME}:${params.VERSION}..."
sh "docker build -t ${IMAGE_NAME}:${params.VERSION} ."
  
}

def testApp() {
echo "Testing version ${params.VERSION}..."
sh "docker run --rm ${IMAGE_NAME}:${params.VERSION} node --check app.js"
  
}

def deployApp(){
  echo "Pushing ${IMAGE_NAME}:${params.VERSION} to Docker Hub..."
  withCredentials([usernamePassword(credentialsId: 'Docker-Hub credentials',
                                usernameVariable: 'DOCKER_USER',
                                passwordVariable: 'DOCKER_PASS')]) {
  sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
  sh "docker push ${IMAGE_NAME}:${params.VERSION}"
  sh 'docker logout'
}


return this
