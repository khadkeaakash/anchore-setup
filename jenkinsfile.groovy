
pipeline {
    agent {label "ubuntu"
          }//end of agent

    stages {
        stage('Anchore Engine Setup') {
            steps {
                echo 'Installing Anchore Engine'
                sh '''
                
                mkdir ~/aevolume || true
                cd ~/aevolume

                docker pull docker.io/anchore/anchore-engine:latest
                docker create --name ae docker.io/anchore/anchore-engine:latest || true
                ls -al
                docker cp ae/docker-compose.yaml ~/aevolume/docker-compose.yaml || true
                docker rm ae || true

                docker-compose pull
                docker-compose up -d
                
                '''
            } //end of steps
        } //end of stage build

    } //end of stages
} //end of pipeline
