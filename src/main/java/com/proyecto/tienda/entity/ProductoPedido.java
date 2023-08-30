package com.proyecto.tienda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//Con la anotacion @Entity se crea la entidad

@Entity
@Table(name = "PRODUCTOPEDIDO")
public class ProductoPedido {
	
	//Con la anotacion  @Id se define la llave primaria y con la anotacion @Column el nombre de las columnas de la entidad

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productoPedidoID;
	
	@Column(name = "CANTIDAD")
	private long cantidad;
	
	@Column(name = "PRECIO_PRODUCTO")
	private double precioProducto;

	//Se realiza la relacion ManyToOne entre las entidaddes producto y pedidos
	@ManyToOne
    @JoinColumn(name = "pedidoId")
	private Pedidos pedidoP;
	
	@ManyToOne
	@JoinColumn(name = "productoId")
	private Producto productoP;
	
	
	// Se generan los constructores

	public ProductoPedido() {
	}

	public ProductoPedido(Long productoPedidoID, Pedidos pedidoP, Producto producto, long cantidad, double precioProducto) {
		this.productoPedidoID = productoPedidoID;
		this.pedidoP = pedidoP;
		this.productoP = producto;
		this.cantidad = cantidad;
		this.precioProducto = precioProducto;
	}

	//Se generan los metodos getter y setter y toString

	public Long getProductoPedidoID() {
		return productoPedidoID;
	}

	public void setProductoPedidoID(Long productoPedidoID) {
		this.productoPedidoID = productoPedidoID;
	}

	public Pedidos getPedidoP() {
		return pedidoP;
	}

	public void setPedido(Pedidos pedido) {
		this.pedidoP = pedido;
	}

	public Producto getProducto() {
		return productoP;
	}

	public void setProducto(Producto producto) {
		this.productoP = producto;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

	public double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}

	@Override
	public String toString() {
		return "ProductoPedido [productoPedidoID=" + productoPedidoID + ", pedido=" + pedidoP + ", producto=" + productoP
				+ ", cantidad=" + cantidad + "]";
	}
	
	
}
