package com.example.ecomerce.controller;


import com.example.ecomerce.models.Usuario;
import com.example.ecomerce.models.UsuarioDTOA;
import com.example.ecomerce.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Endpoint POST para guardar y registrar un usuario en la base de datos
    @PostMapping("/registrar-usuario")
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
            return new ResponseEntity<>("Usuario guardado con exito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el usuario " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para buscar un usuario registrado por su Id en la base de datos
    @GetMapping("/obtener-usuario/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
            if (usuarioOptional.isPresent()){
                Usuario usuario = usuarioOptional.get();
                return new ResponseEntity<>(usuario,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint GET para obtener todos los usuarios de la base de datos
    @GetMapping("/obtener-usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        try{
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            if (!usuarios.isEmpty()){
                return new ResponseEntity<>(usuarios, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Endpoint DELETE para eliminar un usuario por su Id de la base de datos
    @DeleteMapping("/eliminar-usuario/{id}")
    public ResponseEntity<String> eliminarCategoriaPorId(@PathVariable int id){
        try {
            usuarioService.eliminarUsuarioPorId(id);
            return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar el usuario " + e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint PUT para editar un usuario registrado por su Id en la base de datos
    @PutMapping("/actualizar-usuario/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario){
        try{
            if (usuarioService.buscarUsuarioPorId(id).isPresent()){
                usuario.setId(id);
                usuarioService.guardarUsuario(usuario);
                return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("El usuario con Id: " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Error al actualizar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Endpoint GET para obtener todos los usuarios de la base de datos
    @GetMapping("/obtener-usuarios-para-admin")
    public ResponseEntity<List<UsuarioDTOA>> obtenerUsuariosParaAdmin(){
        try{
            List<UsuarioDTOA> usuarios = usuarioService.obtenerUsuariosParaAdmin();
            if (!usuarios.isEmpty()){
                return new ResponseEntity<>(usuarios, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
