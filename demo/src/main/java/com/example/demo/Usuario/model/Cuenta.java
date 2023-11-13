package com.example.demo.Usuario.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer mercado_pago;
    private Float saldo;
    private LocalDate fecha_alta;
    private boolean habilitada;
    @ManyToMany(mappedBy = "cuentas")
    private List<Usuario> usuarios;
    
    public Cuenta() {
    }
    public Cuenta(Integer mercado_pago, Float saldo, LocalDate fecha_alta) {
        this.mercado_pago = mercado_pago;
        this.saldo = saldo;
        this.fecha_alta = fecha_alta;
        this.habilitada=true;
        this.usuarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMercado_pago() {
        return mercado_pago;
    }

    public void setMercado_pago(Integer mercado_pago) {
        this.mercado_pago = mercado_pago;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(LocalDate fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public boolean isHabilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }
}
