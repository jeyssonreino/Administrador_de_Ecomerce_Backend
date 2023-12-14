package com.example.ecomerce.dao;


import com.example.ecomerce.models.CarritoDTO;
import com.example.ecomerce.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoDao extends JpaRepository<Pedido, Integer> {

    //Método para obtener los pedidos y mostralos el carrito por el Id del usuario
    @Query(value = "SELECT pe.id AS idPedido,\n" +
            "pe.id_detallepedido AS idDetallePedido,\n" +
            "pe.id_producto AS idProducto,\n" +
            "pe.id_usuario AS idUsuario,\n" +
            "p.nombre AS nombreProducto,\n" +
            "p.precio AS precioProducto,\n" +
            "p.descuento AS descuentoProducto,\n" +
            "p.imagen_principal AS imagenPrincipal\n" +
            "FROM pedido pe INNER JOIN producto p ON pe.id_producto = p.id\n" +
            "WHERE pe.id_usuario = :idUser", nativeQuery = true)
    List<CarritoDTO> ListarCarritoPorIdUser(@Param("idUser") int idUser);


}
