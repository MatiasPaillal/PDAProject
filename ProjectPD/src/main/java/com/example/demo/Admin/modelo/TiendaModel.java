package com.example.demo.Admin.modelo;

import javax.persistence.*;

/**
 * Clase TiendaModel
 *
 * Contiene información de la tienda
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "tienda")
public class TiendaModel {
    /**
     * Rut de la tienda
     */
    @Id
    @Column(length = 10)
    private String rutTienda;
    /**
     * Nombre de la tienda
     */
    @Column(length = 45, nullable = false)
    private String nombre;
    /**
     * Dirección de la tienda
     */
    @Column(length = 45, nullable = false)
    private String direccion;
    /**
     * Teléfono de la tienda
     */
    @Column(nullable = false)
    private Integer telefono;
    /**
     * Información del administrador
     */
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "usuario")
    private AdministradorModel administrador_usuario;

    public String getRutTienda() {
        return this.rutTienda;
    }

    public void setRutTienda(String rutTienda) {
        this.rutTienda = rutTienda;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public AdministradorModel getAdministrador_usuario() {
        return this.administrador_usuario;
    }

    public void setAdministrador_usuario(AdministradorModel administrador_usuario) {
        this.administrador_usuario = administrador_usuario;
    }

}
