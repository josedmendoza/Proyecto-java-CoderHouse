package com.proyecto.tienda.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.tienda.entity.Cliente;
import com.proyecto.tienda.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	
//	Metodo para crear cliente en la base de dato
	public Cliente crearCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
//	Metodo para crear buscar un cliente en la base de dato
	public Cliente buscasClientePorId (long idCliente) {
		return clienteRepository.findById(idCliente).
				orElseThrow(() -> new RuntimeException("Cliente no encontrado,  ID: " + idCliente));
	}
	
//	Metodo para borrar cliente de la tabla cliente
	public void borrarCliente (long idCliente) {
		 clienteRepository.deleteById(idCliente);
	}
	
//	Metodo para actualizar datos del cliente en la tabla cliente 
	public Cliente actualizarDatoCliente (long idCliente, Cliente cliente) {
		Cliente buscarCliente = clienteRepository.findById(idCliente).
				orElseThrow(() -> new RuntimeException("Cliente no encontrada con ID: " + idCliente));
		buscarCliente.setNombre(cliente.getNombre());
		buscarCliente.setApellido(cliente.getApellido());
		buscarCliente.setDni(cliente.getDni());
		
		return clienteRepository.save(buscarCliente);
	}
	
	
	
	
}
