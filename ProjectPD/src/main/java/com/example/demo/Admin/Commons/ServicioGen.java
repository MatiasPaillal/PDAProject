
package com.example.demo.Admin.Commons;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Interfaz de tipo servicio que sera implementada en los demas servicios
 * @author Autoservicio
 */
@Service
public interface ServicioGen<T, ID extends Serializable> {
    
    T guardar(T entity);

    void eliminar(ID id);
    
    void vaciarTabla();

    T obtener(ID id);

    List<T> getAll();


}
