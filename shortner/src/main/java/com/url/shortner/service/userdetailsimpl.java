package com.url.shortner.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.url.shortner.model.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class userdetailsimpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private long id;
    private String username;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    

    public userdetailsimpl(long id, String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static userdetailsimpl build(Users user){
        GrantedAuthority authority=new SimpleGrantedAuthority(user.getRole());
        return new userdetailsimpl(user.getUser_id(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(), 
        Collections.singletonList(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        
        return username;
    }

}
