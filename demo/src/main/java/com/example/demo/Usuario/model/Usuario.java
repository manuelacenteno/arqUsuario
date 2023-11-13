package com.example.demo.Usuario.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Entity
public class Usuario implements UserDetails {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private Integer telefono;
    @Column
    private String email;
    @Column
    private String rol;
    @Column
    private String password;

    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
    )
    private List<Cuenta> cuentas;

    public Usuario(){
        super();
    }

    public Usuario(Integer id, String nombre, String apellido, Integer telefono, String email, String rol, String password, List<Cuenta> cuentas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
        this.password = password;
        this.cuentas = cuentas;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((this.rol)));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
