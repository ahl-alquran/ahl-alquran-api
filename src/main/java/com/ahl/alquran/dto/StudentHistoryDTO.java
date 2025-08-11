package com.ahl.alquran.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class StudentHistoryDTO {
    private String name;
    private Integer code;
    private String level;
    private Integer result;
    private String city;
    private Integer year;
    private String nationalId;

    public StudentHistoryDTO(String name, Integer code, String level, Integer result, String city, Integer year, String nationalId) {
        this.name = name;
        this.code = code;
        this.level = level;
        this.result = result;
        this.city = city;
        this.year = year;
        this.nationalId = nationalId;
    }

    public StudentHistoryDTO(String name, Integer code, String nationalId) {
        this.name = name;
        this.code = code;
        this.nationalId = nationalId;
    }

}
