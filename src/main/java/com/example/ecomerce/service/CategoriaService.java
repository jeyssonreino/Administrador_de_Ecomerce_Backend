package com.example.ecomerce.service;

import com.example.ecomerce.dao.CategoriaDao;
import com.example.ecomerce.models.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaDao categoriaDao;

    public CategoriaService(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    //Método para guardar un categoria en la base de datos
    public Categoria guardarCategoria(Categoria categoria){
        return categoriaDao.save(categoria);
    }

    //Método para buscar una categoria por su Id
    public Optional<Categoria> buscarCategoriaPorId(int id){
        return categoriaDao.findById(id);
    }

    //Método para obtener todas las categorias
    public List<Categoria> obtenerTodasLasCategorias(){
        return categoriaDao.findAll();
    }

    //Método para eliminar categoria por su Id
    public void eliminarCategoriaPorId(int id){
        categoriaDao.deleteById(id);
    }


}
