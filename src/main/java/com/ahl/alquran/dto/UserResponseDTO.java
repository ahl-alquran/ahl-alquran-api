package com.ahl.alquran.dto;

import com.ahl.alquran.entity.Authority;
import lombok.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String username;
    private String mobileNumber;
    private String email;
    private Set<String> authorities;

    public UserResponseDTO(String name, String username, String mobileNumber, String email) {
        this.name = name;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public UserResponseDTO(String name, String username, String mobileNumber, String email, Collection<Authority> authorities) {
        this.name = name;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.authorities = authorities.stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
    }
}

