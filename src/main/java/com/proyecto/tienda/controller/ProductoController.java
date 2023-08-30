package com.proyecto.tienda.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.tienda.entity.Producto;
import com.proyecto.tienda.service.ProductoPedidoServicio;
import com.proyecto.tienda.service.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ProductoPedidoServicio productoPedidoService;
	
	//Metodo para crear un producto
	@PostMapping (value = "/crearproducto", consumes = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity <?> crearProducto(@RequestBody Producto producto){
		try {
			Producto nuevoProducto = productoService.crearProducto(producto);
			return ResponseEntity.ok(nuevoProducto);
		}catch(Exception e) {
			String mensaje = e.getMessage();
			return ResponseEntity.badRequest().body("No se pudo agregar el error correctamente" + " " + mensaje);
		}
	}
	
	//Metodo para obtener un producto
	@GetMapping(value = "/buscarproducto/{id}",produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<?> obtenerProducto(@PathVariable (name = "id") long idProducto){
		try {
			Producto buscarProducto = productoService.obtenerProducto(idProducto);
			return ResponseEntity.ok( buscarProducto );
		}catch(Exception e) {
			String mensaje = e.getMessage();
			return ResponseEntity.badRequest().body("No se pudo buscar el producto" + " " + mensaje);
		}
	}
	
	//Metodo para actualizar producto
		@PutMapping(value = "/actualizarproducto/{id}", consumes = (MediaType.APPLICATION_JSON_VALUE))
		public ResponseEntity<?> actualizarProducto (@PathVariable (name = "id") long idProducto, @RequestBody Producto producto) {
			try {
				Producto actualizadoProducto = productoService.actualizarProducto(idProducto, producto);
			return ResponseEntity.ok(actualizadoProducto);
			}catch(Exception e) {
				String mensaje = e.getMessage();
				return ResponseEntity.badRequest().body("No se pudo actualizar el producto" + " " + mensaje);
			}
		}
		
	//Metodo para actualizar precio de un producto
			@PutMapping(value = "/actualizarprecio/{id}", consumes = (MediaType.APPLICATION_JSON_VALUE))
			public ResponseEntity<?> actualizarPrecioProducto (@PathVariable (name = "id") long idProducto, @RequestBody Producto producto) {
				try {
					boolean existeProducto = productoPedidoService.validarProductoVendido(idProducto);
					if(existeProducto == false) {
					Producto actualizadoPrecioProducto = productoService.actualizarPrecioProducto(idProducto, producto);
				return ResponseEntity.ok( actualizadoPrecioProducto);
					}else {
						return ResponseEntity.badRequest().body("No se pudo actualizar el precio del producto porque pertenece a un pedido");
					}
				}catch(Exception e) {
					String mensaje = e.getMessage();
					return ResponseEntity.badRequest().body("No se pudo actualizar el precio del producto" + " " + mensaje);
					}
			}
			
			//Metodo para obtener el stock de productos  
			@GetMapping(value = "/consultarStock",produces = (MediaType.APPLICATION_JSON_VALUE))
			public ResponseEntity<?> consultarStock (){
				try {
					List<Producto> obtenerStock = productoService.consultarStock();
					return ResponseEntity.ok( obtenerStock );
				}catch(Exception e) {
					String mensaje = e.getMessage();
					return ResponseEntity.badRequest().body("No se pudo consultar stock de producto existentes" + " " + mensaje);
				}
			}
		
}
