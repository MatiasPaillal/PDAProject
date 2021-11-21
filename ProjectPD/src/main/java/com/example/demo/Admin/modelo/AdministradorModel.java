package com.example.demo.Admin.modelo;

import javax.persistence.*;

/**
 * Clase AdministradorModel
 *
 *
 * Contiene información de cada administrador
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "administrador")
public class AdministradorModel {

    /**
     * Usuario del administrador
     */
    @Id
    @Column(length = 15)

    private String usuario;
    /**
     * Contraseña del administrador
     */
    @Column(length = 20, nullable = false)
    private String password;
    /**
     * Nombre del administrador
     */
    @Column(length = 80, nullable = false)
    private String nombre;

    /**
     * Constructor con 3 parametros
     *
     * @param usuario Usuario del administrador
     * @param password Contraseña del administrador
     * @param nombre Nombre del administrador
     */
    public AdministradorModel(String usuario, String password, String nombre) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
    }

    /**
     * Constructor por defecto
     */
    public AdministradorModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
