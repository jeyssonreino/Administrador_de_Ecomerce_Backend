package com.example.ecomerce.dao;


import com.example.ecomerce.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDao extends JpaRepository<Pedido, Integer> {
}
