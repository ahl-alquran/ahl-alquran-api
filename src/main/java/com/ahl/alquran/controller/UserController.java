package com.ahl.alquran.controller;

import com.ahl.alquran.constants.ApplicationConstants;
import com.ahl.alquran.dto.LoginRequestDTO;
import com.ahl.alquran.dto.LoginResponseDTO;
import com.ahl.alquran.dto.UserRequestDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.service.AhlQuranUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final AhlQuranUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDTO user) {
        AhlQuranUser savedUser = userService.registerUser(user);
        if (savedUser.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).
                    body("Given user details are successfully registered");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("User registration failed");
        }

    }



    @GetMapping("/user")
    public AhlQuranUser getUserDetailsAfterLogin(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
