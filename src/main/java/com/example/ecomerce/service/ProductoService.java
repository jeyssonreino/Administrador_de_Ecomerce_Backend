package com.example.ecomerce.service;

import com.example.ecomerce.dao.ProductoDao;
import com.example.ecomerce.models.Producto;
import com.example.ecomerce.models.ProductoDTOA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    public final ProductoDao productoDao;

    public ProductoService(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    //Método para guardar un producto en la base de datos
    public Producto guardarProducto(Producto producto) {
        return productoDao.save(producto);
    }

    //Método para buscar un producto por su Id
    public Optional<Producto> buscarProductoPorId(int id) {
        return productoDao.findById(id);
    }

    //Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoDao.findAll();
    }

    //Método para eliminar un producto por su Id
    public void eliminarProductoPorId(int id) {
        productoDao.deleteById(id);
    }


    //Método para obtener todos los productos con los Foreing Keys de categoria y tienda
    public List<ProductoDTOA> obtenerProductosConForeingKeys() {
         return this.productoDao.ListarProductosConForeingKeys();
    }

}
