package com.ahl.alquran.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StudentDTO(
        @NotBlank
        String name,
        @NotBlank
        String nationalId,
        int code,
        String city
) {
}
