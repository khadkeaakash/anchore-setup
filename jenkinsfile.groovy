
pipeline {
    agent {label "ubuntu"
          }//end of agent

    stages {
        stage('Anchore Engine Setup') {
            steps {
                echo 'Installing Anchore Engine'
                sh '''
                docker volume create anchore-db-volume || true
                
                docker container stop $(docker container ls -aq)
                
                docker pull postgres:9.6.18
                hn=$(docker run --rm --name postgresdb -e POSTGRES_PASSWORD=chaklee -d postgres:9.6.18 || true)
                
                
                
                
                rm -rf aevolume || true
                mkdir ~/aevolume || true
                cd ~/aevolume

                docker pull docker.io/anchore/anchore-engine:latest
                docker images
                docker run --rm --name ae -e ANCHORE_DB_HOST=localhost -e ANCHORE_LOG_LEVEL=DEBUG -e ANCHORE_DB_PASSWORD=chaklee anchore/anchore-engine:latest 
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
