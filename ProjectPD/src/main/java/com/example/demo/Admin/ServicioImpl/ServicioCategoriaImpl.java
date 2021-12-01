package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazCategoria;
import com.example.demo.Admin.modelo.CategoriaModel;
import com.example.demo.Admin.servicios.ServicioCategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioCategoriaImpl extends servicioGenImpl<CategoriaModel, Long> implements ServicioCategoria {

    @Autowired
    private InterfazCategoria interfazCategoria;

    @Autowired
    public CrudRepository<CategoriaModel, Long> obtener() {
        return interfazCategoria;
    }
}
