package com.proyecto.tienda.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


//Con la anotacion @Entity se crea la entidad
@Entity
@Table(name = "CLIENTE")
public class Cliente {
	
	//Con la anotacion  @Id se define la llave primaria y con la anotacion @Column el nombre de las columnas de la entidad
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CLIENTE")
	private Integer idCliente;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "APELLIDO")
	private String apellido;
	
	@Column(name = "DNI")
	private long dni;
	
	
	
	//Se realiza la relacion OneToMany con la entidad Pedidos

	@OneToMany(mappedBy = "cliente")
	 List<Pedidos> listapedidos;
	
	// Se generan los constructores
	public Cliente() {}


	public Cliente(Integer id, String nombre, String apellido, long dni) {
		this.idCliente = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		
	}

	//Se generan los metodos getter y setter y toString
	public Integer getId() {
		return idCliente;
	}


	public void setId(Integer id) {
		this.idCliente = id;
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


	public long getDni() {
		return dni;
	}


	public void setDni(long dni) {
		this.dni = dni;
	}


	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
				+ ", listapedidos=" + listapedidos + "]";
	}



}
