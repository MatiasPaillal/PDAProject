/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.CategoriaModel;
import com.example.demo.Admin.modelo.ProductoModel;
import com.example.demo.Admin.modelo.RegistrosModificacionesModel;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.servicios.ServicioRegistrosModificaciones;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    private ServicioRegistrosModificaciones servicioRegistrosModificaciones;

    //***************************GUARDAR PRODUCTO*********************************************
    @RequestMapping(value = "guardarProducto", method = RequestMethod.POST)
    public String guardarProducto(String admin, String fURL, String fnombre, String fprecio, String categorias, Model modelo) {

        CategoriaModel categoria = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categorias)));
        ProductoModel producto = new ProductoModel(fnombre, Integer.parseInt(fprecio), categoria, fURL);
        servicioProducto.guardar(producto);

        ModelAndView m = new ModelAndView("forward:/Opciones");

        m.addObject("admin", admin);

        ArrayList<ProductoModel> productosBD = (ArrayList<ProductoModel>) servicioProducto.getAll();
        RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Guardó", LocalDateTime.now(), servicioAdmin.obtener(admin), String.valueOf(productosBD.get(productosBD.size() - 1).getIdProducto()));
        servicioRegistrosModificaciones.guardar(registro);

        return "redirect:opciones";
    }

    //************ELIMINAR PRODUCTO*************
    @GetMapping(value = "/eliminar/{id}/{admin}")
    public ModelAndView eliminarProducto(@PathVariable String id, @PathVariable String admin, Model modelo) {

        ModelAndView m = new ModelAndView("forward:/Opciones");

        m.addObject("admin", admin);
        try {
            servicioProducto.eliminar(Long.parseLong(id));

            RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Eliminó", LocalDateTime.now(), servicioAdmin.obtener(admin), id);
            servicioRegistrosModificaciones.guardar(registro);

        } catch (NumberFormatException e) {
        }

        return m;
    }

    //************ACTUALIZAR ADMIN*************
    @RequestMapping(value = "actualizarProducto", method = RequestMethod.POST)
    public ModelAndView actualizarProducto(String admin, String id, String url, String nombre, String precio, String categoria, Model modelo) {
        CategoriaModel categoriaProducto = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categoria)));

        ProductoModel producto = new ProductoModel(Long.parseLong(id), nombre, Integer.parseInt(precio), categoriaProducto, url);
        servicioProducto.guardar(producto);

        RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Actualizó", LocalDateTime.now(), servicioAdmin.obtener(admin), id);
        servicioRegistrosModificaciones.guardar(registro);

        ModelAndView m = new ModelAndView("forward:/Opciones");
        m.addObject("admin", admin);

        return m;
    }

    //************CONSULTA PRODUCTO ADMIN*************
    @RequestMapping(value = "buscarProducto", method = RequestMethod.POST)
    public ModelAndView consultaProductos(String codigo, String admin, Model modelo) {
        ModelAndView m = new ModelAndView("forward:/Opciones");

        m.addObject("codigo", codigo);
        m.addObject("admin", admin);
        return m;

    }

    @RequestMapping(value = "Opciones")
    public String verOpc1iones(@ModelAttribute("codigo") String codigo, @ModelAttribute("admin") String admin, Model modelo) {

        if (!codigo.equals("") && servicioProducto.obtener(Long.parseLong(codigo)) != null) {

            ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
            productos.add(servicioProducto.obtener(Long.parseLong(codigo)));

            modelo.addAttribute("lista", productos);
        } else {
            modelo.addAttribute("lista", servicioProducto.getAll());
        }

        modelo.addAttribute("listaAdmin", servicioAdmin.obtener(admin));
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "Admin_Opciones";

    }
}
