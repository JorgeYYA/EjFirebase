package com.example.jorgeyya.ejfirebase.model;

/**
 * Created by JorgeYYA on 14/01/2018.
 */

public class Articulo {

    private String user;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String precio;

    public Articulo() {

    }

    public Articulo(String user, String nombre, String descripcion, String categoria, String precio) {
        this.user = user;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripción) {
        this.descripcion = descripción;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoría) {
        this.categoria = categoría;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "user='" + user + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripcion + '\'' +
                ", categoría='" + categoria + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }
}
