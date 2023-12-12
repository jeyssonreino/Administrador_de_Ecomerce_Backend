package com.example.ecomerce.controller;

import com.example.ecomerce.config.JwtUtil;
import com.example.ecomerce.dao.UsuarioDao;
import com.example.ecomerce.models.LoginDto;
import com.example.ecomerce.models.Usuario;
import com.example.ecomerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }


    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);
        String jwt = this.jwtUtil.create(loginDto.getUsername());
        String email = loginDto.getUsername();
        Usuario usuario = this.usuarioService.bucarUsuarioPorEmail(email);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).body(usuario);

    }


}
