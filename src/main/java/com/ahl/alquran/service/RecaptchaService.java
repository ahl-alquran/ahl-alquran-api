package com.ahl.alquran.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String secretKey;
    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    public boolean verifyRecaptcha(String token) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", token);

        RecaptchaResponse response = restTemplate.postForObject(
                recaptchaUrl, params, RecaptchaResponse.class);

        return response != null && response.isSuccess();
    }

    @Getter
    @Setter
    private static class RecaptchaResponse {
        private boolean success;
        private double score;
        private String action;
        private Date challenge_ts;
        private String hostname;
    }

}
