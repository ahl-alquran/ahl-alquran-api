package com.ahl.alquran.service;

import com.ahl.alquran.dto.LoginRequestDTO;
import com.ahl.alquran.dto.UserRequestDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.entity.Authority;
import com.ahl.alquran.exception.BusinessException;
import com.ahl.alquran.repo.AhlQuranAuthorityRepository;
import com.ahl.alquran.repo.AhlQuranUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Ahl Quran User Service Test")
@ExtendWith(MockitoExtension.class)
class AhlQuranUserServiceTest {

    @Mock
    private AhlQuranUserRepository userRepository;

    @Mock
    private AhlQuranAuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Environment env;

    @InjectMocks
    private AhlQuranUserService userService;

    private AhlQuranUser testUser;
    private Authority testAuthority;
    private UserRequestDTO registerRequest;

    @BeforeEach
    void setUp() {
        testAuthority = Authority.builder().name("USER").build();
        testUser = AhlQuranUser.builder().id(1L)
                .username("testuser")
                .pwd("hashedPassword")
                .name("Test User")
                .email("test@example.com")
                .mobileNumber("01034567890")
                .authorities(Set.of(testAuthority)).build();
        registerRequest = new UserRequestDTO(
                "Test User",
                "1234567890",
                "testuser",
                "test@example.com",
                "password",
                "USER"
        );
    }

    @Test
    @DisplayName("registerUser_NewUser_ReturnsSavedUser")
    void registerUser_NewUser_ReturnsSavedUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(authorityRepository.findByName(anyString())).thenReturn(Optional.ofNullable(testAuthority));
        when(userRepository.save(any(AhlQuranUser.class))).thenReturn(testUser);

        AhlQuranUser result = userService.registerUser(registerRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(any(AhlQuranUser.class));
    }

    @Test
    @DisplayName("registerUser_ExistingUser_ThrowsBusinessException")
    void registerUser_ExistingUser_ThrowsBusinessException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        assertThrows(BusinessException.class, () -> userService.registerUser(registerRequest));
        verify(userRepository, never()).save(any(AhlQuranUser.class));
    }

    @Test
    @DisplayName("login_ValidCredentials_ReturnsJwtToken")
    void login_ValidCredentials_ReturnsJwtToken() {
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);
        when(env.getProperty(anyString(), anyString())).thenReturn("jxgEQeXHuPqsfsdfdYFNkdsdsdsshrhlmsn4");

        String token = userService.login(new LoginRequestDTO("user", "pass"));

        assertNotNull(token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("login_InvalidCredentials_ThrowsException")
    void login_InvalidCredentials_ThrowsException() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        try {
            userService.login(new LoginRequestDTO("user", "pass"));
        } catch (BadCredentialsException e) {
            assertInstanceOf(BadCredentialsException.class, e);
            assertEquals("Invalid credentials", e.getMessage());
        }
    }

    @Test
    @DisplayName("findByUsername_UserExists_ReturnsUser")
    void findByUsername_UserExists_ReturnsUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        AhlQuranUser result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    @DisplayName("findByUsername_UserNotExists_ReturnsNull")
    void findByUsername_UserNotExists_ReturnsNull() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        AhlQuranUser result = userService.findByUsername("nonexistent");

        assertNull(result);
    }
}