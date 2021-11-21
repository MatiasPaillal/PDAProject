package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.BoletaModel;
import com.example.demo.Admin.modelo.CarroModel;
import com.example.demo.Admin.modelo.TiendaModel;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioBoleta;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.servicios.ServicioRegistrosModificaciones;
import com.example.demo.Admin.servicios.ServicioTienda;

import java.time.LocalDateTime;

/**
 * Clase ControladorBoletas de tipo controlador
 *
 * Se encarga de realizar las acciones correspondientes a las boletas y
 * redireccionar a las respectivas vistas html
 *
 * @author Autoservicio
 */
@Controller
public class ControladorBoletas {

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
     * Importacion de metodos del servicio de Administrador
     */
    @Autowired
    private ServicioAdmin servicioAdmin;
    /**
     * Importacion de metodos del servicio de Carro
     */
    @Autowired
    private ServicioCarro servicioCarro;
    /**
     * Importacion de metodos del servicio de Tienda
     */
    @Autowired
    private ServicioTienda servicioTienda;
    /**
     * Importacion de metodos del servicio de Boleta
     */
    @Autowired
    private ServicioBoleta servicioBoleta;
    /**
     * Importacion de metodos del servicio de Producto
     */
    @Autowired
    private ServicioProducto servicioProducto;

    /**
     * Método que genera una boleta con la información almacenada en la base de
     * datos y redirecciona a la vista HTML Cliente_Boleta con toda la
     * información de la boleta.
     *
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     *
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "realizarCompra", method = RequestMethod.POST)
    public String generarBoleta(Model modelo) {
        TiendaModel tienda = servicioTienda.getAll().get(0);
        String productosCantidad = "";
        int totalCompra = 0;

        ArrayList<CarroModel> carro = (ArrayList<CarroModel>) servicioCarro.getAll();

        for (CarroModel producto : carro) {
            totalCompra += producto.getIdProductoCarro().getPrecio() * producto.getCantidad();
            productosCantidad += producto.getIdProductoCarro().getIdProducto() + ";"; //Simbolo que separa un codigo de producto del siguiente.
        }

        productosCantidad += ":"; //Simbolo que separa los codigos de los productos de las cantidades de estos.

        for (CarroModel producto : carro) {
            productosCantidad += producto.getCantidad() + ";"; //Simbolo que separa una cantidad de producto de la siguiente.
        }

        BoletaModel boleta = new BoletaModel(LocalDateTime.now(), totalCompra, tienda, productosCantidad);
        servicioBoleta.guardar(boleta);

        modelo.addAttribute("totalCompra", totalCompra);
        modelo.addAttribute("boleta", boleta);
        modelo.addAttribute("listaP", servicioCarro.getAll());
        modelo.addAttribute("infoTienda", servicioTienda.getAll());
        modelo.addAttribute("isAdmin", "0");
        servicioCarro.vaciarTabla();
        return "Cliente_Boleta";
    }

    /**
     * Metodo que genera una boleta con la informacion almacenada en la base de
     * datos y redirecciona a la vista HTML Cliente_Boleta con toda la
     * informacion de la boleta.
     *
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     *
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "mostrarProducto/realizarCompra", method = RequestMethod.POST)
    public String generarBoleta2(Model modelo) {
        TiendaModel tienda = servicioTienda.getAll().get(0);
        String productosCantidad = "";
        int totalCompra = 0;

        ArrayList<CarroModel> carro = (ArrayList<CarroModel>) servicioCarro.getAll();

        for (CarroModel producto : carro) {
            totalCompra += producto.getIdProductoCarro().getPrecio() * producto.getCantidad();
            productosCantidad += producto.getIdProductoCarro().getIdProducto() + ";"; //Simbolo que separa un codigo de producto del siguiente.
        }

        productosCantidad += ":"; //Simbolo que separa los codigos de los productos de las cantidades de estos.

        for (CarroModel producto : carro) {
            productosCantidad += producto.getCantidad() + ";"; //Simbolo que separa una cantidad de producto de la siguiente.
        }

        BoletaModel boleta = new BoletaModel(LocalDateTime.now(), totalCompra, tienda, productosCantidad);
        servicioBoleta.guardar(boleta);

        modelo.addAttribute("totalCompra", totalCompra);
        modelo.addAttribute("boleta", boleta);
        modelo.addAttribute("listaP", servicioCarro.getAll());
        modelo.addAttribute("infoTienda", servicioTienda.getAll());
        modelo.addAttribute("isAdmin", "0");
        servicioCarro.vaciarTabla();
        return "Cliente_Boleta";
    }

    /**
     * Metodo que se encarga de buscar una boleta a traves del numero de boleta
     * y luego redirecciona al HTML Cliente_Boleta con la informacion de esta.
     * En caso de no encontrarse la boleta, se redirecciona al mismo HTML, en
     * este caso a Admin_Opciones
     *
     * @param admin String con el nombre de usuario del administrador
     * @param nroBoleta String con el numero de la boleta que se quiere buscar
     * @param modelo Permite almacenar datos, a los cuales se pueden acceder
     * desde los HTML
     *
     * @return String con el nombre de la vista HTML a donde se redireccionará
     */
    @RequestMapping(value = "buscarBoleta", method = RequestMethod.POST)
    public String buscarBoleta(String admin, String nroBoleta, Model modelo) {

        int totalCompra = 0;
        BoletaModel boleta = servicioBoleta.obtener(Long.parseLong(nroBoleta));
        if (boleta != null) {
            String[] partes = boleta.getProductosCantidad().split(":");

            String[] cadenaProductos = partes[0].split(";");
            String[] cadenaCantidad = partes[1].split(";");

            ArrayList<CarroModel> carro = new ArrayList<>();

            for (int i = 0; i < cadenaProductos.length; i++) {
                totalCompra += servicioProducto.obtener(Long.parseLong(cadenaProductos[i])).getPrecio() * Integer.parseInt(cadenaCantidad[i]);
                carro.add(new CarroModel(servicioProducto.obtener(Long.parseLong(cadenaProductos[i])), Integer.parseInt(cadenaCantidad[i])));
            }

            modelo.addAttribute("listaAdmin", servicioAdmin.obtener(admin));
            modelo.addAttribute("isAdmin", "1");
            modelo.addAttribute("totalCompra", totalCompra);
            modelo.addAttribute("boleta", boleta);
            modelo.addAttribute("listaP", carro);
            modelo.addAttribute("infoTienda", servicioTienda.getAll());
            return "Cliente_Boleta";

        } else {
            modelo.addAttribute("listaAdmin", servicioAdmin.obtener(admin));
            modelo.addAttribute("listaC", servicioCategoria.getAll());
            modelo.addAttribute("listaHistorial", servicioRegistrosModificaciones.getAll());
            modelo.addAttribute("lista", servicioProducto.getAll());

            return "Admin_Opciones";

        }
    }

}
