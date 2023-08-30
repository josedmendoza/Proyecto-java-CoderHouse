package com.proyecto.tienda.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.proyecto.tienda.entity.Pedidos;
import com.proyecto.tienda.entity.ProductoPedido;
import com.proyecto.tienda.service.PedidosService;
import com.proyecto.tienda.service.ProductoPedidoServicio;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidosService pedidosService;
	
	@Autowired
	private ProductoPedidoServicio productoPedidoService;
	
	//Metodo para obtener fecha que es consumida de una ApiRest
	@GetMapping(value = "/obtenerFecha", produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity <?> getFecha() {
		try {
			LocalDate response = pedidosService.getFecha();
			return ResponseEntity.ok("{ \"Fecha\" : \"" + response + "\" }");	  
		}
		catch(Exception ex) {
			String mensaje = ex.getMessage();
			return ResponseEntity.badRequest().body("{ \"Error\" : \"" + mensaje + "\" }");
		}
	}
	
	//Metodo para crear comprobantes 
	@PostMapping(value = "/crearComprobante", consumes = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity <?> crearComprobante(@RequestBody String pedidoJson){
		
		try {
			//COMIENZA PROCESANDO EL INPUT
			//Convierte el String a JSON 
			JSONObject objetoJson = new JSONObject(pedidoJson);
			//Obtiene el objeto Cliente del JSON 
			JSONObject clienteJson = objetoJson.getJSONObject("cliente"); //cliente: {"id":1}
			long idCliente =  clienteJson.getLong("id");
	
			 //Obtiene el objeto Prodcuto del JSON 
			JSONArray productoPedidoJson = objetoJson.getJSONArray("productoPedido"); 
			
			// Se realiza la validacion de los datos recibido antes de crear el comprobante
			boolean validar =pedidosService.validarPedido(idCliente, productoPedidoJson);
			if(validar == true) {
			Pedidos pedidoCreado =pedidosService.crearComprobante(  idCliente, productoPedidoJson);

			
			//Se arma el body del Json donde se genera el comprobante
			JSONObject venta = new JSONObject();
			venta.put("idPedido", pedidoCreado.getIdPedido());
			venta.put("fecha", pedidoCreado.getFecha());
			JSONObject cliente = new JSONObject();
			cliente.put("idCliente", pedidoCreado.getCliente().getId());
			cliente.put("nombre", pedidoCreado.getCliente().getNombre());
			cliente.put("apellido", pedidoCreado.getCliente().getApellido());
			cliente.put("dni", pedidoCreado.getCliente().getDni());
			venta.put("cliente", cliente);
			venta.put("totalVenta", pedidoCreado.getTotal());
			
			JSONArray listaProducto = new JSONArray();
			JSONArray listaStockProducto = new JSONArray();

			List<ProductoPedido> productosVendidos = new ArrayList<>();

			productosVendidos = productoPedidoService.listaProducto(pedidoCreado.getIdPedido());

			for(int i=0; i< productosVendidos.size(); i++) {
				JSONObject producto = new JSONObject();
				JSONObject stock = new JSONObject();
				stock.put("idProducto", productosVendidos.get(i).getProducto().getIdProducto());
				stock.put("stock", productosVendidos.get(i).getProducto().getStock());
				producto.put("idProducto", productosVendidos.get(i).getProducto().getIdProducto());
				producto.put("nombreProducto", productosVendidos.get(i).getProducto().getNombre());
				producto.put("descripcionProducto", productosVendidos.get(i).getProducto().getDescripcion());
				producto.put("cantidadVendida", productosVendidos.get(i).getCantidad());
				producto.put("precioProducto", productosVendidos.get(i).getPrecioProducto());
				listaProducto.put(producto);
				listaStockProducto.put(stock);
			}
			venta.put("productosVendidos",listaProducto);
			venta.put("stock", listaStockProducto);
			
			return ResponseEntity.ok(venta.toString()) ;

			}else {
				return ResponseEntity.badRequest().body("Alta de pedido fallo: no se cumplieron con los requerimientos ");
			}
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body("Falla al generar pedido: no se cumplieron con los requerimientos ");
		}
		  
	}
		
}
