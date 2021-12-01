package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazCarro;
import com.example.demo.Admin.modelo.CarroModel;
import com.example.demo.Admin.servicios.ServicioCarro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioCarroImpl extends servicioGenImpl<CarroModel, Long> implements ServicioCarro {

    @Autowired
    private InterfazCarro interfazCarro;

    @Autowired
    public CrudRepository<CarroModel, Long> obtener() {
        return interfazCarro;
    }
}
