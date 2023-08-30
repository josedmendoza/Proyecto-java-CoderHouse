package com.proyecto.tienda.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FechaRestApi {

	//Metodo para consumir un servicio de una apiRest
	public String getFecha(){
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://worldtimeapi.org/api/ip";
		return restTemplate.getForObject(url, String.class);
	}
	
		 

	
}
