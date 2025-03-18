package com.url.shortner.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.url.shortner.DTOs.UrlmappingDTO;
import com.url.shortner.model.Users;
import com.url.shortner.service.UrlmappingService;
import com.url.shortner.service.userService;

@RestController
@RequestMapping("/api/urls")
public class UrlmappingController {

    private UrlmappingService urlmappingService;
    private userService userservice;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlmappingDTO> CreateshortUrl(
        @RequestBody Map<String ,String> request
        , Principal principal){

        String Originalurl=request.get("Originalurl");
        Users user=userservice.findByUsername(principal.getName());
        UrlmappingDTO urlmappingDTO=urlmappingService.shorten(Originalurl,user);
        return ResponseEntity.ok(urlmappingDTO);

    }

}
