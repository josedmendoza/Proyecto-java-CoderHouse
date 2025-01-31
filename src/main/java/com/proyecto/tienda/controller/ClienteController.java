package com.proyecto.tienda.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.tienda.model.Cliente;
import com.proyecto.tienda.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

@Autowired	
private ClienteService clienteService;

	//Metodo para crear un cliente
	@PostMapping(value = "/crearcliente", consumes = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
    try {
		Cliente nuevoCliente =clienteService.crearCliente(cliente);
    	return ResponseEntity.ok(nuevoCliente);
    }catch (Exception e) {
		String mensaje = e.getMessage();
    	return ResponseEntity.badRequest().body("No se pudo crear el cliente"+ mensaje);
    	}
	}
	
	//Metodo para buscar un cliente
	@GetMapping(value = "/buscarcliente/{id}",produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<?> obtenerCliente(@PathVariable (name = "id") long idCliente){
		Optional<Cliente> buscarCliente = clienteService.buscarClientePorId(idCliente);
		if(buscarCliente.isPresent()) {
				return ResponseEntity.ok(buscarCliente);
			
			}else {
				return ResponseEntity.badRequest().body("{ \"Error\" : \"" + "Cliente no existe" + "\" }");
			}
	}
	
	//Metodo para actualizar cliente
	@PutMapping(value = "/actualizarcliente/{id}", consumes = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<?> actualizarCliente (@PathVariable (name = "id") long idCliente, @RequestBody Cliente cliente) {
			Optional<Cliente> buscarCliente = clienteService.buscarClientePorId(idCliente);
			if(buscarCliente.isPresent()) {
				try {
					Cliente clienteActualizado = clienteService.actualizarDatoCliente(idCliente, cliente);
				return ResponseEntity.ok( clienteActualizado);
				}catch(Exception e) {
					String mensaje = e.getMessage();
					return ResponseEntity.badRequest().body("No se pudo actualizar los datos del cliente" + " " + mensaje);
				}
			}else {
			
				return ResponseEntity.badRequest().body("{ \"Error\" : \"" + "Cliente no existe" + "\" }");
		}
	}
    

	
}
