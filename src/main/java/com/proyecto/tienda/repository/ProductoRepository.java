package com.proyecto.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.tienda.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Long>  {

}
