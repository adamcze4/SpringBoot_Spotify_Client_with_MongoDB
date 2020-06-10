package com.spotify.client.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    @GetMapping("/user")
    public Principal get(Principal principal) {
        return principal;
    }
}
