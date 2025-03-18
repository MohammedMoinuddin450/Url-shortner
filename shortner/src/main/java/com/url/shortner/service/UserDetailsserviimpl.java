package com.url.shortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.url.shortner.UserRepository.UserRepository;
import com.url.shortner.model.Users;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsserviimpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));

        
        return userdetailsimpl.build(users);
    }

}
