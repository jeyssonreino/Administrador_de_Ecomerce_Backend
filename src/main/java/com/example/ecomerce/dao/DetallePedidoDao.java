package com.example.ecomerce.dao;

import com.example.ecomerce.models.DetallePedido;
import com.example.ecomerce.models.DetallePedidoDTO;
import com.example.ecomerce.models.ProductosDelPedidoDTO;
import com.example.ecomerce.models.TotalDelPedidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetallePedidoDao extends JpaRepository<DetallePedido, Integer> {

    // Método para obtener la informacion del pedido,cliente mediante el Id del detalle pedido pedido
    @Query(value = "SELECT dp.id AS id,\n" +
            "dp.fecha_pedido AS fechaPedido,\n" +
            "dp.hora_pedido AS horaPedido,\n" +
            "dp.forma_de_pago AS formaPago,\n" +
            "dp.ciudad AS ciudad,\n" +
            "dp.direccion AS direccion,\n" +
            "dp.estado AS estado,\n" +
            "dp.id_usuario AS idUsuario,\n" +
            "CONCAT(u.nombre1,\" \",u.nombre2,\" \",u.apellido1,\" \",apellido2) AS nombreCompleto,\n" +
            "u.telefono AS telefono,\n" +
            "u.correo AS correo\n" +
            "FROM detalle_pedido dp \n" +
            "INNER JOIN usuario u ON u.id = dp.id_usuario\n" +
            "WHERE dp.estado = \"Comprado\" AND dp.id = :id",nativeQuery = true)
    DetallePedidoDTO obtenerIformacionDePedidoPorId(@Param("id") Integer id);

    //Método para obtener los productos comprados de un detalle del pedido mediante el Id del detalle pedido
    @Query(value = "SELECT p.id AS id,\n" +
            "p.nombre AS nombre,\n" +
            "p.descuento AS descuento,\n" +
            "p.precio AS precio,\n" +
            "FORMAT(p.precio -((p.precio * p.descuento)/100),2) AS descuentoProducto\n" +
            "FROM producto p \n" +
            "INNER JOIN pedido pe ON pe.id_producto = p.id\n" +
            "INNER JOIN detalle_pedido dp ON pe.id_detallepedido = dp.id  WHERE dp.estado = \"Comprado\" AND dp.id = :id", nativeQuery = true)
    List<ProductosDelPedidoDTO> obtenerProductosDelPedidoPorId(@Param("id") Integer id);

    //Método para obtener el total de precios teniendo en cuenta el descuento de estos de un detalle del pedido en especifico mediante su Id
    @Query(value = "SELECT FORMAT(SUM(p.precio -((p.precio * p.descuento)/100)),2) AS Total\n" +
            "FROM producto p \n" +
            "INNER JOIN pedido pe ON pe.id_producto = p.id\n" +
            "INNER JOIN detalle_pedido dp ON pe.id_detallepedido = dp.id  WHERE dp.estado = \"Comprado\" AND dp.id = :id", nativeQuery = true)
    TotalDelPedidoDTO obtenerTotalDelPedido(@Param("id") Integer id);




}
