package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazRegistrosModificaciones;
import com.example.demo.Admin.modelo.RegistrosModificacionesModel;
import com.example.demo.Admin.servicios.ServicioRegistrosModificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioRegistrosModificacionesImpl extends servicioGenImpl<RegistrosModificacionesModel, Long> implements ServicioRegistrosModificaciones {

    @Autowired
    private InterfazRegistrosModificaciones interfazRegistrosModificaciones;

    @Autowired
    public CrudRepository<RegistrosModificacionesModel, Long> obtener() {
        return interfazRegistrosModificaciones;
    }
}
