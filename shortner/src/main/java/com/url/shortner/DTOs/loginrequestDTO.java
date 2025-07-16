package com.url.shortner.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class loginrequestDTO {
    @NotBlank(message = "username is required")
    private String username;
    @Size(message = "Password is required")
    private String password;
}