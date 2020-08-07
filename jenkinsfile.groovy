
pipeline {
    agent {label "ubuntu"
          }//end of agent

    stages {
        stage('Anchore Engine Setup') {
            steps {
                echo 'Installing Anchore Engine'
                sh '''
                
                mkdir ~/aevolume
                cd ~/aevolume

                docker pull docker.io/anchore/anchore-engine:latest
                docker create --name ae docker.io/anchore/anchore-engine:latest
                docker cp ae/docker-compose.yaml ~/aevolume/docker-compose.yaml
                docker rm ae

                docker-compose pull
                docker-compose up -d
                
                '''
            } //end of steps
        } //end of stage build

    } //end of stages
} //end of pipeline
