package com.example.ecomerce.controller;

import com.example.ecomerce.models.DetallePedido;
import com.example.ecomerce.models.DetallePedidoDTO;
import com.example.ecomerce.models.ProductosDelPedidoDTO;
import com.example.ecomerce.models.TotalDelPedidoDTO;
import com.example.ecomerce.service.DetallePedidoService;
import com.sun.mail.iap.ByteArray;
import jakarta.annotation.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DetallePedidoController {

    private DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    //Endpoint POST para registrar un DetallePedido en la base de datos
    @PostMapping("/registrar-detallepedido")
    public ResponseEntity<String> guardarDetallePedido(@RequestBody DetallePedido detallePedido){
        try{
            DetallePedido nuevoDetallePedido = detallePedidoService.guardarDetallePedido(detallePedido);
            return new ResponseEntity<>("Detalle del pedido guardado con exito", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Endpoint GET para obtener DetallePedido por su Id de la base de datos
    @GetMapping("/obtener-detallepedido/{id}")
    public ResponseEntity<DetallePedido> obtenerDetallePedidoPorId(@PathVariable int id){
        try{
            Optional<DetallePedido> detallePedidoOptional = detallePedidoService.buscarDetallePedidoPorId(id);
            if(detallePedidoOptional.isPresent()){
                DetallePedido detallePedido = detallePedidoOptional.get();
                return new ResponseEntity<>(detallePedido,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener todos los DetallePedido de la base de datos
    @GetMapping("/obtener-detallepedidos")
    public ResponseEntity<List<DetallePedido>> obtenerDetallePedidos(){
        try{
            List<DetallePedido> detallePedidos = detallePedidoService.obtenerTodosLosDetallesPedidos();
            if (!detallePedidos.isEmpty()){
                return new ResponseEntity<>(detallePedidos, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint DELETE para eliminar un DetallePedido por su Id de la base de datos
    @DeleteMapping("/eliminar-detallepedido/{id}")
    public ResponseEntity<String> eliminarDetallePedidoPorId(@PathVariable int id){
        try{
            detallePedidoService.eliminarDetallePedidoPorId(id);
            return new ResponseEntity<>("Detalle del pedido elimnado con exito", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar el detalle del pedido" + e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para editar un DetallePedido por su Id de la base de datos
    @PutMapping("/actualizar-detallepedido/{id}")
    public ResponseEntity<String> actualizarDetallePedido(@PathVariable int id, @RequestBody DetallePedido detallePedido){
        try{
            if (detallePedidoService.buscarDetallePedidoPorId(id).isPresent()){
                detallePedido.setId(id);
                detallePedidoService.guardarDetallePedido(detallePedido);
                return new ResponseEntity<>("Detalle del pedido actualizado correctamente",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("El detalle del pedido con Id: " + id + " no ha sido encontrado",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Error al actualizar el detalle del pedido" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Método get para obtener la informacion del detalle de un pedido con interseccion de tablas con el cliente mediante el Id del detalle del pedido
    @GetMapping("/obtenerDetallePedidoPorId/{id}")
    public ResponseEntity<DetallePedidoDTO> obtenerDetallePedidoPorId(@PathVariable Integer id){
        try{
            DetallePedidoDTO detallePedidoDTO = detallePedidoService.obtenerDetallePedidoPorId(id);
            if(detallePedidoDTO != null){
                return new ResponseEntity<>(detallePedidoDTO, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obtenerProductosDelPedidoPorId/{id}")
    public ResponseEntity<List<ProductosDelPedidoDTO>> obtenerProductosDelDetalleDelPedido(@PathVariable Integer id){
        try{
            List<ProductosDelPedidoDTO> productosDelPedidoDTO = detallePedidoService.obtenerProductosDelPedidoPorId(id);
            if (!productosDelPedidoDTO.isEmpty()){
                return new ResponseEntity<>(productosDelPedidoDTO,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obtenerTotalDelDetalleDelPedido/{id}")
    public ResponseEntity<TotalDelPedidoDTO> obtenerTotalDelDetalleDelPedido(@PathVariable Integer id){
        try{
            TotalDelPedidoDTO totalDelPedidoDTO = detallePedidoService.obtenerTotalDelDetalleDelPedido(id);
            if (totalDelPedidoDTO != null){
                return new ResponseEntity<>(totalDelPedidoDTO,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generar-factura/{id}")
    public ResponseEntity<ByteArrayResource> generarFacturaDePedido(@PathVariable Integer id){
        return this.detallePedidoService.generarFacturaByIde(id);
    }
}
