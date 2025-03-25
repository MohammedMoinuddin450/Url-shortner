package com.url.shortner.UserRepository;

import com.url.shortner.model.Clickevent;
import com.url.shortner.model.Urlmapping;
//import com.url.shortner.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<Clickevent, Long> {
   List<Clickevent> findByUrlmappingAndClickDateBetween(Urlmapping mapping, LocalDateTime startDate, LocalDateTime endDate);
   List<Clickevent> findByUrlmappingInAndClickDateBetween(List<Urlmapping> urlMappings, LocalDateTime startDate, LocalDateTime endDate);
}