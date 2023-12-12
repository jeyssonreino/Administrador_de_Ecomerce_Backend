package com.example.ecomerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UsuarioDTOA {

     int getId();
     String getNombre1();
     String getNombre2();
     String getApellido1();
     String getApellido2();
     String getSexo();
     String getTelefono();
     String getCorreo();
     String getTipo_de_usuario();

}
