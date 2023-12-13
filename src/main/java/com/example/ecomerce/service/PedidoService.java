package com.example.ecomerce.service;

import com.example.ecomerce.dao.PedidoDao;
import com.example.ecomerce.models.CarritoDTO;
import com.example.ecomerce.models.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PedidoService {

    private final PedidoDao pedidoDao;

    public PedidoService(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }
    //Mérodo para guardar un pedido en la base de datos
    public Pedido guardarPedido(Pedido pedido){


        return pedidoDao.save(pedido);
    }

    //Método para buscar un pedido por su Id
    public Optional<Pedido> buscarPedidoPorId(int id){
        return pedidoDao.findById(id);
    }

    //Método para obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos(){
        return pedidoDao.findAll();
    }

    //Método para eliminar un pedido por su Id
    public void eliminarPedidoPorId(int id){
        pedidoDao.deleteById(id);
    }

    //Método para obtener un listado de productos para el carrito por Id de usuario
    public List<CarritoDTO> obtenerCarritoPorIdUser(int idUser){return pedidoDao.ListarCarritoPorIdUser(idUser);}


}
