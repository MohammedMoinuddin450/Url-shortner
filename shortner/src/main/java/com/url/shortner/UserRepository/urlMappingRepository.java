package com.url.shortner.UserRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.model.Urlmapping;
import com.url.shortner.model.Users;

@Repository
public interface urlMappingRepository extends JpaRepository<Urlmapping , Long>{
    Urlmapping findByshorturl(String shorturl);
    List<Urlmapping> findByUser(Users user);
} 
