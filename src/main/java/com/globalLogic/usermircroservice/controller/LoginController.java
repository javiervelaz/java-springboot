package com.globalLogic.usermircroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globalLogic.usermircroservice.service.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    
     @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody String token) {
        try {
            return userService.login(token);
        } catch (Exception e) {
            // error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }  
    }
}
