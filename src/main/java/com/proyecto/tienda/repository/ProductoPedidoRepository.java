package com.proyecto.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.tienda.entity.ProductoPedido;

@Repository
public interface ProductoPedidoRepository extends JpaRepository <ProductoPedido, Long> {
	

}