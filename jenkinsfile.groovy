
pipeline {
    agent {label "ubuntu"
          }//end of agent

    stages {
        stage('Anchore Engine Setup') {
            steps {
                echo 'Installing Anchore Engine'
                sh '''
                
                docker container stop $(docker container ls -aq)
                
                docker pull postgres:latest
                hn=$(docker run --rm --name postgresdb -e POSTGRES_PASSWORD=chaklee -d postgres || true)
                
                
                
                
                rm -rf aevolume || true
                mkdir ~/aevolume || true
                cd ~/aevolume

                docker pull docker.io/anchore/anchore-engine:latest
                docker images
                docker run --rm --name ae -e ANCHORE_DB_HOST=$hn -e ANCHORE_DB_PASSWORD=chaklee anchore/anchore-engine:latest 
                docker ps -a
                ls -al
                docker cp ae:/docker-compose.yaml ~/aevolume/docker-compose.yaml || true
                docker rm ae || true

                docker-compose pull
                docker-compose up -d
                
                '''
            } //end of steps
        } //end of stage build

    } //end of stages
} //end of pipeline
