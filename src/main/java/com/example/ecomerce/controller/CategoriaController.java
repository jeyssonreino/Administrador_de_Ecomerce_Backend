package com.example.ecomerce.controller;


import com.example.ecomerce.models.Categoria;
import com.example.ecomerce.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    //Endpoint POST para guardar y registrar categorias en la base de datos
    @PostMapping("/registrar-categoria")
    public ResponseEntity<String> guardarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = categoriaService.guardarCategoria(categoria);
            return new ResponseEntity<>("Categoria guardada con exito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la categoria: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para buscar una categoria registrada por su Id en la base de datos
    @GetMapping("/obtener-categoria/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        try {
            Optional<Categoria> categoriaOptional = categoriaService.buscarCategoriaPorId(id);
            if (categoriaOptional.isPresent()) {
                Categoria categoria = categoriaOptional.get();
                return new ResponseEntity<>(categoria, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener todos las categorias registradas en la base de datos
    @GetMapping("/obtener-categorias")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        try {
            List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
            if (!categorias.isEmpty()) {
                return new ResponseEntity<>(categorias, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint DELETE para eliminar una categoria registrada en la base de datos por su Id
    @DeleteMapping("/eliminar-categoria/{id}")
    public ResponseEntity<String> eliminarCategoriaPorId(@PathVariable int id) {
        try {
            categoriaService.eliminarCategoriaPorId(id);
            return new ResponseEntity<>("Categoria eliminada con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la categoria: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para eidtar una categoria registrada en la base de datos por su Id
    @PutMapping("/actualizar-categoria/{id}")
    public ResponseEntity<String> actualizarCategoria(@PathVariable int id, @RequestBody Categoria categoria) {
        try {
            if (categoriaService.buscarCategoriaPorId(id).isPresent()) {
                categoria.setId(id);
                categoriaService.guardarCategoria(categoria);
                return new ResponseEntity<>("Categoría actalizada correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La categoría con Id: " + id + " no ha sido encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la categoría: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
