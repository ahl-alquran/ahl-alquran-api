package com.ahl.alquran.dto;

import lombok.Builder;

@Builder
public record StudentDTO(
        String name,
        String nationalId,
        int code,
        String city,
        String level,
        int year
) {
}
