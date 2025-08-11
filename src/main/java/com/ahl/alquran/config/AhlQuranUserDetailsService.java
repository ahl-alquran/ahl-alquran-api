package com.ahl.alquran.config;

import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.repo.AhlQuranUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AhlQuranUserDetailsService implements UserDetailsService {

    private final AhlQuranUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AhlQuranUser ahlQuranUser = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found for username: " + username));
        List<GrantedAuthority> authorities = ahlQuranUser.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
                
        return new User(ahlQuranUser.getUsername(), ahlQuranUser.getPwd(), authorities);
    }
}
