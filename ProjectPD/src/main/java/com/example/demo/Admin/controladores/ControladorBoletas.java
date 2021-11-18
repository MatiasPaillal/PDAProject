package com.example.demo.Admin.controladores;

import com.example.demo.Admin.modelo.BoletaModel;
import com.example.demo.Admin.modelo.CarroModel;
import com.example.demo.Admin.modelo.TiendaModel;
import com.example.demo.Admin.servicios.ServicioBoleta;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Admin.servicios.ServicioCarro;
import com.example.demo.Admin.servicios.ServicioTienda;
import com.example.demo.Boleta;
import java.time.LocalDateTime;

/**
 * @author matias
 */
@Controller
public class ControladorBoletas {

    @Autowired
    private ServicioCarro servicioCarro;

    @Autowired
    private ServicioTienda servicioTienda;
    
    @Autowired
    private ServicioBoleta servicioBoleta;
 

    @GetMapping("/Cliente_Boleta")
    String Cliente_Boleta(Model modelo) {
        modelo.addAttribute("infoTienda", servicioTienda.getAll());
        return "Cliente_Boleta";
    }

    @GetMapping("/Boletas")
    String Boleta(@RequestParam(name = "Boleta", required = false, defaultValue = "asda") Boleta boleta, Model modelo) {
        //modelo.addAttribute(this.boleta);
        return "Boleta";
    }

    @RequestMapping(value = "realizarCompra", method = RequestMethod.POST)
    public String generarBoleta(Model modelo) {
        TiendaModel tienda = servicioTienda.getAll().get(0);

        ArrayList<CarroModel> carro = (ArrayList<CarroModel>) servicioCarro.getAll();
        int totalCompra = 0;

        for (CarroModel producto : carro) {
            totalCompra += producto.getIdProductoCarro().getPrecio() * producto.getCantidad();
        }

        BoletaModel boleta = new BoletaModel(LocalDateTime.now(), totalCompra, tienda);
        servicioBoleta.guardar(boleta);
        
        
        
        modelo.addAttribute("totalCompra", totalCompra);
        modelo.addAttribute("boleta", boleta);
        modelo.addAttribute("listaP", servicioCarro.getAll());
        modelo.addAttribute("infoTienda", servicioTienda.getAll());
        servicioCarro.vaciarTabla();
        return "Cliente_Boleta";
    }

    /*          @RequestMapping(value = "mostrarProducto", method = RequestMethod.POST)
    public String mostrarProductoCategoria(String nombre, Model modelo) {
        
      ArrayList <ProductoModel> productos= (ArrayList <ProductoModel>) servicioProducto.getAll();
      
      for(int i=0; i<productos.size();i++){
      if(nombre==0)
      
      
      }
             
       if(producto != null){
       ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto); 
        modelo.addAttribute("listaP", productos);
        return "/Cliente_ProductoSeleccionado";
       }else{
           modelo.addAttribute("listaP", servicioProducto.getAll());
        return "Cliente_Productos";
       }
     
    
    @RequestMapping("/Admin_Opciones")
     public String mostrarAdmin(Model modelo) {
        modelo.addAttribute("lista", servicioProducto.getAll());
        return "Admin_Opciones";
    }
   
  
      
     @GetMapping("/guardar/{id}")
     public String mostrarAdminGuardad(@PathVariable("id") int id, Model modelo) {
        if(id!=0){
            modelo.addAttribute("admin", servicioAdmin.obtener(id));
        }else{
            modelo.addAttribute("admin", new Administrador());
        }
        
        return "Admins";
    }
     */
    @RequestMapping(value = "buscarBoleta", method = RequestMethod.POST)
    public String buscarBoleta(String numero) {
        if ("11111".equals(numero)) {
            return "Admin_Boleta";
        }

        return "Admin_BuscarBoletas";

    }

}
