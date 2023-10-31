package com.example.demo.Usuario.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class CuentaServicio {
    @Value("${cuentaURL}")
    private String cuentaURL;

    private final RestTemplate rest;

    @Autowired
    public CuentaServicio(RestTemplate rest){
        this.rest = rest;
    }

    @PutMapping
    public String anularCuenta(Long idCuenta) {
        String response = rest.exchange(cuentaURL + "/anularCuenta/" + idCuenta, HttpMethod.PUT, null, String.class).getBody();
        return response;
    }
}
