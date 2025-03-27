package com.url.shortner.DTOs;

import java.util.Set;

import lombok.Data;

@Data
public class RegisterrequestDTO {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;    

}
