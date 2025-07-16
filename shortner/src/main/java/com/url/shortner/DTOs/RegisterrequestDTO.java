package com.url.shortner.DTOs;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterrequestDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    private Set<String> roles;    

}
