package com.example.demo.Usuario.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonopatinServicio {
    @Value("${monopatinURL}")
    private String cuentaURL;

    private final RestTemplate rest;

    @Autowired
    public MonopatinServicio(RestTemplate rest){
        this.rest = rest;
    }


}
