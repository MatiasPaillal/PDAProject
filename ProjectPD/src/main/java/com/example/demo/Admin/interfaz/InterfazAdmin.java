package com.example.demo.Admin.interfaz;

import com.example.demo.Admin.modelo.AdministradorModel;
import org.springframework.data.repository.CrudRepository;

public interface InterfazAdmin extends CrudRepository<AdministradorModel, String> {

}
