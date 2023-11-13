package com.example.demo.Usuario.controler;

import com.example.demo.Usuario.model.Cuenta;
import com.example.demo.Usuario.repository.CuentaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaRepo repo;

    @GetMapping
    public List<Cuenta> dameCuentas() {
        return repo.findAll();
    }

    @GetMapping("/tieneSaldo/{id}")
    public Float tieneSaldo(@PathVariable Long id) {
        Cuenta cuenta = repo.findById(id).get();
        Float saldo = cuenta.getSaldo();
        if (cuenta.isHabilitada())
            return saldo;
        else
            return (float) -1;

    }

    @PostMapping
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        return repo.save(cuenta);
    }

    @PutMapping("/descontarCostoViaje/{idCuenta}")
    public Cuenta descontarSaldo(@PathVariable Long idCuenta, @RequestBody Double costoViaje) {
        Cuenta cuenta = repo.findById(idCuenta).orElse(null);
        System.out.println(costoViaje);
        Float restarSaldo = (float) (cuenta.getSaldo() - costoViaje);
        cuenta.setSaldo(restarSaldo);
        return repo.save(cuenta);
    }

    @PutMapping("/anularCuenta/{idCuenta}") 
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
