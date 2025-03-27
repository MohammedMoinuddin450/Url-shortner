package com.url.shortner.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.url.shortner.DTOs.ClickeventDTO;
import com.url.shortner.DTOs.UrlmappingDTO;

import com.url.shortner.model.Users;
import com.url.shortner.service.UrlmappingService;
import com.url.shortner.service.userService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
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

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlmappingDTO>> getUserUrls(Principal principal){
        Users user = userservice.findByUsername(principal.getName());
        List<UrlmappingDTO> urls = urlmappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }


      @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickeventDTO>> getUrlAnalytics(
                                        @PathVariable String shortUrl,
                                    @RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickeventDTO> clickEventDTOS = urlmappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);
    }


    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(
                                                    Principal principal,
                                            @RequestParam("startDate") String startDate,
                                            @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        Users user = userservice.findByUsername(principal.getName());
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlmappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }
}
