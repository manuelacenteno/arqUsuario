package com.example.demo.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Usuario.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.rol FROM Usuario u WHERE  idUsuario = :id")
	public char xRol(Integer id);

    Optional<Usuario> findByNombre(String nombre);



}
