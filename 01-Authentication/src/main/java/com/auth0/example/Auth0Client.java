package com.auth0.example;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import org.springframework.stereotype.Component;

@Component
public class Auth0Client {

    private final AuthAPI client;

    public Auth0Client(String domain, String clientId, String clientSecret) {
        this.client = new AuthAPI(domain, clientId, clientSecret);
    }

    public String getUserId(JwtAuthentication authentication) {
        try {
            UserInfo info = client.userInfo(authentication.getToken()).execute();
            return (String) info.getValues().get("sub");
        } catch (Auth0Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
