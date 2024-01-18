package com.example.ecomerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class    SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(customizeRequests -> {
                            customizeRequests
                                    .requestMatchers("/api/login").permitAll()
                                    .requestMatchers("/api/obtener-productos").permitAll()
                                    .requestMatchers("/api/obtener-producto/{id}").permitAll()
                                    .requestMatchers("/api/registrar-usuario").permitAll()
                                    .requestMatchers("/api/enviar-email").permitAll() //permiso provisional
                                    .requestMatchers("/api/enviar-email-con-archivo").permitAll() // permiso provisional
                                    .requestMatchers("/api/registrar-pedido").hasAnyRole("CUSTOMER","ADMIN")
                                    .requestMatchers("/api/obtener-carrito/{idUser}").hasAnyRole("CUSTOMER","ADMIN")
                                    .requestMatchers("/api/eliminar-pedido/{id}").hasAnyRole("CUSTOMER","ADMIN")
                                    .requestMatchers("/api/registrar-detallepedido").hasAnyRole("CUSTOMER","ADMIN")
                                    .requestMatchers("/api/obtener-usuario/{id}").hasAnyRole("CUSTOMER","ADMIN")
                                    .requestMatchers("/api/obtenerDetallePedidoPorId/{id}").hasAnyRole("ADMIN")
                                    .requestMatchers("/api/obtenerProductosDelPedidoPorId/{id}").hasAnyRole("ADMIN")
                                    .requestMatchers("/api/obtenerTotalDelDetalleDelPedido/{id}").hasAnyRole("ADMIN")
                                    .requestMatchers("/api/**").hasAnyRole("ADMIN")
                                    .anyRequest()
                                    .authenticated();
                        }
                ).csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults()).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /*
    @Bean
    public UserDetailsService memoryUsers(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder().encode("customer123"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(admin, customer);
    }

     */


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
