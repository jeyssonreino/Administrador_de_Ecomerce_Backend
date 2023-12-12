package com.example.ecomerce.service;

import com.example.ecomerce.dao.TiendaDao;
import com.example.ecomerce.models.Tienda;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    private final TiendaDao tiendaDao;

    public TiendaService(TiendaDao tiendaDao) {
        this.tiendaDao = tiendaDao;
    }

    //Método para guardar una tienda en la base de datos
    public Tienda guardarTienda(Tienda tienda){
        return tiendaDao.save(tienda);
    }

    //Método para buscar una tienda por su Id
    public Optional<Tienda> buscarTiendaPorId(int id){
        return tiendaDao.findById(id);
    }

    //Método para obtener todas las tiendas
    public List<Tienda> obtenerTodasLasTiendas(){
        return tiendaDao.findAll();
    }

    //Método para eliminar tienda por su Id
    public void eliminarTiendaPorId(int id){
        tiendaDao.deleteById(id);
    }


}
