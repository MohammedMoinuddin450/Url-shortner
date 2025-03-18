package com.url.shortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.model.Users;
import com.url.shortner.service.userService;

import com.url.shortner.DTOs.Registerrequest;
import com.url.shortner.DTOs.loginrequest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class authcontroller {

    private userService userservice;

    @PostMapping("public/register")
    public ResponseEntity<?> registeruser(@RequestBody Registerrequest request){
        Users user=new Users();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole("ROLE_USER");

        userservice.registerusersave(user);

        return ResponseEntity.ok("user created successfully");
    }

    @PostMapping("public/login")
    public ResponseEntity<?> loginuser(@RequestBody loginrequest request){

        return ResponseEntity.ok(userservice.authenticateuser(request));
}
}
