package com.ahl.alquran.controller;

import com.ahl.alquran.dto.UserRequestDTO;
import com.ahl.alquran.dto.UserResponseDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import com.ahl.alquran.service.AhlQuranUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AhlQuranUserService userService;

    @PostMapping("/add")
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

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/authority")
    public ResponseEntity<String> updateUserAuthority(Set<String> authorities, String username) {
        userService.updateUserAuthority(authorities, username);
        return ResponseEntity.ok("User Updated successfully");
    }

    @GetMapping("authority/list")
    public ResponseEntity<Set<String>> getAllAuthorities() {
        Set<String> authorities = userService.getAuthorities();
        return ResponseEntity.status(HttpStatus.OK).body(authorities);
    }
}
