package com.example.demo.Admin.modelo;

import javax.persistence.*;

@Entity
@Table(name = "carro")
public class CarroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarro;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "idProducto")
    private ProductoModel idProductoCarro;

    @Column(nullable = false)
    private Integer cantidad;

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
