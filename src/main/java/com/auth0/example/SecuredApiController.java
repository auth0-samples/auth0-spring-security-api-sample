package com.auth0.example;

import com.auth0.spring.security.api.Auth0JWTToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Component
@RestController
public class SecuredApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected EmailRetrievalService emailRetrievalService;


    @RequestMapping("/secured/username")
    public String exampleGetRequest(Principal principal) {
        logger.info("Handling getUsername request");
        final String name = principal.getName();
        logger.info("Principal name: " + name);
        printGrantedAuthorities((Auth0JWTToken) principal);
        final String email = emailRetrievalService.fetchEmail();
        return email;
    }

    private void printGrantedAuthorities(final Auth0JWTToken principal) {
        for(final GrantedAuthority grantedAuthority: principal.getAuthorities()) {
            final String authority = grantedAuthority.getAuthority();
            logger.info(authority);
        }
    }

    @RequestMapping(value = "/secured/post", method = RequestMethod.POST)
    public void examplePostRequest(Principal principal, @RequestBody Map<String, Object> payload) {
        // JSON Post body converted to map
        logger.info("Handling post request");
        payload.forEach((key, value) -> logger.info("key:" + key + ", value:" + value.toString()));
    }

}
