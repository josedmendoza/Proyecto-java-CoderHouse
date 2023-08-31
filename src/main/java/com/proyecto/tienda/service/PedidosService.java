package com.proyecto.tienda.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.tienda.model.Cliente;
import com.proyecto.tienda.model.Pedidos;
import com.proyecto.tienda.model.Producto;
import com.proyecto.tienda.model.ProductoPedido;
import com.proyecto.tienda.repository.ClienteRepository;
import com.proyecto.tienda.repository.PedidosRepository;
import com.proyecto.tienda.repository.ProductoRepository;

@Service
public class PedidosService {
	
	
	@Autowired
	private  ProductoRepository productoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private  PedidosRepository pedidosRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProductoPedidoServicio productoPedidoService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private FechaRestApi fechaRestApi;

	public LocalDate getFecha() {
		String response =  fechaRestApi.getFecha();
		
		//Formatea respuesta en pares para obtener el valor de la dateTime
		String[] lines = response.split("\n");
		Map<String, String> keyValueMap = new HashMap<>();

		for (String line : lines) {
		    String[] parts = line.split(": ", 2);
		        if (parts.length == 2) {
		            keyValueMap.put(parts[0], parts[1]);
		        }
		    }
		String datetimeValue = keyValueMap.get("datetime");

		datetimeValue = datetimeValue.replace(" ", "");

		// Ajustar el patrón para incluir espacio en blanco y el offset con dos puntos
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
		LocalDate localDate = LocalDate.parse(datetimeValue, formatter);
		
		return localDate;
	}
	
	//Metgrodo para crear el comprobante del pedido
	public Pedidos crearComprobante ( long idCliente, JSONArray productoPedidoJson) {
		
		Pedidos pedidos = new Pedidos();
		Cliente clientes = clienteService.buscasClientePorId(idCliente);
		pedidos.setCliente(clientes);
		pedidos.setFecha(getFecha());
		Pedidos guardarPedido = pedidosRepository.save(pedidos);
		
		int cantidadProducto = 0;
		double importeTotal = 0;
		List<ProductoPedido> listaProductoPedido = new ArrayList<>();
		for(int i= 0; i < productoPedidoJson.length();i++) {
		
			
			try {	
				//obtiene todo el JSON de entrada y obtiene los objtos cantidad, producto y id producto
				
				JSONObject productoJson =  productoPedidoJson.getJSONObject(i);
				int cantidad = productoJson.getInt("cantidad");
				JSONObject productoJ = productoJson.getJSONObject("producto");
				long productoId = productoJ.getLong("idProducto");
				
				Producto producto = productoService.obtenerProducto(productoId);
				ProductoPedido productoPedido = new ProductoPedido();
				productoPedido.setCantidad(cantidad);
				productoPedido.setProducto(producto);
				productoPedido.setPedido(guardarPedido);

				ProductoPedido productoPedidoG = productoPedidoService.crearListaProductoPorObjeto(cantidad, producto, pedidos);
				listaProductoPedido.add(productoPedidoG);
				
				// calculo de precio total de productos que se venden
				double precioProducto = producto.getPrecio();
				double importeProducto = cantidad * precioProducto;
				importeTotal = importeTotal + importeProducto; 
				
				//Calcular cantidad total de productos vendidos
				cantidadProducto = cantidadProducto + cantidad;
				
				//actualizar stock luego de la venta
				productoService.actualizarStock(producto, cantidad);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		guardarPedido.setTotal(importeTotal);
		guardarPedido.setCantidadProducto(cantidadProducto);
		
		return pedidosRepository.save(guardarPedido);
	}

	
	public String armarComprobante(Pedidos pedidoCreado) {
		
		try {
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
			venta.put("cantidadTotalVendida",pedidoCreado.getCantidadProducto());
			
			JSONArray listaProducto = new JSONArray();
			JSONArray listaStockProducto = new JSONArray();
	
			List<ProductoPedido> productosVendidos = new ArrayList<>();
	
			productosVendidos = productoPedidoService.listaProducto(pedidoCreado.getIdPedido());
	
			for(int i=0; i< productosVendidos.size(); i++) {
				JSONObject producto = new JSONObject();
				JSONObject stock = new JSONObject();
				stock.put("idProducto", productosVendidos.get(i).getProducto().getIdProducto());
				stock.put("stockActualizado", productosVendidos.get(i).getProducto().getStock());
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
			
			return venta.toString();
		}catch(JSONException e) {
			String error = e.getMessage();
			return error;
		}
	}
	
	//Metodo para validar el pedido antes de generar la venta 
	public boolean validarPedido(long idCliente, JSONArray productoPedidoJson) {
		
		boolean validacion = false;
		
		for(int i= 0; i < productoPedidoJson.length();i++) {
		
			try {	
				
				JSONObject productoJson =  productoPedidoJson.getJSONObject(i);
				int cantidad = productoJson.getInt("cantidad");
				JSONObject productoJ = productoJson.getJSONObject("producto");
				long productoId = productoJ.getLong("idProducto");
			
				Optional<Producto> producto = productoRepository.findById(productoId);
				Optional<Cliente> clientes = clienteRepository.findById(idCliente);
				if(clientes.isPresent()) {
					if (producto.isPresent()) {
						if ( cantidad  < producto.get().getStock()) {
							validacion = true;
						}else {validacion = false; break;}
					}else {validacion = false; break;}
				}else {validacion = false; break;}
	
			} catch (JSONException e) {}
		}
		return validacion;
	}
	
	public Optional<Pedidos> buscarPedidoPorId (long idPedido) {
		Optional<Pedidos> obtenerPedido = pedidosRepository.findById(idPedido);
		return obtenerPedido;
	}

}
