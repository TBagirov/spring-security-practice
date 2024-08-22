package org.security.practic.controllers;

import org.security.practic.jwt.JwtUtils;
import org.security.practic.request.SignInRequest;
import org.security.practic.service.JwtAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
public class FirstController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Spring Security";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello/user1")
    public String sayHelloUser1(){
        return "Hello User1";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello/admin")
    public String sayHelloAdmin(){
        return "Hello Admin";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {

        return ResponseEntity.ok(jwtAuthenticationService.signIn(signInRequest));
    }

}
