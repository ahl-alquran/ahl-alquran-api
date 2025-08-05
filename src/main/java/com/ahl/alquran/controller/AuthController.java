package com.ahl.alquran.controller;

import com.ahl.alquran.constants.ApplicationConstants;
import com.ahl.alquran.dto.LoginRequestDTO;
import com.ahl.alquran.dto.LoginResponseDTO;
import com.ahl.alquran.dto.UserResponseDTO;
import com.ahl.alquran.service.AhlQuranUserService;
import com.ahl.alquran.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AhlQuranUserService userService;

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }

    @GetMapping("/me")
    public UserResponseDTO getUserDetailsAfterLogin(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }

}
