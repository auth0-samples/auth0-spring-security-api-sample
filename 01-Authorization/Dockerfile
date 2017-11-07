FROM maven:3.5-jdk-8-alpine

WORKDIR /usr/src/app

#COPY pom.xml /usr/src/app
#RUN mvn dependency:resolve
COPY . /usr/src/app
#RUN mvn install

CMD ["java", "-jar", "target/auth0-spring-security-api-sample-0.0.1-SNAPSHOT.jar"]
EXPOSE 3001
