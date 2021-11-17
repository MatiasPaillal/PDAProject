/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.CarroModel;
import com.example.demo.Admin.modelo.CategoriaModel;
import com.example.demo.Admin.modelo.ProductoModel;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControladorAdminOpciones {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioCategoria servicioCategoria;
    @Autowired
    private ServicioCarro servicioCarro;
    private int total= 12;

    public ArrayList <Integer> generarTotal(){
        Integer total=0;
        ArrayList<CarroModel> carros = (ArrayList <CarroModel>) servicioCarro.getAll();
       for(CarroModel carro:carros){
       total+=carro.getIdProductoCarro().getPrecio()*carro.getCantidad();
       }
        ArrayList<Integer> totales= new ArrayList<Integer>();
        totales.add(total);
        return totales;
    
    }


    //***************************GUARDAR PRODUCTO*********************************************
    @RequestMapping(value = "guardarProducto", method = RequestMethod.POST)
    public String guardarProducto(String fURL, String fnombre, String fprecio, String categorias, Model modelo) {
        CategoriaModel categoria = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categorias)));

        ProductoModel producto = new ProductoModel(fnombre, Integer.parseInt(fprecio), categoria, fURL);

        servicioProducto.guardar(producto);
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "redirect:opciones";
    }

    //************CONSULTA PRODUCTO ADMIN*************
    @RequestMapping(value = "buscarProducto", method = RequestMethod.POST)
    public String consultaProductos(String codigo, Model modelo) {
        ProductoModel producto = servicioProducto.obtener(Long.parseLong(codigo));
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());
        ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
        productos.add(producto);

        if (producto != null) {
            modelo.addAttribute("lista", productos);
            return "Admin_Opciones";
        } else {
            modelo.addAttribute("lista", servicioProducto.getAll());
            return "Admin_Opciones";
        }

    }

    //************ELIMINAR PRODUCTO*************
    @GetMapping(value = "/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id, Model modelo) {

        try {
            servicioProducto.eliminar(Long.parseLong(id));
        } catch (NumberFormatException e) {
        }
        return "redirect:/opciones";
    }

    //************ACTUALIZAR ADMIN*************
    @RequestMapping(value = "actualizarProducto", method = RequestMethod.POST)
    public String actualizarProducto(String id, String url, String nombre, String precio, String categoria, Model modelo) {
        CategoriaModel categoriaProducto = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categoria)));

        ProductoModel producto = new ProductoModel(Long.parseLong(id), nombre, Integer.parseInt(precio), categoriaProducto, url);

        servicioProducto.guardar(producto);
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "redirect:opciones";
    }
 @GetMapping("/barra")
    String IngresarAdmin() {
        return "barra";
    }
}
