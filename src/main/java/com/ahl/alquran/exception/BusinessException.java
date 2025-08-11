package com.ahl.alquran.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final String message;
}
