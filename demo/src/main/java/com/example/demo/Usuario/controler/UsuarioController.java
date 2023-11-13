package com.example.demo.Usuario.controler;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.Usuario.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JWTService jwtService;

    @GetMapping("/verificarToken/{token}")
    public String verificarToken(@PathVariable String token) {
        return jwtService.verificarToken(token);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

    @GetMapping("/anularCuenta/{idCuenta}")
    public String anularCuenta(@RequestHeader("Authorization") String authorization,
                               @PathVariable Long idUsuario, @PathVariable Long idCuenta) {

        // Busca usuario y comprueba si es administrador
        if (esAdmin(authorization)) {
            String intentarAnularCuenta = cuentaServicio.anularCuenta(idCuenta);
            return intentarAnularCuenta;
        }

        return "Necesitas permisos de administrador.";
    }

    @GetMapping("/reporteMonopatines/{cantViajes}/{anio}")
    public List<ReporteMonopatinesPorViajeDTO> obtenerReportePorViaje(
            @RequestHeader("Authorization") String authorization, @PathVariable int cantViajes, @PathVariable int anio) {
        if (esAdmin(authorization)) {
            List<ReporteMonopatinesPorViajeDTO> reporte = viajeServicio.obtenerReportePorViaje(cantViajes, anio);
            return reporte;
        }
        return null;
    }

    @GetMapping("/totalFacturadoEnRangoDeMeses/{mesInicio}/{mesFin}/{anio}")
    public String obtenerTotalFacturadoEnRangoDeMeses(
            @RequestHeader("Authorization") String authorization,
            @PathVariable int mesInicio, @PathVariable int mesFin, @PathVariable int anio) {
        if (esAdmin(authorization)) {
            Double totalFacturado = viajeServicio.getTotalFacturadoEnRangoDeMeses(mesInicio, mesFin, anio);
            return "El total facturado fue:" + totalFacturado;
        }
        return "Necesitas permisos de administrador.";
    }

    @GetMapping("/cantidadMonopatines")
    public Map<String, Integer> obtenerMonopatinesEnTaller(@RequestHeader("Authorization") String authorization) {
        if (esAdmin(authorization)) {
            return monoServicio.obtenerMonopatinesEnTaller();
        }
        return null;
    }

    @GetMapping("/ajustarTarifa")
    public String ajustarTarifa(@RequestHeader("Authorization") String authorization, @RequestBody Tarifa tarifa) {
        if (esAdmin(authorization)) {
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

    @GetMapping("/dameSaldo/{idCuenta}")
    public Float dameSaldo(@PathVariable Long idCuenta) {
        return usuarioServ.dameSaldo(idCuenta);
    }

    private boolean esAdmin(String authorization) {
        String tokenSinBearer = authorization.substring(7);
        if (jwtService.isAdmin(tokenSinBearer))
            return true;
        return false;
    }
}
