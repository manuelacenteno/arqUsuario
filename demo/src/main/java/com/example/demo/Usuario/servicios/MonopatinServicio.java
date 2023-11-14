package com.example.demo.Usuario.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonopatinServicio {
    @Value("${monopatinURL}")
    private String monopatinURL;

    private final RestTemplate rest;

    @Autowired
    public MonopatinServicio(RestTemplate rest) {
        this.rest = rest;
    }

    public Map<String, Integer> obtenerMonopatinesEnTaller(String authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        // Configura la entidad de la solicitud con los headers
        ResponseEntity<Map<String, Integer>> response = rest.exchange(
                monopatinURL + "/cantidadMonopatines",
                HttpMethod.GET,
                new HttpEntity<>(headers), // Incluye los headers en la solicitud
                new ParameterizedTypeReference<Map<String, Integer>>() {
                });

        return response.getBody();
    }
}
