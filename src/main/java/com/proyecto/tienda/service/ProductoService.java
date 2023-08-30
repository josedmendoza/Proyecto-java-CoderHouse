package com.proyecto.tienda.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.tienda.entity.Producto;
import com.proyecto.tienda.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private  ProductoRepository productoRepository;

	//Metodo para crear un producto
	public Producto crearProducto (Producto producto) {
		return productoRepository.save(producto);
	}
	
	//Metodo para actulizar productos
	public Producto actualizarProducto (long idProducto, Producto producto) {
	Producto buscarProducto = productoRepository.findById(idProducto).
			orElseThrow(() -> new RuntimeException("Producto no encontrado, ID: " + idProducto));
	
	buscarProducto.setNombre(producto.getNombre());
	buscarProducto.setDescripcion(producto.getDescripcion());
	buscarProducto.setPrecio(producto.getPrecio());
	buscarProducto.setStock(producto.getStock());
	
	return productoRepository.save(buscarProducto);
	}
	
	//Metodo para actulizar precios de los productos no vendidos
		public Producto actualizarPrecioProducto (long idProducto, Producto producto) {
		Producto buscarProducto = productoRepository.findById(idProducto).
				orElseThrow(() -> new RuntimeException("Producto no encontrado, ID: " + idProducto));
		
		if(buscarProducto.getStock() != 0)
		buscarProducto.setPrecio(producto.getPrecio());
		return productoRepository.save(buscarProducto);
		}
		
	//Metodo para obtener producto
		public Producto obtenerProducto (long idProducto) {
			return productoRepository.findById(idProducto).
					orElseThrow(() -> new RuntimeException("Producto no encontrado,  ID: " + idProducto));
		}
		
	//Metodo para actualizar stock
		public Producto actualizarStock (Producto producto, int cantidadVendida){
			
				long nuevoStock = producto.getStock() - cantidadVendida;
				producto.setStock(nuevoStock);
				return productoRepository.save(producto);
		} 
		
		//Metodo para consultar Stock
			public List<Producto> consultarStock () {
			List<Producto> obtenerStock= productoRepository.findAll();
			return obtenerStock;
			
		}
}
