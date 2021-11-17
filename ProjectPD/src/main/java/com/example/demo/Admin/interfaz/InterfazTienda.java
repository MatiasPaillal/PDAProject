package com.example.demo.Admin.interfaz;

import com.example.demo.Admin.modelo.TiendaModel;
import org.springframework.data.repository.CrudRepository;

public interface InterfazTienda extends CrudRepository<TiendaModel, String> {

}
