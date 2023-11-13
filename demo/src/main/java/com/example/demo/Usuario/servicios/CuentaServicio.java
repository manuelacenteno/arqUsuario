package com.example.demo.Usuario.servicios;

import com.example.demo.Usuario.controler.CuentaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class CuentaServicio {

    @Autowired
    private CuentaController cuentaController;

    public String anularCuenta(Long idCuenta) {
        return cuentaController.anularCuenta(idCuenta);
    }

    public Float dameSaldo(Long idCuenta){
        return cuentaController.tieneSaldo(idCuenta);
    }
}
