package com.example.ecomerce.dao;

import com.example.ecomerce.models.Producto;
import com.example.ecomerce.models.ProductoDTOA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Integer> {

    //Método para listar los productos de la base de datos con informacón de sus Llaver Foraneas

    @Query(value ="SELECT\n" +
            " p.id AS id,\n" +
            " p.nombre AS nombre,\n" +
            " p.descripcion AS descripcion,\n" +
            " p.cantidad AS cantidad,\n" +
            " p.precio AS precio,\n" +
            " p.genero AS genero,\n" +
            " p.talla AS talla,\n" +
            " p.tipo_material AS tipoMaterial,\n" +
            " p.descuento AS descuento,\n" +
            " p.marca AS marca,\n" +
            " p.imagen_principal AS imagenPrincipal,\n" +
            " p.imagen_2 AS imagen2,\n" +
            " p.imagen_3 AS imagen3,\n" +
            " p.imagen_4 AS imagen4,\n" +
            " p.imagen_5 AS imagen5,\n" +
            " p.id_categoria AS idCategoria,\n" +
            " p.id_tienda AS idTienda,\n" +
            " c.nombre AS nombreCategoria,\n" +
            " t.nombre AS nombreTienda\n" +
            " FROM producto p \n" +
            " INNER JOIN categoria c ON p.id_categoria = c.id \n" +
            " INNER JOIN tienda t ON p.id_tienda = t.id;", nativeQuery = true )
    List<ProductoDTOA> ListarProductosConForeingKeys();

}
