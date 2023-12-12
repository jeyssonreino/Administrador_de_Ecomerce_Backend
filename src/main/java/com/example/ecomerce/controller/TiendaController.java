package com.example.ecomerce.controller;


import com.example.ecomerce.models.Tienda;
import com.example.ecomerce.service.TiendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TiendaController {

    private TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    //Endpoint POST para guardar una registrar tienda en la base de datos
    @PostMapping("/registrar-tienda")
    public ResponseEntity<String> guardarTienda(@RequestBody Tienda tienda) {
        try {
            Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
            return new ResponseEntity<>("Tienda guardada con exito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la Tienda: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para buscar una tienda registrada por su Id en la base de datos
    @GetMapping("/obtener-tienda/{id}")
    public ResponseEntity<Tienda> obtenerTiendaPorId(@PathVariable int id) {
        try {
            Optional<Tienda> tiendaOptional = tiendaService.buscarTiendaPorId(id);
            if (tiendaOptional.isPresent()) {
                Tienda tienda = tiendaOptional.get();
                return new ResponseEntity<>(tienda, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener todas las tiendas registradas en la base de datos
    @GetMapping("/obtener-tiendas")
    public ResponseEntity<List<Tienda>> obtenerTiendas() {
        try {
            List<Tienda> tienda = tiendaService.obtenerTodasLasTiendas();
            if (!tienda.isEmpty()) {
                return new ResponseEntity<>(tienda, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint DELETE para eliminar una tienda registrada en la base de datos por su Id
    @DeleteMapping("/eliminar-tienda/{id}")
    public ResponseEntity<String> eliminarTiendaPorId(@PathVariable int id) {
        try {
            tiendaService.eliminarTiendaPorId(id);
            return new ResponseEntity<>("Tienda eliminada con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la tienda: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para editar una tienda registrada en la base de datos por su Id
    @PutMapping("/actualizar-tienda/{id}")
    public ResponseEntity<String> actualizarTienda(@PathVariable int id, @RequestBody Tienda tienda) {
        try {
            if (tiendaService.buscarTiendaPorId(id).isPresent()) {
                tienda.setId(id);
                tiendaService.guardarTienda(tienda);
                return new ResponseEntity<>("Tienda actalizada correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La tienda con Id: " + id + " no ha sido encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la tienda: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
