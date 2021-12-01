package com.example.demo.Admin.interfaz;

import com.example.demo.Admin.modelo.CategoriaModel;
import org.springframework.data.repository.CrudRepository;

public interface InterfazCategoria extends CrudRepository<CategoriaModel, Long> {

}
