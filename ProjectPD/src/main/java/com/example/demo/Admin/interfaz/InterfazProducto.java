package com.example.demo.Admin.interfaz;

import com.example.demo.Admin.modelo.ProductoModel;
import org.springframework.data.repository.CrudRepository;

public interface InterfazProducto extends CrudRepository<ProductoModel, Long> {

}
