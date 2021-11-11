### How to start?

0) run Docker!  

1) create network
> docker network create CONTEST_NETWORK  

2) use jib and create each service as image
> task jibDockerBuild  

3) run docker compose 
> docker compose up  

4) check actuator: 
> http://localhost:9001/actuator  
> http://localhost:9002/actuator  

5) profit!