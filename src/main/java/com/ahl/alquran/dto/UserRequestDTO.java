package com.ahl.alquran.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank(message = "Name must not be empty")
        String name,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "Mobile number must be 11 digits")
        String mobile,
        @NotBlank(message = "Username must not be empty")
        String username,
        @NotBlank(message = "Password must not be empty")
        String password,
        String email,
        String role) {
}

