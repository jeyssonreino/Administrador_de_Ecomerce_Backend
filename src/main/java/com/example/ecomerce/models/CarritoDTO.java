package com.example.ecomerce.models;

public interface CarritoDTO {

    int getIdPedido();
    int getIdProducto();
    int getIdUsuario();
    String getNombreProducto();
    double getPrecioProducto();
    double getDescuentoProducto();
    String getImagenPrincipal();
}
