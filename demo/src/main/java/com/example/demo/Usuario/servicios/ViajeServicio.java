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

    public Double getTotalFacturadoEnRangoDeMeses(int mesInicio, int mesFin, int anio, String authorization) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            HttpEntity<Object> entity = new HttpEntity<>(headers);

            // Realiza la solicitud GET
            ResponseEntity<Double> response = rest.exchange(
                    viajeURL + "/totalFacturadoEnRangoDeMeses/" + mesInicio + "/" + mesFin + "/" + anio,
                    HttpMethod.GET,
                    entity,
                    Double.class);

            return response.getBody();
    }

    public List<ReporteMonopatinesPorViajeDTO> obtenerReportePorViaje(int cantViajes, int anio, String authorization) {
        String url = viajeURL + "/obtenerReporteMonopatinesPorViaje/" + cantViajes + "/" + anio;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        // Configura la entidad de la solicitud con los headers
        ResponseEntity<List<ReporteMonopatinesPorViajeDTO>> response = rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers), // Incluye los encabezados en la solicitud
                new ParameterizedTypeReference<List<ReporteMonopatinesPorViajeDTO>>() {
                });

        return response.getBody();
    }

    public String enviarViaje(UsuarioViajeDTO v) {
        return rest.getForEntity(viajeURL + "/iniciar/" + v.getIdUsuarioServ() + "/" + v.getIdUsuario(), String.class)
                .getBody();
    }

}
