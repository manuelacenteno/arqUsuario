package com.example.demo.Usuario.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Tarifa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private double tarifa_normal;

	@Column
	private double tarifa_extra;

	@Column
	private Date fecha_desde;

	/*@Column
	private Date fecha_hasta;*/

	// Constructor vacío
	public Tarifa() {
		super();
	}

	// Constructor con todos los campos
	public Tarifa(double valorN, double valorE, Date fecha_ds /* , Date fecha_hs*/) {

		this.tarifa_normal = valorN;
		this.tarifa_extra = valorE;
		this.fecha_desde = fecha_ds;
		//this.fecha_hasta = fecha_hs;
	}

	/* geters y seters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTarifaNormal(double tarifaN) {
		this.tarifa_normal = tarifaN;
	}

	public double getTarifaNormal() {
		return tarifa_normal;
	}

	public double getTarifaExtra() {
		return tarifa_extra;
	}

	public void setTarifaExtra(double tarifaE) {
		this.tarifa_extra = tarifaE;
	}

	public Date getFechaDesde() {
		return fecha_desde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fecha_desde = fechaDesde;
	}

	/*public Date getFechaHasta() {
		return fecha_hasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fecha_hasta = fechaHasta;
	}*/


	
}


