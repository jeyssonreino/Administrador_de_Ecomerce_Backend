package com.example.ecomerce.service;

import com.example.ecomerce.dao.DetallePedidoDao;
import com.example.ecomerce.models.DetallePedido;
import com.example.ecomerce.models.Pedido;
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
}
