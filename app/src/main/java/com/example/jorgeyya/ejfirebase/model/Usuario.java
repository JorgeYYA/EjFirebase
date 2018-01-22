package com.example.jorgeyya.ejfirebase.model;

/**
 * Created by JorgeYYA on 14/01/2018.
 */

public class Usuario {

    private String username;
    private String correo;
    private String direccion;
    private String nombre;
    private String apellidos;

    public Usuario(){


    }


    public Usuario(String username, String correo, String direccion, String nombre, String apellidos) {

        this.username = username;
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }
}
