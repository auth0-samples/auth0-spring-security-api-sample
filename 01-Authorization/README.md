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

This seed uses 3 types of endpoint security:
* `permitAll` - will NOT require authentication
* `authenticated` - will require authentication
* `hasAuthority` - will require authentication with specific scope

First we create the controller for our endpoints: `PhotosController.java`.

```java
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class PhotosController {

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login() {
        return "All good. You DO NOT need to be authenticated to call /login";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    @ResponseBody
    public String getPhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'read:photos' scope";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.POST)
    @ResponseBody
    public String createPhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'create:photos' scope";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.PUT)
    @ResponseBody
    public String updatePhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'update:photos' scope";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.DELETE)
    @ResponseBody
    public String deletePhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'delete:photos' scope";
    }

    @RequestMapping(value = "/**")
    @ResponseBody
    public String anyRequest() {
        return "All good. You can see this because you are Authenticated.";
    }

}
```

Next we configure which endpoint is secure and which is not, by editing the `AppConfig.java` file:

```java
@Override
protected void authorizeRequests(final HttpSecurity http) throws Exception {
    JwtWebSecurityConfigurer
      .forRS256(apiAudience, issuer)
      .configure(http)
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/login").permitAll()
      .antMatchers(HttpMethod.GET, "/photos/**").hasAuthority("read:photos")
      .antMatchers(HttpMethod.POST, "/photos/**").hasAuthority("create:photos")
      .antMatchers(HttpMethod.PUT, "/photos/**").hasAuthority("update:photos")
      .antMatchers(HttpMethod.DELETE, "/photos/**").hasAuthority("delete:photos")
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
