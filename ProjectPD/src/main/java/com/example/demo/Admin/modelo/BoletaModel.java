package com.example.demo.Admin.modelo;

import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Clase BoletaModel
 *
 *
 * Contiene información de cada boleta
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "boleta")
public class BoletaModel {

    /**
     * Número de la boleta
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroBoleta;
    /**
     * Fecha de emisión de la boleta
     */
    @Column(nullable = false)
    private LocalDateTime fechaEmision;
    /**
     * Total de la boleta (Total de la compra)
     */
    @Column(nullable = false)
    private Integer total;
    /**
     * Información de la tienda
     */
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "rutTienda")
    private TiendaModel rutTienda;
    /**
     * Id de los productos con sus respectivas cantidades
     */
    @Column(columnDefinition = "text")
    private String productosCantidad;

    /**
     * Constructor con 4 parametros
     *
     * @param fechaEmision Fecha de emisión de la boleta
     * @param total Total de la boleta
     * @param rutTienda Objeto de TiendaModel con la información de la tienda
     * @param productosCantidad Id de los productos con sus respectivas
     * cantidades
     */
    public BoletaModel(LocalDateTime fechaEmision, Integer total, TiendaModel rutTienda, String productosCantidad) {
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.rutTienda = rutTienda;
        this.productosCantidad = productosCantidad;
    }

    /**
     * Constructor por defecto
     */
    public BoletaModel() {
    }

    public Long getNroBoleta() {
        return this.nroBoleta;
    }

    public void setNroBoleta(Long nroBoleta) {
        this.nroBoleta = nroBoleta;
    }

    public LocalDateTime getFechaEmision() {
        return this.fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public TiendaModel getRutTienda() {
        return this.rutTienda;
    }

    public void setRutTienda(TiendaModel rutTienda) {
        this.rutTienda = rutTienda;
    }

    public String getProductosCantidad() {
        return productosCantidad;
    }

    public void setProductosCantidad(String productosCantidad) {
        this.productosCantidad = productosCantidad;
    }

    /**
     * Le da un formato a la fecha de emisión de la boleta
     *
     * @return String con la fecha de emisión de la boleta con el siguiente
     * formato: DIA-MES-AÑO HORA:MINUTOS:SEGUNDOS
     */
    public String fechaEmisionFormateada() {
        String fechaFormateada = this.fechaEmision.getDayOfMonth() + "-"
                + this.fechaEmision.getMonthValue() + "-"
                + this.fechaEmision.getYear() + "   "
                + this.fechaEmision.getHour() + ":"
                + this.fechaEmision.getMinute() + ":"
                + this.fechaEmision.getSecond();

        return fechaFormateada;
    }

}
