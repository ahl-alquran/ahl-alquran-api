package com.ahl.alquran.controller;

import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.exception.BusinessException;
import com.ahl.alquran.service.LevelStatisticsService;
import com.ahl.alquran.service.RecaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/public")
public class PublicController {

    private final LevelStatisticsService levelStatisticsService;
    private final RecaptchaService recaptchaService;

    @GetMapping("/result")
    public ResponseEntity<StudentResponseDTO> result(@RequestParam Integer code, @RequestParam Integer year, @RequestParam String recaptchaResponse) {
        if (recaptchaService.verifyRecaptcha(recaptchaResponse)) {
            StudentResponseDTO result = levelStatisticsService.getResult(code, year);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
            throw new BusinessException("لا توجد نتائج للطالب في هذه السنة");
        } else {
            throw new BusinessException("Failed reCAPTCHA verification");
        }
    }
}
