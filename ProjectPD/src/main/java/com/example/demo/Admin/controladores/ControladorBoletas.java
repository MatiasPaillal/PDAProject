package com.example.demo.Admin.controladores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.demo.Admin.modelo.CarroModel;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Boleta;

/**
 * @author matias
 */
@Controller
public class ControladorBoletas {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioCategoria servicioCategoria;
    @Autowired
    private ServicioCarro servicioCarro;

    public ArrayList<Integer> generarTotal() {
        Integer total = 0;
        ArrayList<CarroModel> carros = (ArrayList<CarroModel>) servicioCarro.getAll();
        for (CarroModel carro : carros) {
            total += carro.getIdProductoCarro().getPrecio() * carro.getCantidad();
        }
        ArrayList<Integer> totales = new ArrayList<Integer>();
        totales.add(total);
        return totales;

    }


    @GetMapping("/Cliente_Boleta")
    String Cliente_Boleta() {
        return "Cliente_Boleta";
    }






    @GetMapping("/Boletas")
    String Boleta(@RequestParam(name = "Boleta", required = false, defaultValue = "asda") Boleta boleta, Model modelo) {
        //modelo.addAttribute(this.boleta);
        return "Boleta";
    }






    /*          @RequestMapping(value = "mostrarProducto", method = RequestMethod.POST)
    public String mostrarProductoCategoria(String nombre, Model modelo) {
        
      ArrayList <ProductoModel> productos= (ArrayList <ProductoModel>) servicioProducto.getAll();
      
      for(int i=0; i<productos.size();i++){
      if(nombre==0)
      
      
      }
             
       if(producto != null){
       ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto); 
        modelo.addAttribute("listaP", productos);
        return "/Cliente_ProductoSeleccionado";
       }else{
           modelo.addAttribute("listaP", servicioProducto.getAll());
        return "Cliente_Productos";
       }
         
    }*/
    @RequestMapping(value = "generarBoleta", method = RequestMethod.POST)
    public String generarBoleta(Model modelo) {

        modelo.addAttribute("listaP", servicioCarro.getAll());

        return "Cliente_Boleta";

    }


    /*
    
    @RequestMapping("/Admin_Opciones")
     public String mostrarAdmin(Model modelo) {
        modelo.addAttribute("lista", servicioProducto.getAll());
        return "Admin_Opciones";
    }
   
  
      
     @GetMapping("/guardar/{id}")
     public String mostrarAdminGuardad(@PathVariable("id") int id, Model modelo) {
        if(id!=0){
            modelo.addAttribute("admin", servicioAdmin.obtener(id));
        }else{
            modelo.addAttribute("admin", new Administrador());
        }
        
        return "Admins";
    }
     */
    @RequestMapping(value = "buscarBoleta", method = RequestMethod.POST)
    public String buscarBoleta(String numero) {
        if ("11111".equals(numero)) {
            return "Admin_Boleta";
        }

        return "Admin_BuscarBoletas";

    }




}
