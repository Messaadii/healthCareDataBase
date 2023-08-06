pipeline {
    agent any
    
    stages {
            stage ('Checkout SCM'){
             steps{
        	git branch : 'main', url:'https://github.com/Messaadii/healthCareDataBase.git'}
        }
        
        stage('Build') {
            steps{
                 bat 'mvn clean package -DskipTests'
        }}
        
         stage ('Build docker image'){
          steps{
        	
        	bat "docker build -t moohamedd/jenkins_test_ci_back:v1 ."
    }}

          stage ('run docker image'){
          steps{
        	
        	bat "docker run -p 8086:8080 -d moohamedd/jenkins_test_ci_back:v1"
    }}

    stage ('Push docker image'){
     steps{
        bat "docker login -u moohamedd -p Sghair123@+"

        bat "docker push moohamedd/jenkins_test_ci_back:v1"
    }}
    
    }
    }
    
    
