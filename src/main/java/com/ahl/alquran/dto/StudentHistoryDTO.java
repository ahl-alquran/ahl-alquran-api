package com.ahl.alquran.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentHistoryDTO {
    private Integer year;
    private String level;
    private Integer result;
}
