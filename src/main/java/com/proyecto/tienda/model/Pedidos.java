package com.proyecto.tienda.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//Con la anotacion @Entity se crea la entidad

@Entity
@Table(name = "PEDIDOS")
public class Pedidos {
	
	//Con la anotacion  @Id se define la llave primaria y con la anotacion @Column el nombre de las columnas de la entidad

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;
	
	@Column(name = "FECHA")
	private LocalDate fecha;
	
	@Column(name = "TOTAL")
	private double total;
	
	@Column(name = "CANTIDAD_TOTAL")
	private int cantidadProducto;
	
	// Se realiza la relacion ManyToOne con la entidad cliente
	
	@ManyToOne()
	@JoinColumn(name= "idCliente")
	private Cliente cliente;
	
	// Se realiza la relacion OneToMany con la entidad cliente

	@OneToMany(mappedBy = "pedidoP")
	private List<ProductoPedido> productoPedido;
	
	// Se generan los constructores

	public Pedidos() {}
	
	public Pedidos(Long idPedido, LocalDate fecha, double total, Cliente cliente, List<ProductoPedido> productoPedido, int cantidadProducto) {
		this.idPedido = idPedido;
		this.fecha = fecha;
		this.total = total;
		this.cliente = cliente;
		this.productoPedido = productoPedido;
		this.cantidadProducto = cantidadProducto;
	}


	//Se generan los metodos getter y setter y toString

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ProductoPedido> getProductoPedido() {
		return productoPedido;
	}

	public void setProductoPedido(List<ProductoPedido> productoPedido) {
		this.productoPedido = productoPedido;
	}

	@Override
	public String toString() {
		return "Pedidos [idPedido=" + idPedido + ", fecha=" + fecha + ", total=" + total + "]";
	}


}
