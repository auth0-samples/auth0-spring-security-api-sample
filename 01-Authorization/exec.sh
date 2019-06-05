#!/usr/bin/env bash
docker build -t auth0-samples/auth0-spring-security-api-01-authorization .
docker run -p 3010:3010 -it auth0-samples/auth0-spring-security-api-01-authorization
