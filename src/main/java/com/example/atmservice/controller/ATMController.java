package com.example.atmservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class ATMController {

    @Value("${bank-service-key.header}")
    private String principalRequestHeader;

    @Value("${bank-service-key.token}")
    private String principalRequestToken;

    private final RestTemplate apiTemplate;

    private final String BANK_SERVICE_URL = "http://localhost:8081";

    public static final String ATMService = "ATMService";


    @GetMapping("/validate")
    @Retry(name = ATMService, fallbackMethod = "bankServiceIsDownHandler")
    public ResponseEntity<?> validateCard(@RequestParam String cardNumber) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/validate")
                .queryParam("cardNumber",cardNumber)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(principalRequestHeader, principalRequestToken);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.GET,
                request,
                String.class
        );
    }

    @PostMapping("/auth")
    @Retry(name = ATMService, fallbackMethod = "bankServiceIsDownHandler")
    public ResponseEntity<?> authenticateUser(@RequestParam String cardNumber, @RequestParam String pin) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/auth")
                .queryParam("cardNumber",cardNumber)
                .queryParam("pin",pin)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(principalRequestHeader, principalRequestToken);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @PostMapping("/deposit")
    @Retry(name = ATMService, fallbackMethod = "bankServiceIsDownHandler")
    public ResponseEntity<?> deposit(@RequestHeader(value = "Authorization", required = false) String jwt, @RequestParam Double amount) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/deposit")
                .queryParam("amount", amount)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.add(principalRequestHeader, principalRequestToken);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @PostMapping("/withdraw")
    @Retry(name = ATMService, fallbackMethod = "bankServiceIsDownHandler")
    public ResponseEntity<?> withdraw(@RequestHeader(value = "Authorization", required = false) String jwt, @RequestParam Double amount) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/withdraw")
                .queryParam("amount", amount)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.add(principalRequestHeader, principalRequestToken);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @GetMapping("/balance")
    @Retry(name = ATMService, fallbackMethod = "bankServiceIsDownHandler")
    public ResponseEntity<?> checkBalance(@RequestHeader(value = "Authorization", required = false) String jwt) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BANK_SERVICE_URL + "/balance")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(principalRequestHeader, principalRequestToken);
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return apiTemplate.exchange(
                url.toString(),
                HttpMethod.GET,
                request,
                String.class
        );
    }

    public ResponseEntity<String> bankServiceIsDownHandler(Exception e){
        return ResponseEntity.ok("Bank Service is down. Please try again later.");
    }
}
