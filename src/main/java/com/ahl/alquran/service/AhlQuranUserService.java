package com.ahl.alquran.service;

import com.ahl.alquran.dto.UserRequestDTO;
import com.ahl.alquran.dto.UserResponseDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.entity.Authority;
import com.ahl.alquran.exception.BusinessException;
import com.ahl.alquran.repo.AhlQuranAuthorityRepository;
import com.ahl.alquran.repo.AhlQuranUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AhlQuranUserService {

    private final AhlQuranUserRepository userRepository;
    private final AhlQuranAuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

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

    public UserResponseDTO findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getName(), user.getUsername(),
                        user.getMobileNumber(), user.getEmail(), user.getAuthorities()))
                .toList();
    }

    @Transactional
    public void updateUserAuthority(Set<String> authorities, String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.updateUserAuthority(authorities, username);
    }

    public Set<String> getAuthorities() {
        return authorityRepository.findAll().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
    }
}
