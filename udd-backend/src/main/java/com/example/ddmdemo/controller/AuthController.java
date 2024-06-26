package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.LoginDTO;
import com.example.ddmdemo.dto.UserRegistrationDTO;
import com.example.ddmdemo.model.User;
import com.example.ddmdemo.service.impl.UserService;
import com.example.ddmdemo.util.TokenUtils;
import com.example.ddmdemo.security.UserTokenState;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.NoSuchElementException;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

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

//    @PostMapping("/login")
//    public ResponseEntity<UserTokenState> login(@RequestBody LoginDTO authenticationRequest,
//                                                HttpServletResponse response) {
//        System.out.println("Usao u login");
//        try {
//
//            User logInUser = userService.login(authenticationRequest);
//            System.out.println(logInUser);
//            StringBuilder passwordWithSalt = new StringBuilder();
//            passwordWithSalt.append(authenticationRequest.getPassword());
//            passwordWithSalt.append(logInUser.getSalt());
//            System.out.println(passwordWithSalt);
//
//            try {
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//
//                // Ako se izvrsi, autentifikacija je uspesna
//                System.out.println("Korisnik je uspesno autentifikovan: " + authentication.getName());
//                // Mozes dalje obraditi rezultat autentifikacije ako je potrebno
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                User user = (User) authentication.getPrincipal();
//                System.out.println(user);
//
//                String jwt = tokenUtils.generateToken(authenticationRequest.getEmail());
//                int expiresIn = tokenUtils.getExpiredIn();
//                return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user));
//
//            } catch (AuthenticationException e) {
//                // U slucaju neuspela autentifikacije, mozes obraditi izuzetak
//                System.out.println("Autentifikacija nije uspela: " + e.getMessage());
//            }
//
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> login(@RequestBody LoginDTO authenticationRequest, HttpServletResponse response) {
        System.out.println("Attempting to authenticate user: " + authenticationRequest.getEmail());
        User logInUser = userService.login(authenticationRequest);
        System.out.println(logInUser);
        StringBuilder passwordWithSalt = new StringBuilder();
        passwordWithSalt.append(authenticationRequest.getPassword());
        passwordWithSalt.append(logInUser.getSalt());
        System.out.println(passwordWithSalt);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), passwordWithSalt.toString()));

            System.out.println("Authentication successful: " + authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();

            String jwt = tokenUtils.generateToken(authenticationRequest.getEmail());
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user));
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
