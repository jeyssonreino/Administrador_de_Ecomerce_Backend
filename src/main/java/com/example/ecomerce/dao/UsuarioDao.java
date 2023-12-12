package com.example.ecomerce.dao;

import com.example.ecomerce.models.LoginDto;
import com.example.ecomerce.models.Usuario;
import com.example.ecomerce.models.UsuarioDTOA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {


    @Query(value = "SELECT id,\n" +
            "nombre1,\n" +
            "nombre2,\n" +
            "apellido1,\n" +
            "apellido2,\n" +
            "fecha_nacimiento,\n" +
            "sexo,\n" +
            "telefono,\n" +
            "correo,\n" +
            "fecha_y_hora_de_registro,\n" +
            "tipo_de_usuario\n" +
            " FROM usuario;", nativeQuery = true)
    List<UsuarioDTOA> ListarUsuariosParaAdmin();

    @Query(value = "SELECT * FROM usuario WHERE correo = :email", nativeQuery = true)
    Usuario obtenerUsuarioPorEmail(@Param("email") String email);



}
