package com.example.demo.Usuario.controler;

import com.example.demo.Usuario.model.Cuenta;
import com.example.demo.Usuario.repository.CuentaRepo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaRepo repo;

    @GetMapping
    @Operation(summary = "Lista cuentas", description = "FindAll de las cuentas en el repositorio")
    public List<Cuenta> dameCuentas() {
        return repo.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear cuenta", description = "Obtiene una cuenta por cuerpo y la guarda en el repositorio")
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        return repo.save(cuenta);
    }

    @GetMapping("/tieneSaldo/{id}")
    @Operation(summary = "Tiene saldo (cantidad)", description = "Si la cuenta esta deshabilitada devuelve -1, de lo contrario devuelve la cantidad")
    public Float tieneSaldo(@PathVariable Long id) {
        Cuenta cuenta = repo.findById(id).get();
        Float saldo = cuenta.getSaldo();
        if (cuenta.isHabilitada())
            return saldo;
        else
            return (float) -1;

    }

    @PutMapping("/descontarCostoViaje/{idCuenta}")
    @Operation(summary = "Descontar saldo tras viaje", description = "Busca la cuenta y le descuenta el costo del viaje")
    public Cuenta descontarSaldo(@PathVariable Long idCuenta, @RequestBody Double costoViaje) {
        Cuenta cuenta = repo.findById(idCuenta).orElse(null);
        System.out.println(costoViaje);
        Float restarSaldo = (float) (cuenta.getSaldo() - costoViaje);
        cuenta.setSaldo(restarSaldo);
        return repo.save(cuenta);
    }

    @PutMapping("/anularCuenta/{idCuenta}")
    @Operation(summary = "Deshabilitar cuenta", description = "Busca cuenta por id y la deshabilita (solo admins)")
    public String anularCuenta(@PathVariable Long idCuenta) {
        Cuenta cuenta = repo.findById(idCuenta).orElse(null);
        if (cuenta!=null) {
            cuenta.setHabilitada(false);
            repo.save(cuenta);
            return "Cuenta deshabilitada con exito";
        }
        return "No se encontro una cuenta con ese id";
    }
}
