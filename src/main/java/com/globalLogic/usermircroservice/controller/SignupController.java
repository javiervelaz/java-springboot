package com.globalLogic.usermircroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globalLogic.usermircroservice.dto.UserDTO;
import com.globalLogic.usermircroservice.service.UserService;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    @Autowired
    UserService userService;
    
    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody UserDTO user) {
        try {
             
            return userService.registerUser(user);
        } catch (Exception e) {
            // error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
