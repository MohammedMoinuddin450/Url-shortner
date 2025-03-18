package com.url.shortner.service;

import java.time.LocalDateTime;
import java.util.Random;

import com.url.shortner.DTOs.UrlmappingDTO;
import com.url.shortner.UserRepository.urlMappingRepository;
import com.url.shortner.model.Urlmapping;
import com.url.shortner.model.Users;

public class UrlmappingService {

    private urlMappingRepository urlmappingrepository;

    public UrlmappingDTO shorten(String originalurl, Users user) {
        String shortUrl = generateShortUrl();
        Urlmapping urlMapping = new Urlmapping();
        urlMapping.setOriginalurl(originalurl);
        urlMapping.setShorturl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedTime(LocalDateTime.now());
        Urlmapping savedUrlMapping = urlmappingrepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }


    private UrlmappingDTO convertToDto(Urlmapping urlMapping){
        UrlmappingDTO urlMappingDTO = new UrlmappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalurl(urlMapping.getOriginalurl());
        urlMappingDTO.setShortUrl(urlMapping.getShorturl());
        urlMappingDTO.setClickCount(urlMapping.getClick_count());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedTime());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDTO;
    }
        
    private String generateShortUrl() {
         String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }
}

