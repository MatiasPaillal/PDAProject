package com.example.demo.Admin.modelo;

import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Clase RegistrosModificacionesModel
 *
 * Contiene información de cada registro de modificaciones
 *
 * @author Autoservicio
 *
 */
@Entity
@Table(name = "registros_modificaciones")
public class RegistrosModificacionesModel {

    /**
     * Id del registro de modificación
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;
    /**
     * Tipo de modificación
     */
    @Column(length = 25, nullable = false)
    private String modificacion;
    /**
     * Fecha de la modificación
     */
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;
    /**
     * Información del administrador
     */
    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "usuario")
    private AdministradorModel administrador_usuario;
    /**
     * Id del producto modificado
     */
    @Column(nullable = false)
    private String idProducto;

    /**
     * Constructor por defecto
     */
    public RegistrosModificacionesModel() {
    }

    /**
     * Constructor con 4 parametros
     *
     * @param modificacion Tipo de modificación
     * @param fechaModificacion Fecha de la modificación
     * @param administrador_usuario Objeto de AdministradorModel con la
     * información del administrador que hizo los cambios
     * @param idProducto Id del producto modificado
     */
    public RegistrosModificacionesModel(String modificacion, LocalDateTime fechaModificacion, AdministradorModel administrador_usuario, String idProducto) {
        this.modificacion = modificacion;
        this.fechaModificacion = fechaModificacion;
        this.administrador_usuario = administrador_usuario;
        this.idProducto = idProducto;
    }

    public Long getIdRegistro() {
        return this.idRegistro;
    }

    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getModificacion() {
        return this.modificacion;
    }

    public void setModificacion(String modificacion) {
        this.modificacion = modificacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public AdministradorModel getAdministrador_usuario() {
        return this.administrador_usuario;
    }

    public void setAdministrador_usuario(AdministradorModel administrador_usuario) {
        this.administrador_usuario = administrador_usuario;
    }

    public String getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
public String fechaModificacionFormateada() {
        String fechaFormateada = this.fechaModificacion.getDayOfMonth() + "-"
                + this.fechaModificacion.getMonthValue() + "-"
                + this.fechaModificacion.getYear() + "   "
                + this.fechaModificacion.getHour() + ":"
                + this.fechaModificacion.getMinute() + ":"
                + this.fechaModificacion.getSecond();

        return fechaFormateada;
    }
}
