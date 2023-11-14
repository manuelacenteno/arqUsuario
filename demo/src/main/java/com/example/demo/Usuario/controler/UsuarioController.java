package com.example.demo.Usuario.controler;

import java.util.List;
import java.util.Map;

import com.example.demo.Usuario.jwt.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Usuario.dto.ReporteMonopatinesPorViajeDTO;
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
    @Operation(summary = "Verificar token", description = "Le llega un token en forma de string y si es valido devuelve el rol del usuario")
    public String verificarToken(@PathVariable String token) {
        return jwtService.verificarToken(token);
    }

    @GetMapping
    @Operation(summary = "Lista usuarios", description = "FindAll de los usuarios en el repositorio")
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Recibe un usuario por el body y lo guarda en el repositorio")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

    @GetMapping("/anularCuenta/{idCuenta}")
    @Operation(summary = "Anula/Deshabilita cuenta", description = "Confirma si el token del solicitante es de un admin y deshabilita la cuenta")
    public String anularCuenta(@RequestHeader("Authorization") String authorization, @PathVariable Long idCuenta) {

        // Busca usuario y comprueba si es administrador
        if (esAdmin(authorization)) {
            String intentarAnularCuenta = cuentaServicio.anularCuenta(idCuenta);
            return intentarAnularCuenta;
        }

        return "Necesitas permisos de administrador.";
    }

    @GetMapping("/reporteMonopatines/{cantViajes}/{anio}")
    @Operation(summary = "Reporte de monopatines por viaje y año", description = "Confirma si es admin y devuelve un reporte en forma de DTO")
    public List<ReporteMonopatinesPorViajeDTO> obtenerReportePorViaje(
            @RequestHeader("Authorization") String authorization, @PathVariable int cantViajes, @PathVariable int anio) {
        if (esAdmin(authorization)) {
            List<ReporteMonopatinesPorViajeDTO> reporte = viajeServicio.obtenerReportePorViaje(cantViajes, anio, authorization);
            return reporte;
        }
        return null;
    }

    @GetMapping("/totalFacturadoEnRangoDeMeses/{mesInicio}/{mesFin}/{anio}")
    @Operation(summary = "Total facturado en un rango de 2 meses", description = "Confirma si es admin y devuelve se comunica con 'Viaje' que devuelve el total facturado")
    public String obtenerTotalFacturadoEnRangoDeMeses(
            @RequestHeader("Authorization") String authorization,
            @PathVariable int mesInicio, @PathVariable int mesFin, @PathVariable int anio) {
        if (esAdmin(authorization)) {
            Double totalFacturado = viajeServicio.getTotalFacturadoEnRangoDeMeses(mesInicio, mesFin, anio, authorization);
            return "El total facturado fue:" + totalFacturado;
        }
        return "Necesitas permisos de administrador.";
    }

    @GetMapping("/cantidadMonopatines")
    @Operation(summary = "Reporte de monopatines en taller y en funcionamiento", description = "Confirma si es admin y devuelve con un map: Estado del monopatin, cantidad de monopatines")
    public Map<String, Integer> obtenerMonopatinesEnTaller(@RequestHeader("Authorization") String authorization) {
        if (esAdmin(authorization)) {
            return monoServicio.obtenerMonopatinesEnTaller(authorization);
        }
        return null;
    }

    @GetMapping("/ajustarTarifa")
    @Operation(summary = "Ajusta tarifa", description = "Confirma si es admin y se comunica con el servicio 'Tarifa' enviandole en el cuerpo una tarifa para que la guarde en su bbdd")
    public String ajustarTarifa(@RequestHeader("Authorization") String authorization, @RequestBody Tarifa tarifa) {
        if (esAdmin(authorization)) {
            tarifaServicio.aplicarTarifa(tarifa, authorization);
            return "Tarifa aplicada";
        }
        return "El usuario no es admin";
    }

    @GetMapping("/paradasCercanas/{latitud}/{longitud}")
    @Operation(summary = "Obtener paradas cercanas", description = "Se comunica con el servicio 'Parada' para obtener un listado de las paradas mas cercanas")
    public List<Parada> obtenerParadasCercanas(@RequestHeader("Authorization") String authorization,
                                               @PathVariable Double latitud, @PathVariable Double longitud) {
        List<Parada> paradas = paradasServicio.getParadas(latitud, longitud, authorization);
        return paradas;
    }

    // TODO: Segurizar cuando la solicitud entrante este configurada para envíar header.
    @GetMapping("/dameSaldo/{idCuenta}")
    public Float dameSaldo(@PathVariable Long idCuenta) {
        return cuentaServicio.dameSaldo(idCuenta);
    }

    private boolean esAdmin(String authorization) {
        String tokenSinBearer = authorization.substring(7);
        if (jwtService.isAdmin(tokenSinBearer))
            return true;
        return false;
    }
}
