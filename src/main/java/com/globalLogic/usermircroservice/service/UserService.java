package com.globalLogic.usermircroservice.service;

import org.springframework.http.ResponseEntity;

import com.globalLogic.usermircroservice.dto.UserDTO;
import com.globalLogic.usermircroservice.model.User;

public interface  UserService {
    ResponseEntity<Object> registerUser(UserDTO user);
    User updateUser(User user);
    User getUserByEmail(String email);
    Object checkIfExists(String email);
    ResponseEntity<Object> login(String token);
}
