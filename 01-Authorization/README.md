# Authentication

This example shows how to secure your Java Spring Security API endpoints using Auth0-issued JSON Web Tokens.

You can read a quickstart for this sample [here](https://auth0.com/docs/quickstart/backend/java-spring-security/01-authorization).

Start by renaming the `auth0.properties.example` file in `src/main/resources` to `auth0.properties` and provide it with your application's domain and issuer.

## Prerequisites

This example requires Java 8.

## Configure the endpoints

This seed uses three endpoints: `api/public`, `api/private` and `api/private-scoped`. The former will not require authentication, while the latter will do.

First we create the controller for our endpoints: `APIController.java`.

```java
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;

@Controller
@Component
public class APIController {

	@RequestMapping(value = "/api/public", method = RequestMethod.GET, produces = "application/json")
        @ResponseBody
        public String publicEndpoint() {
            return new JSONObject()
                    .put("message", "All good. You DO NOT need to be authenticated to call /api/public.")
                    .toString();
        }

        @RequestMapping(value = "/api/private", method = RequestMethod.GET, produces = "application/json")
        @ResponseBody
        public String privateEndpoint() {
            return new JSONObject()
                    .put("message", "All good. You can see this because you are Authenticated.")
                    .toString();
        }

        @RequestMapping(value = "/api/private-scoped", method = RequestMethod.GET, produces = "application/json")
        @ResponseBody
        public String privateScopedEndpoint() {
            return new JSONObject()
                    .put("message", "All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope")
                    .toString();
        }

}
```

Next we configure which endpoint is secure and which is not, by editing the `AppConfig.java` file:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    JwtWebSecurityConfigurer
            .forRS256(apiAudience, issuer)
            .configure(http)
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/public").permitAll()
            .antMatchers(HttpMethod.GET, "/api/private").authenticated()
            .antMatchers(HttpMethod.GET, "/api/private-scoped").hasAuthority("read:messages");
}
```

## Build and Run

In order to build and run the project execute:

```sh
./gradlew clean bootRun
```

Or on Windows:

```sh
gradlew.bat clean bootRun
```

## Test the API

To test the non-secure endpoint send a `GET` request at `http://localhost:3010/api/public`.

```bash
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" "http://localhost:3010/api/public"
```

You should get the message: `All good. You DO NOT need to be authenticated to call /api/public.`.

To test the secure endpoint send a `GET` request at `http://localhost:3010/api/private`. In this case, you will also have to add a valid `access_token` to your request.

```bash
curl -X GET -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:3010/api/private"
```

You should get the message: `All good. You can see this because you are Authenticated.`.

To test the endpoints with scope access send a `GET` request at `http://localhost:3010/private-scoped` with a valid `access_token` with a scope of `read:messages`.

```bash
curl -X GET -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:3010/api/private-scoped"
```

You should get the message: `All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope`.

## Running the sample with Docker

In order to run the example with docker, you need to have `docker` installed.

You also need to set the client credentials as explained [previously](#authentication).

Execute in command line `sh exec.sh` to run the Docker in Linux, or `.\exec.ps1` to run the Docker in Windows.

## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders),
either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others**,
or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [JSON Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free account in Auth0

1. Go to [Auth0](https://auth0.com) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section.
Please do not report security vulnerabilities on the public GitHub issue tracker.
The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](https://auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.
