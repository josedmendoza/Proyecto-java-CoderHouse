package com.proyecto.tienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.tienda.model.Pedidos;
import com.proyecto.tienda.model.ProductoPedido;
import com.proyecto.tienda.service.PedidosService;
import com.proyecto.tienda.service.ProductoPedidoServicio;

@Controller
@RequestMapping("/ProductoPedido")
public class ProductoPedidoController {
	
	@Autowired
	private ProductoPedidoServicio productoPedidoServicio;
	
	@Autowired
	private PedidosService pedidoService; 

	//Metodo para consultar todos los productos de un pedido
	@GetMapping(value = "/obtenerProductosPorPedido/{id}",produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<?> obtenerProductosPorPedido(@PathVariable (name = "id") long idPedido) {
		Optional<Pedidos> obtenerPedido = pedidoService.buscarPedidoPorId(idPedido);
		if(obtenerPedido.isPresent()) {
			List<ProductoPedido> obtenerListaProductoPedido = productoPedidoServicio.listaProducto(idPedido);
			return ResponseEntity.ok(obtenerListaProductoPedido.toString()); 
		}else {
			return ResponseEntity.badRequest().body("{ \"Error\" : \"" + "No se encontro pedido" + "\" }");
		}
		
	}
	
}
