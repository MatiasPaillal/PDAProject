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
     * Metodo el cual se encarga de redireccionar a la vista del cliente de
     * categorias
     *
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return la vista HTML Cliente_Categorias a la cual accede el cliente,
     * ademas de los datos de una lista de categorias, y una lista con el
     * contenido de un carro de compras y su total. Datos los cuales estan
     * almacenados en el modelo
     */
    @GetMapping("/Cliente_Categorias")
    String Cliente_Categorias(Model modelo) {

        modelo.addAttribute("listaC", servicioCategoria.getAll());
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "Cliente_Categorias";
    }

    /**
     * Metodo el cual se encarga obtener todos los productos de una categoria
     * seleccionada
     *
     * @param idCateg Este parametro representa el atributo categoria de un
     * objeto de la clase Producto
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return En caso de que halla productos con la categoria seleccionada se
     * redireccionara la la vista con estos productos, en caso contrario se
     * redireccionara a la vista de categorias.
     */
    @RequestMapping(value = "mostrarProductosPorCategoria", method = RequestMethod.POST)
    public String mostrarProductosCategoria(String idCateg, Model modelo) {
        ArrayList<ProductoModel> productos = (ArrayList<ProductoModel>) servicioProducto.getAll();
        ArrayList<ProductoModel> productosCateg = new ArrayList<>();

        if (!productos.isEmpty()) {

            for (int i = 0; i < productos.size(); i++) {
                if ((productos.get(i).getIdCateg().getId() == Long.parseLong(idCateg))) {
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

    /**
     * Este metodo permite eliminar un producto de el carro de compras en la
     * vista de Cliente_Categorias
     *
     * @param id Este parametro representa el id de el producto que sera
     * eliminado del carro de compras
     * @return la vista de Cliente_Categorias a la cual accede el cliente
     */
    @GetMapping(value = "/eliminarDelCarroCategoria/{id}")
    public String eliminarDelCarro(@PathVariable String id) {

        try {
            servicioCarro.eliminar(Long.parseLong(id));
        } catch (NumberFormatException e) {
        }
        return "redirect:/Cliente_Categorias";

    }

    /**
     * Este metodo se encarga de consultar los datos de un producto a la base de
     * datos
     *
     * @param id Este parametro representa el atributo idProducto que tendra un
     * objeto de la clase Producto
     * @param modelo El parametro modelo permite almacenar datos, a los cuales
     * se pueden acceder desde los HTML
     * @return En caso de que se encuentre el producto se redireccionara a la
     * vista del producto seleccionado, en caso contrario redireccionara a la
     * vista de categorias a la cual accede el cliente
     *
     */
    @RequestMapping(value = "mostrarProductoCateg", method = RequestMethod.POST)
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

    @RequestMapping(value = "agregarProductoCarro", method = RequestMethod.POST)
    public String agregarProductoCarro2(String id, int cantidad, Model modelo) {

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

    @RequestMapping(value = "cambiarCantidadCateg", method = RequestMethod.POST)
    public String cambiarCantidadProducto(int idProducto, int cantProducto, Model modelo) {
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

        modelo.addAttribute("listaC", servicioCategoria.getAll());
        modelo.addAttribute("listaCarro", servicioCarro.getAll());
        modelo.addAttribute("listaTotal", generarTotal());

        return "/Cliente_Categorias";
    }

}
