package com.example.demo.Usuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Parada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private Integer longitud;

	@Column
	private Integer latitud;

	// Constructor vacío
	public Parada() {
		super();
	}

	// Constructor con todos los campos
	public Parada(String name, Integer lon, Integer lat) {

		this.nombre = name;
		this.longitud = lon;
		this.latitud = lat;

	}

	// Getters y setters

	public double getId() {
		return id;
	}

	public void setId(Long valor) {
		this.id = valor;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setTLongitud(Integer valor) {
		this.longitud = valor;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setTLatitud(Integer valor) {
		this.latitud = valor;
	}

	public String getNombre() {
		return nombre;
	}

	// para tener una referencia de nombre de la parada 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}
