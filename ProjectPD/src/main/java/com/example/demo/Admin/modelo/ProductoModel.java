package com.example.demo.Admin.modelo;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private CategoriaModel idCateg;
    
    @Column(nullable = false)
    private String urlImagen;

    public ProductoModel() {
    }

    public ProductoModel(String nombre, Integer precio, CategoriaModel idCateg, String urlImagen) {
    
        this.nombre = nombre;
        this.precio = precio;
        this.idCateg = idCateg;
        this.urlImagen = urlImagen;
    }

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
