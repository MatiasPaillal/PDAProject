package com.example.demo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.example.demo.models.Administrador;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Boleta;
import com.example.demo.Carro;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author matias
 */

 
@Controller
public class controllers {
    @Autowired
    private ServicioAdmin servicioAdmin;

    Boleta boleta = new Boleta("a", "a", "a", "a", "a", "a", "a");

    @GetMapping("/")
    String LocalHost() {

        return "Ingresar";
    }

    @GetMapping("/Cliente_Categorias")
    String Cliente_Categorias() {
        return "Cliente_Categorias";
    }
    @GetMapping("/Ejemplo")
    String ej() {
        return "ejemplo";
    }

    @GetMapping("/Cliente_Productos")
    String Cliente_Productos() {
        return "Cliente_Productos";
    }

    @GetMapping("/Cliente_ProductoSeleccionado")
    String Cliente_ProductoSeleccionado() {
        return "Cliente_ProductoSeleccionado";
    }

    @GetMapping("/Cliente_Boleta")
    String Cliente_Boleta() {
        return "Cliente_Boleta";
    }

    @GetMapping("/Ingresar")
    String Ingresar() {
        return "Ingresar";
    }

    @GetMapping("/Admin")
    String IngresarAdmin() {
        return "IngresarAdmin";
    }

    @GetMapping("/Admin_Categorias")
    String desplegarOpcionesAdmin() {
        return "Admin_Categorias";
    }

    @GetMapping("/Admin_BuscarBoletas")
    String mostrarBoletasAdmin() {
        return "Admin_BuscarBoletas";
    }

    @GetMapping("/Boletas")
    String Boleta(@RequestParam(name = "Boleta", required = false, defaultValue = "asda") Boleta boleta, Model modelo) {
        modelo.addAttribute(this.boleta);
        return "Boleta";
    }

    @GetMapping("/AgregarProducto")
    String agregarProducto() {
        return "Admin_AgregarP";
    }

    @GetMapping("/Admin_Productos")
    String mostrarProductoAdmin() {
        return "Admin_Productos";
    }
    @GetMapping("/Admin_ProductoSeleccionado")
    String mostrarProductoSeleccionadoAdmin(Model modelo) {
         
        return "Admin_ProductoSeleccionado";
    }

    /*
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String consultaAdmin(String name, String password) {
        if ("1".equals(name) && "1".equals(password)) {
            return "Admin_Opciones";
        }

        return "IngresarAdmin";

   
    */
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String Cliente_Categorias(String usuario, String password, Model modelo) {
        modelo.addAttribute("lista", servicioAdmin.getAll());

        Administrador admins = (Administrador) servicioAdmin.obtener(usuario);

        try {
            if (admins.getUsuario().equals(usuario) && admins.getPassword().equals(password)) {
                return "Admin_Opciones";
            }
        } catch (NullPointerException e) {
        }

        return "IngresarAdmin";
    }

    
     
    
    
   
   /*
   @RequestMapping("/a")
     public String mostrarAdmin(Model modelo) {
        modelo.addAttribute("lista", servicioAdmin.getAll());
        
        return "Admins";
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

    @RequestMapping(value = "opciones")
    public String guardarProducto(String nombre) {

        return "Admin_Opciones";

    }

    @RequestMapping(value = "productos")
    public String verProductoAdmin(String nombre) {

        return "Admin_Productos";

    }

}
