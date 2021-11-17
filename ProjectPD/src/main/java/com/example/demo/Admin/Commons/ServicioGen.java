/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.Commons;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author matias
 */
@Service
public interface ServicioGen<T, ID extends Serializable> {
    T guardar(T entity);

    void eliminar(ID id);
    void actualizar(ID id);

    T obtener(ID id);


    List<T> getAll();


}
