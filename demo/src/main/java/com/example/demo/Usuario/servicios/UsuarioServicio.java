package com.example.demo.Usuario.servicios;

import com.example.demo.Usuario.model.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;
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
    @Autowired
    private UsuarioRepository userRepository;

    //llamo a cuenta
    public Float dameSaldo(Long idCuenta){
        return rest.getForEntity(cuentaURL + "/tieneSaldo/" + idCuenta, Float.class).getBody();
    }

    public Usuario createUser(Usuario user) {
        return userRepository.save(user);
    }
}
