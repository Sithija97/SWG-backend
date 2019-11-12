package com.virtusa.smartwalletguide.backend.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/test")
public class TestController {

    @GetMapping("/test")
    public Object test(Authentication authentication){
        return authentication.getName();
    }
}
