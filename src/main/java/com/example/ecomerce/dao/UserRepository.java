package com.example.ecomerce.dao;

import com.example.ecomerce.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Usuario,Integer> {

    public Usuario findByCorreo(String correo);




}
