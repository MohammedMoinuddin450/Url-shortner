package com.url.shortner.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.url.shortner.DTOs.loginrequestDTO;
import com.url.shortner.security.JWT.JWTresponse;
import com.url.shortner.security.JWT.JWTutils;
import com.url.shortner.UserRepository.UserRepository;
import com.url.shortner.model.Users;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class userService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTutils jwTutils;

    public Users registerusersave(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public JWTresponse authenticateuser(loginrequestDTO request) {
       Authentication authentication=authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);    
        userdetailsimpl userdetails=(userdetailsimpl) authentication.getPrincipal();
        String jwt=jwTutils.generateToken(userdetails);
        return new JWTresponse(jwt);

    }

    public Users findByUsername(String name) {
        
                return userRepository.findByUsername(name)
        .orElseThrow(() -> new UsernameNotFoundException("User with " + name + " not found"));
    }


}
