package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazBoleta;
import com.example.demo.Admin.modelo.BoletaModel;
import com.example.demo.Admin.servicios.ServicioBoleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioBoletaImpl extends servicioGenImpl<BoletaModel, Long> implements ServicioBoleta {

    @Autowired
    private InterfazBoleta interfazBoleta;

    @Autowired
    public CrudRepository<BoletaModel, Long> obtener() {
        return interfazBoleta;
    }
}
