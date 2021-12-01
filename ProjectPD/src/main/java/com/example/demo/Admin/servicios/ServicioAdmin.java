package com.example.demo.Admin.servicios;

import com.example.demo.Admin.modelo.AdministradorModel;
import com.example.demo.Admin.Commons.ServicioGen;
import org.springframework.stereotype.Service;

@Service
public interface ServicioAdmin extends ServicioGen<AdministradorModel, String> {

}
