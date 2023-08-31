package com.proyecto.tienda.controller;


import java.time.LocalDate;

import java.util.Optional;

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

import com.proyecto.tienda.model.Cliente;
import com.proyecto.tienda.model.Pedidos;
import com.proyecto.tienda.service.ClienteService;
import com.proyecto.tienda.service.PedidosService;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidosService pedidosService;
	
	@Autowired
	private ClienteService	clienteService;
	
	
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
	
			Optional<Cliente> buscarCliente = clienteService.buscarClientePorId(idCliente);
			if(buscarCliente.isPresent()) {
				
				JSONArray productoPedidoJson = objetoJson.getJSONArray("productoPedido"); 
	
				boolean validar =pedidosService.validarPedido(idCliente, productoPedidoJson);
				if(validar == true) {
					Pedidos pedidoCreado =pedidosService.crearComprobante(  idCliente, productoPedidoJson);
	
					String comprobante = pedidosService.armarComprobante(pedidoCreado);
			
					return ResponseEntity.ok(comprobante);

				}else {
					return ResponseEntity.badRequest().body("Alta de pedido fallo: no se cumplieron con los requerimientos ");
				}
			}else {
				return ResponseEntity.badRequest().body("{ \"Error\" : \"" + "Cliente no existe" + "\" }");
			}
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body("Falla al generar pedido: no se cumplieron con los requerimientos ");
			}  
	}
		
}
