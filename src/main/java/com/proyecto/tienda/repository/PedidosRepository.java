package com.proyecto.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.tienda.entity.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository <Pedidos, Long> {

}
