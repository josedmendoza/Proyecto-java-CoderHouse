package com.proyecto.tienda.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.tienda.model.Pedidos;
import com.proyecto.tienda.model.Producto;
import com.proyecto.tienda.model.ProductoPedido;
import com.proyecto.tienda.repository.ProductoPedidoRepository;


@Service
public class ProductoPedidoServicio {

	@Autowired
	private  ProductoPedidoRepository productoPedidoRepository;
	
	
	
	//Metodo para crear lista de productos que se van a comprar
	public ProductoPedido crearListaProductoPorObjeto(int cantidad, Producto producto,  Pedidos pedido) {
	
		ProductoPedido productoPedido = new ProductoPedido();
		productoPedido.setCantidad(cantidad);
		productoPedido.setPedido(pedido);
		productoPedido.setProducto(producto);
		productoPedido.setPrecioProducto(producto.getPrecio());
		return productoPedidoRepository.save(productoPedido);
	}
	
	//Metodo para buscar la lista de todos los productos por pedido
	public List<ProductoPedido> listaProducto (long idPedido){
		List<ProductoPedido> listarProducto = new ArrayList<>();
		List<ProductoPedido> buscarProducto =  productoPedidoRepository.findAll();
		for(int i=0; i< buscarProducto.size(); i++) {
			if(idPedido == buscarProducto.get(i).getPedidoP().getIdPedido()) {
				listarProducto.add(buscarProducto.get(i));
			}
		
		}
		return listarProducto;
	}
	
	//metodo para validar si el producto existe antes de generar la venta
	public boolean  validarProductoVendido(long idProducto) {
		List<ProductoPedido> buscarProducto =  productoPedidoRepository.findAll();
		boolean existeProducto = false;
		for(int i=0; i< buscarProducto.size(); i++) {
			if(idProducto == buscarProducto.get(i).getProducto().getIdProducto()) {
				existeProducto = true;
			}
		} 
		return existeProducto;
	}		
}