package com.example.atmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ATMController {

    private final RestTemplate apiTemplate;

    private final String BANK_SERVICE_URL = "http://localhost:8081";

    @GetMapping("/validate")
    public ResponseEntity<?> validateCard(@RequestParam String cardNumber) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/validate")
                .queryParam("cardNumber",cardNumber)
                .build();

        return apiTemplate.getForEntity(url.toString(), String.class);

    }
    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestParam String cardNumber, @RequestParam String pin) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/auth")
                .queryParam("cardNumber",cardNumber)
                .queryParam("pin",pin)
                .build();
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                null,
                String.class
        );    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader(value = "Authorization", required = false) String jwt, @RequestParam Double amount) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/deposit")
                .queryParam("amount", amount)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                request,
                String.class
        );    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader(value = "Authorization", required = false) String jwt, @RequestParam Double amount) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/withdraw")
                .queryParam("amount", amount)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(@RequestHeader(value = "Authorization", required = false) String jwt) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/balance")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.GET,
                request,
                String.class
        );
    }
}
