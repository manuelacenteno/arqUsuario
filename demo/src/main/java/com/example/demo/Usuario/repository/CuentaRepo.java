package com.example.demo.Usuario.repository;

import com.example.demo.Usuario.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepo extends JpaRepository<Cuenta,Long>{
    
}
