package com.projectx.foundit.controllers;

import com.projectx.foundit.dto.VerifyUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class ItemHelper {

    @Value("${api.base.url}")
    private static String baseUrl;

    @Value("${api.port}")
    private static String port;

    private final RestTemplate restTemplate;

    public ItemHelper(String baseUrl, String port, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public String callEndpoint() {
        String url = baseUrl + ":" + port + "/auth/verify";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Failed to call endpoint: " + e.getMessage());
            return null;
        }
    }

    public static String verifyUser(VerifyUserDto verifyUserDto) {
        RestTemplate restTemplate = new RestTemplate();

        String url = baseUrl + ":" + port + "/auth/verify";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VerifyUserDto> request = new HttpEntity<>(verifyUserDto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

