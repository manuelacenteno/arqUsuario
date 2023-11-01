package com.example.demo.Usuario.controler;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Usuario.dto.ReporteMonopatinesPorViajeDTO;
import com.example.demo.Usuario.dto.UsuarioViajeDTO;
import com.example.demo.Usuario.model.Parada;
import com.example.demo.Usuario.model.Tarifa;
import com.example.demo.Usuario.model.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;
import com.example.demo.Usuario.servicios.CuentaServicio;
import com.example.demo.Usuario.servicios.MonopatinServicio;
import com.example.demo.Usuario.servicios.ParadasServicio;
import com.example.demo.Usuario.servicios.TarifaServicio;
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

    @Autowired
    private MonopatinServicio monoServicio;

    @Autowired
    private ViajeServicio viajeServicio;

    @Autowired
    private TarifaServicio tarifaServicio;

    @Autowired
    private ParadasServicio paradasServicio;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

    @GetMapping("/anularCuenta/{idUsuario}/{idCuenta}")
    public String anularCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta) {

        // Busca usuario y comprueba si es administrador
        if (esAdmin(idUsuario)) {
            String intentarAnularCuenta = cuentaServicio.anularCuenta(idCuenta);
            return intentarAnularCuenta;
        }

        return "El usuario no es administrador";
    }

    @GetMapping("/reporteMonopatines/{cantViajes}/{anio}")
    public List<ReporteMonopatinesPorViajeDTO> obtenerReportePorViaje(
            @PathVariable int cantViajes, @PathVariable int anio) {
        List<ReporteMonopatinesPorViajeDTO> reporte = viajeServicio.obtenerReportePorViaje(cantViajes, anio);
        return reporte;
    }

    @GetMapping("/totalFacturadoEnRangoDeMeses/{mesInicio}/{mesFin}/{anio}")
    public String obtenerTotalFacturadoEnRangoDeMeses(@PathVariable int mesInicio, @PathVariable int mesFin,
            @PathVariable int anio) {
        Double totalFacturado = viajeServicio.getTotalFacturadoEnRangoDeMeses(mesInicio, mesFin, anio);
        return "El total facturado fue:" + totalFacturado;
    }

    @GetMapping("/cantidadMonopatines")
    public Map<String, Integer> obtenerMonopatinesEnTaller() {
        Map<String, Integer> resultado = monoServicio.obtenerMonopatinesEnTaller();
        return resultado;
    }

    @GetMapping("/ajustarTarifa/{idUsuario}")
    public String ajustarTarifa(@PathVariable Long idUsuario, @RequestBody Tarifa tarifa) {
        if (esAdmin(idUsuario)) {
            tarifaServicio.aplicarTarifa(tarifa);
            return "Tarifa aplicada";
        }
        return "El usuario no es admin";
    }

    @GetMapping("/paradasCercanas/{latitud}/{longitud}") 
    public List<Parada> obtenerParadasCercanas(@PathVariable Double latitud, @PathVariable Double longitud) {
        List<Parada> paradas = paradasServicio.getParadas(latitud, longitud);
        return paradas;
    }


    private boolean esAdmin(Long idUsuario) {
        Usuario usuario = repo.findById(idUsuario).orElse(null);
        if (usuario != null && usuario.getRol() == 'a')
            return true;
        return false;
    }

    @GetMapping("/dameSaldo/{id}")
    public Float dameSaldo(@PathVariable Long id) {
        return usuarioServ.dameSaldo(id);

    }

    @GetMapping("/rolAdmin/{id}")
    public boolean xRolAdmin(@PathVariable Integer id) {
        if (repo.xRol(id) == 'a') {
            return true;
        }
        return false;
    }

    @GetMapping("/rolEncargado/{id}")
    public boolean xRolEncargado(@PathVariable Integer id) {
        if (repo.xRol(id) == 'g') {
            return true;
        }
        return false;
    }

}
