/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.CarroModel;
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
public class ControladorCategorias {

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

    @GetMapping("/Cliente_Categorias")
    String Cliente_Categorias(Model modelo) {

        modelo.addAttribute("listaC", servicioCategoria.getAll());
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "Cliente_Categorias";
    }

    //---------------------------------------------------
    @RequestMapping(value = "mostrarProductosPorCategoria", method = RequestMethod.POST)
    public String mostrarProductosCategoria(String idCateg, Model modelo) {
        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> productosCateg = new ArrayList<>();

        if (!productos.isEmpty()) {

            for (int i = 0; i < productos.size(); i++) {
                if ((productos.get(i).getIdCateg().getId() == Long.parseLong(idCateg))) {
                    System.out.println(productos.get(i).getNombre() + "---" + productos.get(i).getIdCateg().getId());
                    productosCateg.add(productos.get(i));
                }
            }

            if (productosCateg.isEmpty()) {
                modelo.addAttribute("listaC", servicioCategoria.getAll());
                modelo.addAttribute("listaCarro", servicioCarro.getAll());
                modelo.addAttribute("listaTotal", generarTotal());
                return "Cliente_Categorias";
            }

            modelo.addAttribute("listaP", productosCateg);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "Cliente_Productos";

        } else {
            modelo.addAttribute("listaC", servicioCategoria.getAll());
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "Cliente_Categorias";
        }
    }
    //---------------------------------------------------
/*
    @RequestMapping(value = "mostrarProductoCategoria", method = RequestMethod.POST)
    public String actualizarProducto(String id, Model modelo) {
        ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));

        if (producto != null) {
            ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
            productos.add(producto);
            modelo.addAttribute("listaP", productos);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "/Cliente_ProductoSeleccionado";
        } else {
            modelo.addAttribute("listaC", servicioCategoria.getAll());
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "Cliente_Categorias";
        }

    }*/

    @GetMapping(value = "/eliminarDelCarroCategoria/{id}")
    public String eliminarDelCarro(@PathVariable String id, Model modelo) {

        try {
            servicioCarro.eliminar(Long.parseLong(id));
        } catch (NumberFormatException e) {
        }
        return "redirect:/Cliente_Categorias";
    }

}
