package com.ahl.alquran.service;

import com.ahl.alquran.constants.ApplicationConstants;
import com.ahl.alquran.dto.LoginRequestDTO;
import com.ahl.alquran.dto.UserRequestDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.entity.Authority;
import com.ahl.alquran.exception.BusinessException;
import com.ahl.alquran.repo.AhlQuranAuthorityRepository;
import com.ahl.alquran.repo.AhlQuranUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AhlQuranUserService {

    private final AhlQuranUserRepository userRepository;
    private final AhlQuranAuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    public AhlQuranUser registerUser(UserRequestDTO user) {
        AhlQuranUser ahlQuranUser = userRepository.findByUsername(user.username()).orElse(null);
        if (ahlQuranUser == null) {
            String hashPwd = passwordEncoder.encode(user.password());
            Authority authority = authorityRepository.findByName(user.role()).orElse(null);
            ahlQuranUser = AhlQuranUser.builder().pwd(hashPwd)
                    .username(user.username())
                    .name(user.name())
                    .email(user.email())
                    .mobileNumber(String.valueOf(user.mobile()))
                    .authorities(Set.of(authority)).build();
            return userRepository.save(ahlQuranUser);
        } else {
            throw new BusinessException("User already exists");
        }
    }

    public String login(LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),
                loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (null != authenticationResponse && authenticationResponse.isAuthenticated() && null != env) {
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("AhlAlQuran").subject("JWT Token")
                    .claim("username", authenticationResponse.getName())
                    .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(secretKey).compact();
        }
        return jwt;
    }

    public AhlQuranUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
