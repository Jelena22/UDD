package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.UserRegistrationDTO;
import com.example.ddmdemo.model.User;
import com.example.ddmdemo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User existUser = this.userService.findByEmail(userRegistrationDTO.getEmail());
        if (existUser != null) {
            throw new NoSuchElementException( "Email already exists");
        }
        try {
            return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
