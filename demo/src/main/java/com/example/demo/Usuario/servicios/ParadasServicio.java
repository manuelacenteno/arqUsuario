package com.example.demo.Usuario.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Usuario.dto.ReporteMonopatinesPorViajeDTO;
import com.example.demo.Usuario.model.Parada;

@Service
public class ParadasServicio {

    @Value("${paradasURL}")
    private String paradasURL;
    private final RestTemplate rest;

    @Autowired
    public ParadasServicio(RestTemplate rest){
        this.rest = rest;
    }

    public List<Parada> getParadas(Double latitud, Double longitud, String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        // Configura la entidad de la solicitud con los headers
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Realiza la solicitud GET
        ResponseEntity<List<Parada>> response = rest.exchange(
                paradasURL + "/parada_cercanas/" + longitud + "/" + latitud,
                HttpMethod.GET,
                requestEntity, // Incluye los headers en la solicitud
                new ParameterizedTypeReference<List<Parada>>() {
                });

        return response.getBody();
    }
}
