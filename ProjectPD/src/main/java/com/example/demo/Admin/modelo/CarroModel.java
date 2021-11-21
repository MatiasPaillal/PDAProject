package com.example.demo.Admin.modelo;

import javax.persistence.*;

/**
 * Clase CarroModel
 *
 * Contiene información del carro de compras
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "carro")
public class CarroModel {

    /**
     * Id del carro
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarro;
    /**
     * Información del producto en el carro
     */
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "idProducto")
    private ProductoModel idProductoCarro;
    /**
     * Cantidad del producto en el carro
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Constructor por defecto
     */
    public CarroModel() {
    }

    /**
     * Constructor con 2 parametros
     *
     * @param idProductoCarro Objeto de ProductoModel que contiene información
     * del producto que se agrega al carro
     * @param cantidad Cantidad del producto que se agrega al carro
     */
    public CarroModel(ProductoModel idProductoCarro, Integer cantidad) {
        this.idProductoCarro = idProductoCarro;
        this.cantidad = cantidad;
    }

    public Long getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(Long idCarro) {
        this.idCarro = idCarro;
    }

    public ProductoModel getIdProductoCarro() {
        return this.idProductoCarro;
    }

    public void setIdProductoCarro(ProductoModel idProductoCarro) {
        this.idProductoCarro = idProductoCarro;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
