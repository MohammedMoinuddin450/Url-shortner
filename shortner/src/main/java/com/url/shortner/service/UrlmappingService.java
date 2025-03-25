package com.url.shortner.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.shortner.DTOs.ClickeventDTO;
import com.url.shortner.DTOs.UrlmappingDTO;
import com.url.shortner.UserRepository.ClickEventRepository;
import com.url.shortner.UserRepository.urlMappingRepository;
import com.url.shortner.model.Clickevent;
import com.url.shortner.model.Urlmapping;
import com.url.shortner.model.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Service
@AllArgsConstructor
public class UrlmappingService {

    private urlMappingRepository urlmappingrepository;
    private ClickEventRepository clickEventRepository;

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


    public List<UrlmappingDTO> getUrlsByUser(Users user) {
        return urlmappingrepository.findByUser(user).stream()
                .map(this::convertToDto)
                .toList();
    }


     public List<ClickeventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        Urlmapping urlMapping = urlmappingrepository.findByshorturl(shortUrl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlmappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickeventDTO clickEventDTO = new ClickeventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }


    public Map<LocalDate, Long> getTotalClicksByUserAndDate(Users user, LocalDate start, LocalDate end) {
        List<Urlmapping> urlMappings = urlmappingrepository.findByUser(user);
        List<Clickevent> clickEvents = clickEventRepository.findByUrlmappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));

    }


    public Urlmapping getOriginalUrl(String shortUrl) {
        Urlmapping urlMapping = urlmappingrepository.findByshorturl(shortUrl);
        if (urlMapping != null) {
            urlMapping.setClick_count(urlMapping.getClick_count() + 1);
            urlmappingrepository.save(urlMapping);

            
            Clickevent clickEvent = new Clickevent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlmapping(urlMapping);
            clickEventRepository.save(clickEvent);
        }
        return urlMapping;
    }
}

