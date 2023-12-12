package com.example.ecomerce.service;

import com.example.ecomerce.dao.UserRepository;
import com.example.ecomerce.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByCorreo(correo);
                if (usuario == null){
                    throw  new UsernameNotFoundException("User " + correo + " not found");
                }
        return User.builder()
                .username(usuario.getCorreo())
                .password((usuario.getContrasena()))
                .roles(usuario.getTipoUsuario())
                .build();
    }
}
