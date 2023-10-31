package com.example.demo.Usuario.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Usuario.dto.UsuarioViajeDTO;
import com.example.demo.Usuario.model.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;
import com.example.demo.Usuario.servicios.CuentaServicio;
import com.example.demo.Usuario.servicios.UsuarioServicio;
import com.example.demo.Usuario.servicios.ViajeServicio;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private UsuarioServicio usuarioServ;

    @Autowired
    private CuentaServicio cuentaServicio;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return repo.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario){
        return repo.save(usuario);
    }

    @GetMapping("anularCuenta/{idUsuario}/{idCuenta}")
    public String anularCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta) {

        // Busca usuario y comprueba si es administrador
        Usuario usuario = repo.findById(idUsuario).orElse(null);
        if (usuario.getRol() == 'a') {
            String intentarAnularCuenta = cuentaServicio.anularCuenta(idCuenta);
            return intentarAnularCuenta;
        }
        
        return "El usuario no es administrador";
    }

    @GetMapping("/dameSaldo/{id}")
    public Float dameSaldo(@PathVariable Long id){
        return usuarioServ.dameSaldo(id);

    }

    @GetMapping("/rolAdmin/{id}")
     public boolean xRolAdmin(@PathVariable Integer id){
         if(repo.xRol(id) == 'a'){
            return true;
         }
        return false ;
    } 

    @GetMapping("/rolEncargado/{id}")
     public boolean xRolEncargado(@PathVariable Integer id){
         if(repo.xRol(id) == 'g'){
            return true;
         }
        return false ;
    } 

}