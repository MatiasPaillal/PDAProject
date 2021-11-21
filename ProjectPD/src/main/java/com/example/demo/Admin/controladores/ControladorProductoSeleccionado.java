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

    /**
     * Metodo que se encarga de calcular el total de la suma de los precios de
     * los productos contenidos en un carro de compras
     *
     * @return El total de la compra
     */
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

    /**
     * Este metodo se encarga de agregar un producto al carro de compras
     *
     * @param id representa un id de un producto
     * @param cantidad representa la cantidad de un producto que habra en un
     * carro
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return Se retornara a la vista del producto anteriormente agregardo al
     * carro
     */
    @RequestMapping(value = "mostrarProducto/agregarProductoCarro", method = RequestMethod.POST)
    public String agregarProductoCarro(String id, int cantidad, Model modelo) {
        ArrayList<CarroModel> carros = (ArrayList<CarroModel>) servicioCarro.getAll();
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

    /**
     * Este metodo permite eliminar un producto de el carro de compras en la
     * vista de Cliente_Categorias
     *
     * @param id1 representa el id de el producto que sera eliminado del carro
     * de compras
     * @param id2 representa el id de del producto mostrador en la vista
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return la vista del producto mostrador anteriormente
     */
    @GetMapping(value = "/eliminarDelCarroProdSelec/{id1}/{id2}")
    public String eliminarDelCarro(@PathVariable String id1, @PathVariable String id2, Model modelo) {
        ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
        try {
            servicioCarro.eliminar(Long.parseLong(id1));

        } catch (NumberFormatException e) {
        }
        return "redirect:/mostrarProducto/" + id2;
    }

    /**
     * Este metodo se encarga de consultar los datos de un producto a la base de
     * datos
     *
     * @param id representa un id de un producto
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return En caso de que se encuentre el producto se retornara a la vista
     * del producto seleccionado, en caso contrario ratornara a la vista de
     * producto seleccionado anteriormente
     *
     */
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

    /**
     * Este metodo se encarga de cambiar las cantidades de un producto que se
     * encuentre en el carro de compras
     *
     * @param idProducto representa el id de un producto que se consultara
     * @param idProdSelec representa el id de del producto mostrado en la vista
     * @param cantProducto representa la cantidad de dicho producto
     * @param modelo permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return vista del producto mostrador anteriormente
     */
    @RequestMapping(value = "mostrarProducto/cambiarCantidadProdSelec", method = RequestMethod.POST)
    public String cambiarCantidadProducto(int idProducto, int idProdSelec, int cantProducto, Model modelo) {
        ArrayList<CarroModel> carros = (ArrayList<CarroModel>) servicioCarro.getAll();
        CarroModel prodCarro = null;

        for (CarroModel x : carros) {
            if (x.getIdProductoCarro().getIdProducto().equals(Long.valueOf(idProducto))) {
                prodCarro = x;
                break;
            }
        }

        if (prodCarro != null) {
            prodCarro.setCantidad(cantProducto);
            servicioCarro.guardar(prodCarro);
        }

        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> listaP = new ArrayList<>();

        for (ProductoModel x : productos) {
            if (x.getIdProducto().equals(Long.valueOf(idProdSelec))) {
                listaP.add(x);
                break;
            }
        }

        modelo.addAttribute("listaP", listaP);
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "/Cliente_ProductoSeleccionado";
    }

    /**
     * Este metodo se encarga de cambiar las cantidades de un producto que se
     * encuentre en el carro de compras
     *
     * @param idProducto representa el id de un producto que se consultara
     * @param idProdSelec representa el id de del producto mostrado en la vista
     * @param cantProducto representa la cantidad de dicho producto
     * @param modelo permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return vista del producto mostrador anteriormente
     */
    @RequestMapping(value = "cambiarCantidadProdSelec", method = RequestMethod.POST)
    public String cambiarCantidadProducto2(int idProducto, int idProdSelec, int cantProducto, Model modelo) {
        ArrayList<CarroModel> carros = (ArrayList<CarroModel>) servicioCarro.getAll();
        CarroModel prodCarro = null;

        for (CarroModel x : carros) {
            if (x.getIdProductoCarro().getIdProducto().equals(Long.valueOf(idProducto))) {
                prodCarro = x;
                break;
            }
        }

        if (prodCarro != null) {
            prodCarro.setCantidad(cantProducto);
            servicioCarro.guardar(prodCarro);
        }

        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> listaP = new ArrayList<>();

        for (ProductoModel x : productos) {
            if (x.getIdProducto().equals(Long.valueOf(idProdSelec))) {
                listaP.add(x);
                break;
            }
        }

        modelo.addAttribute("listaP", listaP);
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "/Cliente_ProductoSeleccionado";
    }

    /**
     * Este metodo permite volver a la vista de productos de la categoria
     * anteriormente seleccionada, desde la vista de producto un producto
     * seleccionado
     *
     * @param idCateg representa el id de una categoria
     * @param modelo permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return la vista de productos, con los productos de la categoria anteriormente seleccionada
     */
    @RequestMapping(value = "volverAtrasProdSelec", method = RequestMethod.POST)
    public String volverAtras(int idCateg, Model modelo) {
        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> productosCateg = new ArrayList<>();

        if (!productos.isEmpty()) {

            for (int i = 0; i < productos.size(); i++) {
                if ((productos.get(i).getIdCateg().getId().equals(Long.valueOf(idCateg)))) {
                    productosCateg.add(productos.get(i));
                }
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

    /**
     * Este metodo permite volver a la vista de productos de la categoria
     * anteriormente seleccionada, desde la vista de producto un producto
     * seleccionado
     *
     * @param idCateg representa el id de una categoria
     * @param modelo permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return la vista de productos, con los productos de la categoria anteriormente seleccionada
     */
    @RequestMapping(value = "mostrarProducto/volverAtrasProdSelec", method = RequestMethod.POST)
    public String volverAtras2(int idCateg, Model modelo) {
        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> productosCateg = new ArrayList<>();

        if (!productos.isEmpty()) {

            for (int i = 0; i < productos.size(); i++) {
                if ((productos.get(i).getIdCateg().getId().equals(Long.valueOf(idCateg)))) {
                    productosCateg.add(productos.get(i));
                }
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

}
