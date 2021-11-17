/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.Commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias
 */
@Service
public abstract class servicioGenImpl<T, ID extends Serializable> implements ServicioGen<T, ID> {

    @Override
    public T guardar(T entity) {

        return obtener().save(entity);

    }

    @Override
    public void eliminar(ID id) {

        obtener().deleteById(id);

    }

    @Override
    public T obtener(ID id) {
        Optional<T> obj = obtener().findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }

        return null;
  
  } @Override
    public void actualizar(ID id) {
     
        

    }

    
    @Override
    public List<T> getAll(){
    List<T> returnList= new ArrayList<>();
    obtener().findAll().forEach(obj-> returnList.add(obj));
    return returnList;
    
    }
   
   public abstract CrudRepository <T,ID> obtener();
}
