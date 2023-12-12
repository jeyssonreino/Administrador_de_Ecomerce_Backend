package com.example.ecomerce.controller;


import com.example.ecomerce.models.Producto;
import com.example.ecomerce.models.ProductoDTOA;
import com.example.ecomerce.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductoController {

    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //Endpoint POST para guardar un producto en la base de datos
    @PostMapping("/registrar-producto")
    public ResponseEntity<String> guardarProducto(@RequestBody Producto producto) {
        try {
            Producto nuevaProducto = productoService.guardarProducto(producto);
            return new ResponseEntity<>("Producto guardado con exito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el producto: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para buscar un producto registrado por su Id en la base de datos
    @GetMapping("/obtener-producto/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        try {
            Optional<Producto> productoOptional = productoService.buscarProductoPorId(id);
            if (productoOptional.isPresent()) {
                Producto producto = productoOptional.get();
                return new ResponseEntity<>(producto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener todos los productos registrados en la base de datos
    @GetMapping("/obtener-productos")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        try {
            List<Producto> productos = productoService.obtenerTodosLosProductos();
            if (!productos.isEmpty()) {
                return new ResponseEntity<>(productos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint DELETE para eliminar un producto registrado en la base de datos por su Id
    @DeleteMapping("/eliminar-producto/{id}")
    public ResponseEntity<String> eliminarProductoPorId(@PathVariable int id) {
        try {
            productoService.eliminarProductoPorId(id);
            return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el producto: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para eidtar un producto registrado en la base de datos por su Id
    @PutMapping("/actualizar-producto/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        try {
            if (productoService.buscarProductoPorId(id).isPresent()) {
                producto.setId(id);
                productoService.guardarProducto(producto);
                return new ResponseEntity<>("Producto actalizado correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El producto con Id: " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Endpoint GET para obtener todos los productos con Foreing Keys de categoria y de tienda registrados en la base de datos
    @GetMapping("/obtener-productosfk")
    public ResponseEntity<List<ProductoDTOA>> obtenerProductosConForeingKeys() {
        try {
            List<ProductoDTOA> productos = productoService.obtenerProductosConForeingKeys();
            if (!productos.isEmpty()) {
                return new ResponseEntity<>(productos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
