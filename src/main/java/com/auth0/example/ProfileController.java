package com.auth0.example;

import com.auth0.spring.security.api.Auth0JWTToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ProfileService profileService;

    @Autowired
    protected UsernameService usernameService;

    @RequestMapping(value = "profiles", method = RequestMethod.GET)
    public List<Profile> list() {
        return profileService.list();
    }

    /**
     * Simple demonstration of how Principal can be injected
     * Here, as demonstration, we want to do audit as only ROLE_ADMIN can create user..
     */
    @RequestMapping(value ="profiles", method = RequestMethod.POST)
    public Profile create(final @Validated @RequestBody Profile profile, final Principal principal) {
        logger.info("create invoked");
        printGrantedAuthorities((Auth0JWTToken) principal);
        final String username = usernameService.getUsername();
        // log username of user requesting profile creation
        logger.info("User with email: " + username + " creating new profile");
        return profileService.create(profile);
    }

    @RequestMapping(value ="profiles/{id}", method = RequestMethod.GET)
    public Profile get(final @PathVariable Long id) {
        logger.info("get invoked");
        return profileService.get(id);
    }

    @RequestMapping(value ="profiles/{id}", method = RequestMethod.PUT)
    public Profile update(final @PathVariable Long id, final @Validated @RequestBody Profile profile) {
        logger.info("update invoked");
        return profileService.update(id, profile);
    }

    @RequestMapping(value ="profiles/{id}", method = RequestMethod.DELETE)
    public Profile delete(final @PathVariable Long id) {
        logger.info("delete invoked");
        return profileService.delete(id);
    }

    /**
     * Simple demonstration of how Principal info can be accessed
     */
    private void printGrantedAuthorities(final Auth0JWTToken principal) {
        for(final GrantedAuthority grantedAuthority: principal.getAuthorities()) {
            final String authority = grantedAuthority.getAuthority();
            logger.info(authority);
        }
    }

}
