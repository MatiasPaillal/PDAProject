/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.interfaz;

import com.example.demo.models.Administrador;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author matias
 */
public interface InterfazAdmin extends CrudRepository<Administrador, String>{

  
    
}
