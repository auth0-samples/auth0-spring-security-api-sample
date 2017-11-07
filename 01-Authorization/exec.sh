#!/usr/bin/env bash
docker build -t auth0-spring-security-mvc-api .
#docker run -p 3001:3001 -it auth0-spring-security-mvc-api
docker run -p 3001:3001 -it \
       -v "$(pwd)":/usr/src/app -v ~/.test:/root/.m2 \
       auth0-spring-security-mvc-api mvn spring-boot:run
