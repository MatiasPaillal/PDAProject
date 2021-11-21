package com.example.demo.Admin.modelo;

import javax.persistence.*;

/**
 * Clase CategoriaModel
 *
 * Contiene información de cada Categoria
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "categoria")
public class CategoriaModel {

    /**
     * Id de la categoria
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre de la categoria
     */
    @Column(length = 30, nullable = false)
    private String categoria;
    /**
     * URL de la imagen de la categoria
     */
    @Column(nullable = false)
    private String urlImagenC;

    public String getUrlImagenC() {
        return urlImagenC;
    }

    public void setUrlImagenC(String urlImagenC) {
        this.urlImagenC = urlImagenC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
