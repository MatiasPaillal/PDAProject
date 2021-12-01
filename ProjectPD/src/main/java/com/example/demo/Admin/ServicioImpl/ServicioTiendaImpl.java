package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazTienda;
import com.example.demo.Admin.modelo.TiendaModel;
import com.example.demo.Admin.servicios.ServicioTienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioTiendaImpl extends servicioGenImpl<TiendaModel, String> implements ServicioTienda {

    @Autowired
    private InterfazTienda interfazTienda;

    @Autowired
    public CrudRepository<TiendaModel, String> obtener(){
        return interfazTienda;
    }

}
