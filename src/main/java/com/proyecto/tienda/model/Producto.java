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
@Table(name = "PRODUCTO")
public class Producto {
	
	//Con la anotacion  @Id se define la llave primaria y con la anotacion @Column el nombre de las columnas de la entidad
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProducto;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "STOCK")
	private long stock;
	
	@Column(name = "PRECIO")
	private double precio;
	
	//Se realiza la relacion OneToMany con la entidad ProductoPedido
	
	@OneToMany(mappedBy = "productoP")
	List<ProductoPedido> productoPedido;
	
	// Se generan los constructores
	
	public Producto() {}
	
	public Producto(long idProducto, String nombre, String descripcion,long stock, long precio) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.stock = stock;
		this.precio = precio;
	}
	
	//Se generan los metodos getter y setter y toString

	public long getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion + ", stock="
				+ stock + ", precio=" + precio + "]";
	}
	

}
