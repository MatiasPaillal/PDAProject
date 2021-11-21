package com.example.demo.Admin.modelo;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "registros_modificaciones")
public class RegistrosModificacionesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    @Column(length = 25, nullable = false)
    private String modificacion;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "usuario")
    private AdministradorModel administrador_usuario;

    @Column(nullable = false)
    private String idProducto;

    public RegistrosModificacionesModel() {
    }

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

}
