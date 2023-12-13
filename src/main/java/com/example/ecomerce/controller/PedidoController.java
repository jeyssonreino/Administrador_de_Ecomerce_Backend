package com.example.ecomerce.controller;


import com.example.ecomerce.models.CarritoDTO;
import com.example.ecomerce.models.Pedido;
import com.example.ecomerce.models.UsuarioDTOA;
import com.example.ecomerce.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    //Endpoint POST para guardar o registrar un Pedido en la base de datos
    @PostMapping("/registrar-pedido")
    public ResponseEntity<String> guardarPedido(@RequestBody Pedido pedido){
        try{
            Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
            return new ResponseEntity<>("Pedido registrado con exito",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar el pedido" + e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener un pedido por su Id de la base de datos
    @GetMapping("/obtener-pedido/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable int id){
        try{
            Optional<Pedido> pedidoOptional = pedidoService.buscarPedidoPorId(id);
            if (pedidoOptional.isPresent()){
                Pedido pedido = pedidoOptional.get();
                return new ResponseEntity<>(pedido,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Enpoint GET para obtener todos los pedidos de la base de datos
    @GetMapping("/obtener-pedidos")
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
            if (!pedidos.isEmpty()) {
                return new ResponseEntity<>(pedidos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint DELETE para eliminar un pedido por su Id en la base de datos
    @DeleteMapping("/eliminar-pedido/{id}")
    public ResponseEntity<String> eliminarPedidoPorId(@PathVariable int id){
        try{
            pedidoService.eliminarPedidoPorId(id);
            return new ResponseEntity<>("Pedido eliminado con exito", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para editar un pedido por su Id en la base de datos
    @PutMapping("/actualizar-pedido/{id}")
    public ResponseEntity<String> actualizarPedidoPorId(@PathVariable int id, @RequestBody Pedido pedido){
        try {
            if (pedidoService.buscarPedidoPorId(id).isPresent()){
                pedido.setId(id);
                pedidoService.guardarPedido(pedido);
                return new ResponseEntity<>("Pedido actualizado correctamente", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("El pedido con Id: " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener el carrito por Id del usuario
    @GetMapping("/obtener-carrito/{idUser}")
    public ResponseEntity<List<CarritoDTO>> obtenerCarritoPorIdUsuario(@PathVariable int idUser){
        try{
            List<CarritoDTO> carrito = pedidoService.obtenerCarritoPorIdUser(idUser);
            if (!carrito.isEmpty()){
                return new ResponseEntity<>(carrito, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
