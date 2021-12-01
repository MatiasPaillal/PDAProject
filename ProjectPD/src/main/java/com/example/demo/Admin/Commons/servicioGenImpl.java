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
 * Clase abstracta de tipo servicio que permite realizar cambios en la base de
 * datos
 *
 * @author Autoservicio
 */
@Service
public abstract class servicioGenImpl<T, ID extends Serializable> implements ServicioGen<T, ID> {

    /**
     * Metodo que se encargara de guardar los atributos de un objeto en un
     * registro de una tabla en la base de datos
     *
     * @param entity una entidad u objeto
     * @return la ejecucion de la instruccion de guardar el producto en la base
     * de datos
     */
    @Override
    public T guardar(T entity) {

        return obtener().save(entity);

    }

    /**
     * Metodo que se encarga de eliminar un registro de un tabla en la base de
     * datos
     *
     * @param id represnta el id de un producto
     */
    @Override
    public void eliminar(ID id) {

        obtener().deleteById(id);

    }

    /**
     * Metodo que se encarga de buscar y obtener un registro de una tabla en la
     * base de datos, y crear un objeto que tanga como atributos las columas de
     * dicho registro
     *
     * @param id
     * @return un objeto o entidad
     */
    @Override
    public T obtener(ID id) {
        Optional<T> obj = obtener().findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }

        return null;

    }

    /**
     * Este metodo se encarga de eliminar todos los registros de una tabla en la
     * base de datos
     */
    @Override
    public void vaciarTabla() {
        obtener().deleteAll();

    }

    /**
     * Este metodo se encarga de obtener todos los registros de una tabla en la
     * base de datos y transformarlos a un tipo de objeto
     *
     * @return todos las entidades obtenidas en la ejecucion del metodo
     */
    @Override
    public List<T> getAll() {
        List<T> returnList = new ArrayList<>();
        obtener().findAll().forEach(obj -> returnList.add(obj));
        return returnList;

    }

    public abstract CrudRepository<T, ID> obtener();
}
