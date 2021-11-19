/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.Administrador;
import com.example.demo.Admin.modelo.CarroModel;
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
public class ControladorIngresar {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioCategoria servicioCategoria;
    @Autowired
    private ServicioCarro servicioCarro;
    


    @GetMapping("/")
    String LocalHost() {

        return "Ingresar";
    }
  
    @GetMapping("/Admin")
    String IngresarAdmin() {
        return "IngresarAdmin";
    }

    @GetMapping("/Ingresar")
    String Ingresar() {
        return "Ingresar";
    }



    //************lOGIN ADMIN*************
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String admin_logear(String usuario, String password, Model modelo) {

        Administrador admins = (Administrador) servicioAdmin.obtener(usuario);

        try {
            if (admins.getPassword().equals(password)) {

                modelo.addAttribute("listaAdmin", servicioAdmin.obtener(usuario));
                modelo.addAttribute("lista", servicioProducto.getAll());
                modelo.addAttribute("listaC", servicioCategoria.getAll());
                return "Admin_Opciones";
            }
        } catch (NullPointerException e) {
        }

        return "Ingresar";
    }

    @RequestMapping(value = "opciones")
    public String guardarProducto(String nombre, Model modelo) {
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "Admin_Opciones";

    }
    
     @RequestMapping(value = "opciones/{admin}")
    public String redireccionarOpciones(@PathVariable String admin, Model modelo) {
        modelo.addAttribute("listaAdmin", servicioAdmin.obtener(admin));
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "Admin_Opciones";

    }
    

}
