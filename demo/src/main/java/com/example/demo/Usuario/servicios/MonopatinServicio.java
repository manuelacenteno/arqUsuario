package com.example.demo.Usuario.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public Map<String, Integer> obtenerMonopatinesEnTaller() {
        return rest.getForEntity(monopatinURL + "/cantidadMonopatines",
                Map.class).getBody();
    }
}
