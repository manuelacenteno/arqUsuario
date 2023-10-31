package com.example.demo.Usuario.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Usuario.dto.ReporteMonopatinesPorViajeDTO;
import com.example.demo.Usuario.dto.UsuarioViajeDTO;

@Service
public class ViajeServicio {
    @Value("${viajeURL}")
    private String viajeURL;
    private final RestTemplate rest;

    @Autowired
    public ViajeServicio(RestTemplate rest) {
        this.rest = rest;

    }

    public String enviarViaje(UsuarioViajeDTO v) {
        return rest.getForEntity(viajeURL + "/iniciar/" + v.getIdUsuarioServ() + "/" + v.getIdUsuario(), String.class)
                .getBody();
    }

    public List<ReporteMonopatinesPorViajeDTO> obtenerReportePorViaje(int cantViajes, int anio) {
        String url = viajeURL + "/obtenerReporteMonopatinesPorViaje/" + cantViajes + "/" + anio;
        ResponseEntity<List<ReporteMonopatinesPorViajeDTO>> response = rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReporteMonopatinesPorViajeDTO>>() {
                });
        return response.getBody();
    }

    public Double getTotalFacturadoEnRangoDeMeses(int mesInicio, int mesFin, int anio) {
        return rest.getForEntity(viajeURL + "/totalFacturadoEnRangoDeMeses/" + mesInicio + "/" + mesFin + "/" + anio,
                Double.class).getBody();
    }

}
