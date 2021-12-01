package com.example.demo.Admin.ServicioImpl;

import com.example.demo.Admin.modelo.ProductoModel;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.Commons.servicioGenImpl;
import com.example.demo.Admin.interfaz.InterfazProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicioProductoImpl extends servicioGenImpl<ProductoModel, Long> implements ServicioProducto {
    @Autowired
    private InterfazProducto interfazProducto;

    @Autowired
    public CrudRepository<ProductoModel, Long> obtener() {
        return interfazProducto;
    }

}
