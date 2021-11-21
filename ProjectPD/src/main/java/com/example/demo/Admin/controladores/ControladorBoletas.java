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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Admin.servicios.ServicioTienda;

import java.time.LocalDateTime;

/**
 * @author matias
 */
@Controller
public class ControladorBoletas {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioCarro servicioCarro;

    @Autowired
    private ServicioTienda servicioTienda;

    @Autowired
    private ServicioBoleta servicioBoleta;
    @Autowired
    private ServicioProducto servicioProducto;

    @GetMapping("/Cliente_Boleta")
    String Cliente_Boleta(Model modelo) {
        modelo.addAttribute("infoTienda", servicioTienda.getAll());
        return "Cliente_Boleta";
    }

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

    @RequestMapping(value = "buscarBoleta", method = RequestMethod.POST)
    public String buscarBoleta(String admin, String nroBoleta, Model modelo) {
     
        int totalCompra = 0;
        BoletaModel boleta = servicioBoleta.obtener(Long.parseLong(nroBoleta));
        String[] partes = boleta.getProductosCantidad().split(":");

        String[] cadenaProductos = partes[0].split(";");
        String[] cadenaCantidad = partes[1].split(";");
        System.out.println(cadenaCantidad.length);
        System.out.println(cadenaProductos.length);
        ArrayList<CarroModel> carro = new ArrayList<CarroModel>();

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
    }

}
