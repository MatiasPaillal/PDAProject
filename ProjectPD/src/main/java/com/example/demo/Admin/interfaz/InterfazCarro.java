package com.example.demo.Admin.interfaz;

import com.example.demo.Admin.modelo.CarroModel;
import org.springframework.data.repository.CrudRepository;

public interface InterfazCarro extends CrudRepository<CarroModel, Long> {

}
