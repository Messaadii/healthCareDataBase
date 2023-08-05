pipeline {
    agent any
    
    stages {
            stage ('Checkout SCM'){
        	git branch : 'main', url:'https://github.com/Messaadii/healthCareDataBase.git'}
        }
        
        stage('Build') {
           
                 bat 'mvn clean package -DskipTests'
        }
        
         stage ('Build docker image'){
        	
        	bat "docker build -t moohamedd/jenkins_test_ci_back:v1 ."
    }

    stage ('Push docker image'){
        bat "docker login -u moohamedd -p Sghair123@+"

        bat "docker push moohamedd/jenkins_test_ci_back:v1"
    }
    
    }
    
    
