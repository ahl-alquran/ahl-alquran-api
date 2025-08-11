package com.ahl.alquran.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class StudentResponseDTO {
    private String name;
    private Integer code;
    private String level;
    private Integer result;
    private String city;
    private Integer year;
    private String nationalId;

    public StudentResponseDTO(String name, Integer code, String level, Integer result, String city, Integer year, String nationalId) {
        this.name = name;
        this.code = code;
        this.level = level;
        this.result = result;
        this.city = city;
        this.year = year;
        this.nationalId = nationalId;
    }

    public StudentResponseDTO(String name, Integer code, String city, String nationalId) {
        this.name = name;
        this.code = code;
        this.city = city;
        this.nationalId = nationalId;
    }

}
