# Starter Seed

The seed is a regular Java Spring Security API, with all the Auth0 dependencies set, but nothing more. It's an empty canvas meant to be filled as you follow along the steps of the [Auth0 Java Spring Security API quickstart](https://auth0.com/docs/quickstart/backend/java-spring-security).

Start by renaming the `auth0.properties.example` file in `src/main/resources` to `auth0.properties` and provide it with your application's domain, client ID, client secret, and issuer.

## Prerequisites

In order to run this example you will need to have Maven installed. You can install Maven with [brew](http://brew.sh/):

```sh
brew install maven
```

Check that your maven version is 3.0.x or above:

```sh
mvn -v
```

## Build and Run

In order to build and run the project execute:

```sh
mvn spring-boot:run
```
