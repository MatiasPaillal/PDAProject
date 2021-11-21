package com.example.demo.Admin.modelo;

import javax.persistence.*;

/**
 * Clase ProductoModel
 *
 * Contiene información de cada Producto
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "producto")
public class ProductoModel {

    /**
     * Id del producto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    /**
     * Nombre del producto
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;
    /**
     * Precio del producto
     */
    @Column(name = "precio", nullable = false)
    private Integer precio;
    /**
     * Categoria del producto
     */
    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private CategoriaModel idCateg;
    /**
     * URL de la imagen del producto
     */
    @Column(nullable = false)
    private String urlImagen;

    /**
     * Constructor por defecto
     */
    public ProductoModel() {
    }

    /**
     * Constructor con 4 parametros
     *
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param idCateg Objeto de CategoriaModel que contiene la información de la
     * categoria del producto
     * @param urlImagen URL de la imagen del producto
     */
    public ProductoModel(String nombre, Integer precio, CategoriaModel idCateg, String urlImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.idCateg = idCateg;
        this.urlImagen = urlImagen;
    }

    /**
     * Constructor con 5 parametros
     *
     * @param idProducto Id del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param idCateg Objeto de CategoriaModel que contiene la información de la
     * categoria del producto
     * @param urlImagen URL de la imagen del producto
     */
    public ProductoModel(Long idProducto, String nombre, Integer precio, CategoriaModel idCateg, String urlImagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.idCateg = idCateg;
        this.urlImagen = urlImagen;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaModel getIdCateg() {
        return idCateg;
    }

    public void setIdCateg(CategoriaModel idCateg) {
        this.idCateg = idCateg;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getURLImagen() {
        return urlImagen;
    }

    public void setURLImagen(String URLImagen) {
        this.urlImagen = URLImagen;
    }

}
