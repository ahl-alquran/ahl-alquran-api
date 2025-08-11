package com.ahl.alquran.dto;

import lombok.Builder;

@Builder
public record StudentExamDTO(
        int code,
        String level,
        int year
) {
}
