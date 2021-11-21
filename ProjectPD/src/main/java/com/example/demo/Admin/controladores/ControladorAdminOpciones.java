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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Esta clase de tipo controlador se encarga de realizar las acciones propias
 * del perfil de administrador el la vista Admin_Opciones y redireccionar a la vista html correspondiente
 *
 * @author matias
 */
@Controller
public class ControladorAdminOpciones {

    /**
     * Importacion de metodos del servicio de Admistrador
     */
    @Autowired
    private ServicioAdmin servicioAdmin;
    
    
    /**
     * Importacion de metodos del servicio de Producto
     */        
    @Autowired
    private ServicioProducto servicioProducto;

    /**
     * Importacion de metodos del servicio de Categoria
     */
    @Autowired
    private ServicioCategoria servicioCategoria;
    
    /**
     * Importacion de metodos del servicio de Carro
     */
    @Autowired
    private ServicioCarro servicioCarro;
    /**
     * Importacion de metodos del servicio de RegistrosModificaciones
     */

    @Autowired
    private ServicioRegistrosModificaciones servicioRegistrosModificaciones;

    /**
     * Metodo que se encarga de recibir los datos de un formulario de un
     * producto y agregar un registro con estos a la base de datos
     *
     * @param admin es una cadena que representa el nombre de usuario de un
     * administrador
     * @param fURL es una cadena que representa el enlace de una imagen de un
     * producto
     * @param fnombre es una cadena que representa el nombre de un producto
     * @param fprecio es una cadena que representa el precio de un producto
     * @param categorias es una cadena que representa la categoria de un
     * producto
     * @return Un objeto ModelAndView que contiene los datos del administrador
     */
    @RequestMapping(value = "guardarProducto", method = RequestMethod.POST)
    public ModelAndView guardarProducto(String admin, String fURL, String fnombre, String fprecio, String categorias) {

        CategoriaModel categoria = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categorias)));
        ProductoModel producto = new ProductoModel(fnombre, Integer.parseInt(fprecio), categoria, fURL);
        servicioProducto.guardar(producto);

        ArrayList<ProductoModel> productosBD = (ArrayList<ProductoModel>) servicioProducto.getAll();
        RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Guardó", LocalDateTime.now(), servicioAdmin.obtener(admin), String.valueOf(productosBD.get(productosBD.size() - 1).getIdProducto()));
        servicioRegistrosModificaciones.guardar(registro);

        ModelAndView vista = new ModelAndView("forward:/Opciones");

        vista.addObject("admin", admin);

        return vista;
    }

    /**
     * Metodo que se encarga de recibir el un id de un producto para luego
     * eliminarlo de la base de datos
     *
     * @param producto representa el codigo de un producto
     * @param admin es una cadena que representa el nombre de usuario de un
     * administrador
     * @return Un objeto ModelAndView que contiene los datos del administrador
     */
    @RequestMapping(value = "eliminarProducto")
    public ModelAndView eliminarProducto(String producto, String admin) {

        ModelAndView m = new ModelAndView("forward:/Opciones");

        m.addObject("admin", admin);
        try {
            servicioProducto.eliminar(Long.parseLong(producto));

            RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Eliminó", LocalDateTime.now(), servicioAdmin.obtener(admin), producto);
            servicioRegistrosModificaciones.guardar(registro);

        } catch (NumberFormatException e) {
        }

        return m;
    }

    /**
     * Metodo que se encarga de recibir los datos de un formulario de un
     * producto y actualizar un registro en la base de datos mediante un Id
     *
     * @param admin es una cadena que representa el nombre de usuario de un
     * administrador
     * @param id una cadena que representa el el id de un producto
     * @param url es una cadena que representa el enlace de una imagen de un
     * producto
     * @param nombre es una cadena que representa el nombre de un producto
     * @param precio es una cadena que representa el precio de un producto
     * @param categoria es una cadena que representa la categoria de un producto
     * @return Un objeto ModelAndView que contiene los datos del administrador
     */
    @RequestMapping(value = "actualizarProducto", method = RequestMethod.POST)
    public ModelAndView actualizarProducto(String admin, String id, String url, String nombre, int precio, String categoria) {
        CategoriaModel categoriaProducto = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categoria)));

        ProductoModel producto = new ProductoModel(Long.parseLong(id), nombre, precio, categoriaProducto, url);
        servicioProducto.guardar(producto);

        RegistrosModificacionesModel registro = new RegistrosModificacionesModel("Actualizó", LocalDateTime.now(), servicioAdmin.obtener(admin), id);
        servicioRegistrosModificaciones.guardar(registro);

        ModelAndView m = new ModelAndView("forward:/Opciones");
        m.addObject("admin", admin);

        return m;
    }

    /**
     * Metodo que se encarga de recibir los datos de un formulario y enviarlos a
     * otro controlador
     *
     * @param codigo es una cadena que representa el id de un producto
     * @param admin es una cadena que representa el nombre de usuario de un
     * administrador
     * @return Un objeto ModelAndView que contiene los datos del administrador
     */
    @RequestMapping(value = "buscarProducto", method = RequestMethod.POST)
    public ModelAndView consultaProductos(String codigo, String admin) {
        ModelAndView m = new ModelAndView("forward:/Opciones");

        m.addObject("codigo", codigo);
        m.addObject("admin", admin);
        return m;

    }

    /**
     * Metodo que se encarga tanto de recibir un id de un producto desde el
     * controlador consultaProductos y consultar por un registro existente a la
     * base de datos
     *
     * @param codigo es una cadena que representa id de un producto
     * @param admin es una cadena que representa el nombre de usuario de un
     * administrador
     * @param modelo permite almacenar datos para luego enviarlos a las vistas
     * HTML
     * @return la vista de con las opciones permitidas a un Administrador
     */
    @RequestMapping(value = "Opciones")
    public String verOpciones(@ModelAttribute("codigo") String codigo, @ModelAttribute("admin") String admin, Model modelo) {

        if (!codigo.equals("") && servicioProducto.obtener(Long.parseLong(codigo)) != null) {

            ArrayList<ProductoModel> productos = new ArrayList<ProductoModel>();
            productos.add(servicioProducto.obtener(Long.parseLong(codigo)));

            modelo.addAttribute("lista", productos);
        } else {
            modelo.addAttribute("lista", servicioProducto.getAll());
        }

        modelo.addAttribute("listaAdmin", servicioAdmin.obtener(admin));
        modelo.addAttribute("listaC", servicioCategoria.getAll());
        modelo.addAttribute("listaHistorial", servicioRegistrosModificaciones.getAll());

        return "Admin_Opciones";

    }
}
