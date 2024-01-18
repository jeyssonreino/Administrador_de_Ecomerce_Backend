package com.example.ecomerce.service;

import com.example.ecomerce.dao.DetallePedidoDao;
import com.example.ecomerce.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DetallePedidoService {

    private final DetallePedidoDao detallePedidoDao;

    public DetallePedidoService(DetallePedidoDao detallePedidoDao) {
        this.detallePedidoDao = detallePedidoDao;
    }

    //Método para guardar un detalle de pedido en la base de datos
    public DetallePedido guardarDetallePedido(DetallePedido detallepedido){
        return detallePedidoDao.save(detallepedido);
    }
    //Método para buscar un detalle de pedido por su Id
    public Optional<DetallePedido> buscarDetallePedidoPorId(int id){
        return detallePedidoDao.findById(id);
    }

    //Método para obtener todos los detalles de pedidos
    public List<DetallePedido> obtenerTodosLosDetallesPedidos(){
        return detallePedidoDao.findAll();
    }

    //Método para eliminar un detalle de pedido por su Id
    public void eliminarDetallePedidoPorId(int id){
        detallePedidoDao.deleteById(id);
    }

    //Método para mostrar un detalle del pedido por Id con tablas cruzadas
    public DetallePedidoDTO obtenerDetallePedidoPorId(Integer id){
        return detallePedidoDao.obtenerIformacionDePedidoPorId(id);
    };

    //Método para mostrar todos los productos de un pedido en especifico por su Id del detalle del pedido
    public List<ProductosDelPedidoDTO> obtenerProductosDelPedidoPorId(Integer id){
        return detallePedidoDao.obtenerProductosDelPedidoPorId(id);
    };

    //Método para obtener el total del pedido mediante el Id del detalle del pedido
    public TotalDelPedidoDTO obtenerTotalDelDetalleDelPedido(Integer id){
        return detallePedidoDao.obtenerTotalDelPedido(id);
    };
}
