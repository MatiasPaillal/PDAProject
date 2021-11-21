package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.CarroModel;
import com.example.demo.Admin.modelo.ProductoModel;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioProducto;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Clase ControladorProductos de tipo controlador
 *
 * Se encarga de realizar las acciones correspondientes a los Productos y a la
 * vista Cliente_Productos, y redireccionar a las respectivas vistas html
 *
 * @author Autoservicio
 */
@Controller
public class ControladorProductos {

    /**
     * Importacion de metodos del servicio de Producto
     */
    @Autowired
    private ServicioProducto servicioProducto;
    /**
     * Importacion de metodos del servicio de Carro
     */
    @Autowired
    private ServicioCarro servicioCarro;

    /**
     * Metodo que calcula el total de la compra
     *
     * @return Integer con el total de la compra
     */
    public ArrayList<Integer> generarTotal() {
        Integer total = 0;
        ArrayList<CarroModel> carros = (ArrayList<CarroModel>) servicioCarro.getAll();
        for (CarroModel carro : carros) {
            total += carro.getIdProductoCarro().getPrecio() * carro.getCantidad();
        }
        ArrayList<Integer> totales = new ArrayList<>();
        totales.add(total);
        return totales;

    }

    /**
     * Metodo que se encarga de buscar el producto por su id y redireccionar a
     * la vista HTML Cliente_ProductoSeleccionado con la informacion de ese
     * producto. En caso contrario se redireccionara a la misma vista HTML, en
     * este caso a Cliente_Productos
     *
     * @param id Id del producto que se quiere buscar y mostrar
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "mostrarProducto", method = RequestMethod.POST)
    public String mostrarProductoEncontrado(String id, Model modelo) {
        ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));

        if (producto != null) {
            ArrayList<ProductoModel> productos = new ArrayList<>();
            productos.add(producto);
            modelo.addAttribute("listaP", productos);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "/Cliente_ProductoSeleccionado";
        } else {
            modelo.addAttribute("listaP", servicioProducto.getAll());
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
            return "Cliente_Productos";
        }

    }

    /**
     * Metodo que se encarga de buscar el producto por su id y redireccionar a
     * la vista HTML Cliente_ProductoSeleccionado con la informacion de ese
     * producto
     *
     * @param id Id del producto que se quiere buscar y mostrar
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @GetMapping(value = "/mostrarProducto/{id}")
    public String mostrarProducto(@PathVariable String id, Model modelo) {

        ArrayList<ProductoModel> productos = new ArrayList<>();

        try {
            ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));
            productos.add(producto);
            modelo.addAttribute("listaP", productos);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
        } catch (NumberFormatException e) {
            modelo.addAttribute("listaP", productos);
            modelo.addAttribute("listaCarro", servicioCarro.getAll());
            modelo.addAttribute("listaTotal", generarTotal());
        }
        return "/Cliente_ProductoSeleccionado";
    }

    /**
     * Metodo que se encarga de eliminar un producto del carro de compras con el
     * id del producto que se manda. Luego redirecciona a la vista HTML
     * Cliente_Productos
     *
     * @param id Id del producto que se quiere eliminar del carro de compras
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @GetMapping(value = "/eliminarDelCarroProducto/{id}")
    public String eliminarDelCarro(@PathVariable String id, Model modelo) {

        try {
            servicioCarro.eliminar(Long.parseLong(id));
        } catch (NumberFormatException e) {
        }
        return "redirect:/Cliente_Productos";
    }

    /**
     * Metodo que se encarga de editar la cantidad de un producto guardado en el
     * carro de compras. Luego redirecciona a la vista HTML Cliente_Productos.
     *
     * @param idProducto Id del producto que se encuentra en el carro de compras
     * @param idCateg Id de la categoria de los productos mostrados en la vista
     * actual Cliente_Productos
     * @param cantProducto Nueva cantidad del producto que se encuentra en el
     * carro de compras
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "cambiarCantidadProd", method = RequestMethod.POST)
    public String cambiarCantidadProducto(int idProducto, int idCateg, int cantProducto, Model modelo) {
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
            if (x.getIdCateg().getId().equals(Long.valueOf(idCateg))) {
                listaP.add(x);
            }
        }

        modelo.addAttribute("listaP", listaP);
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "/Cliente_Productos";
    }

}
