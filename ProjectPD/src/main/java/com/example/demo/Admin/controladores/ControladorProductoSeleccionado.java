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
public class ControladorProductoSeleccionado {

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

    @GetMapping("/Cliente_ProductoSeleccionado")
    String Cliente_ProductoSeleccionado() {

        return "Cliente_ProductoSeleccionado";
    }

    @RequestMapping(value = "mostrarProducto/agregarProductoCarro", method = RequestMethod.POST)
    public String agregarProductoCarro(String id, int cantidad, Model modelo) {
        ArrayList<CarroModel> carros =(ArrayList<CarroModel>) servicioCarro.getAll();
        CarroModel prodCarro = null;
        for (CarroModel x : carros) {
            if (x.getIdProductoCarro().getIdProducto().equals(Long.parseLong(id))) {
                prodCarro = x;
                break;
            }
        }

        ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();

        try {
            ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));
            productos.add(producto);

            if (prodCarro != null) {
                prodCarro.setCantidad(cantidad + prodCarro.getCantidad());

            } else {
                prodCarro = new CarroModel(producto, (Integer) cantidad);

            }
            servicioCarro.guardar(prodCarro);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaP", productos);
            modelo.addAttribute("listaTotal", generarTotal());
        } catch (NumberFormatException e) {

        }

        return "/Cliente_ProductoSeleccionado";
    }

    @GetMapping(value = "/eliminarDelCarroProdSelec/{id1}/{id2}")
    public String eliminarDelCarro(@PathVariable String id1, @PathVariable String id2, Model modelo) {
        ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
        try {
            servicioCarro.eliminar(Long.parseLong(id1));
            System.out.println(id2);

        } catch (NumberFormatException e) {
        }
        return "redirect:/mostrarProducto/" + id2;
    }

    @RequestMapping(value = "/mostrarProducto/mostrarProductoSelec", method = RequestMethod.POST)
    public String mostrarProductoEncontrado(String id, Model modelo) {
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

    }

}
