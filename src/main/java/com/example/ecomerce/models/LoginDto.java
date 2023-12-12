package com.example.ecomerce.models;



public class LoginDto {

    private String correo;
    private String password;

    public LoginDto(String username, String password) {
        this.correo = username;
        this.password = password;
    }

    public String getUsername() {
        return correo;
    }

    public void setUsername(String username) {
        this.correo = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
