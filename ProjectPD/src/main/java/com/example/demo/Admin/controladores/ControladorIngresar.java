package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.AdministradorModel;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.servicios.ServicioRegistrosModificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Clase ControladorIngresar de tipo controlador
 *
 * Se encarga de realizar las acciones correspondientes a la vista Ingresar, y
 * redireccionar a las respectivas vistas html
 *
 * @author Autoservicio
 */
@Controller
public class ControladorIngresar {

    /**
     * Importacion de metodos del servicio de Administrador
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
     * Importacion de metodos del servicio de Registro de modificaciones
     */
    @Autowired
    private ServicioRegistrosModificaciones servicioRegistrosModificaciones;

    /**
     * Metodo que se encarga de redireccionar a la vista HTML Ingresar
     *
     * @return String con el nombre de la vista HTML Ingresar
     */
    @GetMapping("/")
    String LocalHost() {
        return "Ingresar";
    }

    /**
     * Metodo que se encarga de redireccionar a la vista HTML Ingresar
     *
     * @return String con el nombre de la vista HTML Ingresar
     */
    @GetMapping("/Ingresar")
    String Ingresar() {
        return "Ingresar";
    }

    /**
     * Metodo que se encarga de redireccionar a la vista HTML Admin_Opciones en
     * caso de que el usuario y contraseña coincida con algun administrador
     * almacenado en la base de datos, de lo contrario, se redireccionara a la
     * vista HTML Ingresar.
     *
     * @param usuario String con el nombre de usuario
     * @param password String con la contraseña
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String admin_logear(String usuario, String password, Model modelo) {

        AdministradorModel admins = (AdministradorModel) servicioAdmin.obtener(usuario);

        try {
            if (admins.getPassword().equals(password)) {

                modelo.addAttribute("listaAdmin", servicioAdmin.obtener(usuario));
                modelo.addAttribute("lista", servicioProducto.getAll());
                modelo.addAttribute("listaC", servicioCategoria.getAll());
                modelo.addAttribute("listaHistorial", servicioRegistrosModificaciones.getAll());

                return "Admin_Opciones";
            }
        } catch (NullPointerException e) {
        }

        return "Ingresar";
    }

}
