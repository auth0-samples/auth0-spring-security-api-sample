# Authentication

This example shows how to secure your Java Spring Security API endpoints using Auth0-issued JSON Web Tokens.

You can read a quickstart for this sample [here](https://auth0.com/docs/quickstart/backend/java-spring-security).


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

## Test the API

To run a request against the exposed API endpoints, simply make GET or POST requests as follows (using any http client you choose).

Key Point: Remember to include the `Authorization: Bearer {{YOUR JWT TOKEN}}"` header. You can generate a JWT perhaps easiest by downloading
a web client sample from the Auth0 Dashboard for the same application you defined above, and then by logging using that App and retrieving the
generated JWT token that way.

Make sure that the `scope` includes the following as a bare minimum:  `openid roles`  - without `roles` your JWT will contain no roles information
regardless of what is associated with the UserProfile itself.

Here is a snippet of code for Lock, roughly how this should look:

```
var lock = new Auth0Lock('${clientId}', '${domain}');
lock.showSignin({
    authParams: {
        scope: 'openid roles user_id name email'
    },
    // ... other params
});
```

Here are some examples of the available endpoints using CURL from the command line instead.
For the secured endpoints, please ensure you replace {{jwt_token}} with your JWT Token value.


### Public endpoint:

```
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" "http://localhost:3001/ping"
```

### Secured endpoints:

```
curl -X GET -H "Authorization: Bearer {{jwt_token}}" -H "Cache-Control: no-cache" "http://localhost:3001/api/v1/profiles"
```

or

```
curl -X GET -H "Authorization: Bearer {{jwt_token}}" -H "Cache-Control: no-cache" "http://localhost:3001/api/v1/profiles/1"
```

or

```
curl -X POST -H "Authorization: Bearer {{jwt_token}}" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
    "name": "Chuck",
    "email": "chuck@communications.com"
 }' "http://localhost:3001/api/v1/profiles"
```

or

```
curl -X PUT -H "Authorization: Bearer {{jwt_token}}" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
    "email": "bob@youwerehacked.com"
 }' "http://localhost:3001/api/v1/profiles/1"
```

or

```
curl -X DELETE -H "Authorization: Bearer {{jwt_token}}" -H "Cache-Control: no-cache" "http://localhost:3001/api/v1/profiles/2"
```
