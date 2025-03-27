package com.url.shortner.DTOs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlmappingDTO {
    private Long id;
    private String Originalurl;
    private String shortUrl;
    private int  clickCount;
    private LocalDateTime createdDate;
    private String username;


}
