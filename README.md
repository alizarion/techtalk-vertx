# Techtalk-vertx

Create CRUD api using vert.x, kotlin, openshift, fabric8

### Prerequisites 

* Openshift preconfigured (oc cli up)
* java 8 
* maven

### S0 Init project with configuration files 

            git checkout tags/S0
            sh scripts/prepare-project.sh
            
### S1 Hello World Verticle 

run main.kt of MainVerticle.kt

            git checkout tags/S1
            
### S2 Hello world 2 verticle and http client 
  
run main.kt of each verticle to launch
            
            git checkout tags/S1   
            
### S3 Hello world 2 verticle through eventbus  


            git checkout tags/S3   
            vertx run  backend/services/src/main/kotlin/io/github/alizarion/backend/services/ServiceVerticle.kt -cluster
            vertx run backend/web/src/main/kotlin/io/github/alizarion/backend/web/MainVerticle.kt -cluster
            
###  S4 deploy our 2 verticle on openshift with docker and oc 

            git checkout tags/S4   
            oc login
            sh scripts/build-with-docker.sh 
            
   
###  S5 deploy our 2 verticle with fabric8

            git checkout tags/S5   
            cd backend
            mvn clean fabric8:deploy

###  S6 implement backend service api using code generation with vert.x @VertxGen & @ProxyGen

            git checkout tags/S7 
            cd backend
            mvn clean fabric8:deploy 
            

###  S7 using kubernetes service discovery to find and use redis 

            git checkout tags/S7  