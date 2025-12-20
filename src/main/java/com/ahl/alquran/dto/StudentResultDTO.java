package com.ahl.alquran.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResultDTO {
    private Integer year;
    private Integer code;
    private Integer result;
}
