package com.auth0.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class PhotosController {

    @RequestMapping(value = "/api/public", method = RequestMethod.GET)
    @ResponseBody
    public String publicEndpoint() {
        return "All good. You DO NOT need to be authenticated to call /api/public";
    }

    @RequestMapping(value = "/api/private", method = RequestMethod.GET)
    @ResponseBody
    public String privateEndpoint() {
        return "All good. You can see this because you are Authenticated.";
    }

    @RequestMapping(value = "/api/private-scoped", method = RequestMethod.GET)
    @ResponseBody
    public String privateScopedEndpoint() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope";
    }

}
