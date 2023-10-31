package com.example.demo.Usuario.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioServicio {
    @Value("${cuentaURL}")
    private String cuentaURL;
    private final RestTemplate rest;

    @Autowired
    public UsuarioServicio(RestTemplate rest){
        this.rest = rest;

    }
    //llamo a cuenta
    public Float dameSaldo(Long idCuenta){
        return rest.getForEntity(cuentaURL + "/tieneSaldo/" + idCuenta, Float.class).getBody();
    }
}