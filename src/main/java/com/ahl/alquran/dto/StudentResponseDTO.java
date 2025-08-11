package com.ahl.alquran.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private String name;
    private Integer code;
    private String level;
    private Integer result;
    private String city;
    private Integer year;
    private String nationalId;
    
}
