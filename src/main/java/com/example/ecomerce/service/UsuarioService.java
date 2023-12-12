package com.example.ecomerce.service;

import com.example.ecomerce.dao.UsuarioDao;
import com.example.ecomerce.models.Usuario;
import com.example.ecomerce.models.UsuarioDTOA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioDao usuarioDao;

    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    //Método para guardar un usuario en la base de datos
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioDao.save(usuario);
    }

    //Método para buscar un usuario por su Id
    public Optional<Usuario> buscarUsuarioPorId(int id){
        return usuarioDao.findById(id);
    }

    //Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioDao.findAll();
    }

    //Método para eliminar usuario por su Id
    public void eliminarUsuarioPorId(int id){
        usuarioDao.deleteById(id);
    }

    //Método para obtner los usuarios formateados para Admin
    public List<UsuarioDTOA> obtenerUsuariosParaAdmin(){
        return usuarioDao.ListarUsuariosParaAdmin();
    }

    public Usuario bucarUsuarioPorEmail(String email){
        return usuarioDao.obtenerUsuarioPorEmail(email);
    }


}
