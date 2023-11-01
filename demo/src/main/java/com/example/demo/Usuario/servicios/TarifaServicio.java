package com.example.demo.Usuario.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Usuario.model.Tarifa;

@Service
public class TarifaServicio {

    @Value("${tarifaURL}")
    private String tarifaURL;
    private final RestTemplate rest;

    @Autowired
    public TarifaServicio(RestTemplate rest){
        this.rest = rest;
    }

    @PostMapping
    public void aplicarTarifa(@RequestBody Tarifa tarifa) {
        HttpEntity<Tarifa> request = new HttpEntity<>(tarifa);
        rest.exchange(tarifaURL, HttpMethod.POST, request, Void.class);
    }

}
