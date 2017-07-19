# Authentication

This example shows how to secure your Java Spring Security API endpoints using Auth0-issued JSON Web Tokens.

You can read a quickstart for this sample [here](https://auth0.com/docs/quickstart/backend/java-spring-security/01-authorization).

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

## Configure the endpoints

This seed uses two endpoints: `ping` and `secured/ping`. The former will not require authentication, while the later will do.

First we create the controller for our endpoints: `PingController.java`.

```java
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class PingController {

	@RequestMapping(value = "/ping")
	@ResponseBody
	public String ping() {
		return "All good. You DO NOT need to be authenticated to call /ping";
	}

	@RequestMapping(value = "/secured/ping")
	@ResponseBody
	public String securedPing() {
		return "All good. You DO need to be authenticated to call /secured/ping";
	}

}
```

Next we  configure which endpoint is secure and which is not, by editing the `AppConfig.java` file:

```java
@Override
  protected void authorizeRequests(final HttpSecurity http) throws Exception {
      http.authorizeRequests()
        .antMatchers("/ping").permitAll()
        .anyRequest().authenticated();
  }
```

## Build and Run

In order to build and run the project execute:

```sh
mvn spring-boot:run
```

## Test the API

To test the non-secure endpoint send a `GET` request at `http://localhost:3001/login`.

```bash
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" "http://localhost:3001/login"
```

You should get the message: `All good. You DO NOT need to be authenticated to call /login`.

To test the secure endpoint send a `GET` request at `http://localhost:3001/secured/ping`. In this case you will also have to add a valid `access_token` to your request.

```bash
curl -X GET -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:3001/secured/ping"
```

You should get the message: `All good. You can see this because you are Authenticated.`.

To test the endpoints with scope access send a request at `http://localhost:3001/photos` with a valid `access_token`. In this case request method depends on the scope:
* `read:photos` scope - use `GET` request
* `create:photos` scope - use `POST` request
* `update:photos` scope - use `PUT` request
* `delete:photos` scope - use `DELETE` request

For example:

```bash
curl -X GET -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:3001/photos"
```

You should get the message: `All good. You can see this because you are Authenticated with a Token granted the 'read:photos' scope`.

A quick and easy way to obtain an `access_token` is to call the `/oauth/token` endpoint of the Auth0 Authentication API:

```bash
curl --request POST --url 'https://YOUR_AUTH0_DOMAIN/oauth/token' \
  --header 'content-type: application/json' \
  --data '{"grant_type":"client_credentials", "client_id":"{CLIENT_ID}", "client_secret":"{CLIENT_SECRET}", "audience":"{YOUR_API_IDENTIFIER}" }'
```
