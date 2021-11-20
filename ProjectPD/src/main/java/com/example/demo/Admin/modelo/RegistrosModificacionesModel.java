package com.example.demo.Admin.modelo;

import javax.persistence.*;

@Entity
@Table(name = "registros_modificaciones")
public class RegistrosModificacionesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    @Column(length = 25, nullable = false)
    private String modificacion;

    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "usuario")
    private AdministradorModel administrador_usuario;

    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "idProducto")
    private ProductoModel idProducto;

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

    public AdministradorModel getAdministrador_usuario() {
        return this.administrador_usuario;
    }

    public void setAdministrador_usuario(AdministradorModel administrador_usuario) {
        this.administrador_usuario = administrador_usuario;
    }

    public ProductoModel getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(ProductoModel idProducto) {
        this.idProducto = idProducto;
    }

}
