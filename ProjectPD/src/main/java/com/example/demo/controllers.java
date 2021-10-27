package com.example.demo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.example.demo.Admin.modelo.Administrador;
import com.example.demo.Admin.modelo.CategoriaModel;
import com.example.demo.Admin.modelo.ProductoModel;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Admin.servicios.ServicioAdmin;
import com.example.demo.Admin.servicios.ServicioCategoria;
import com.example.demo.Admin.servicios.ServicioProducto;
import com.example.demo.Boleta;
import com.example.demo.Carro;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author matias
 */
@Controller
public class controllers {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioCategoria servicioCategoria;

    Boleta boleta = new Boleta("a", "a", "a", "a", "a", "a", "a");

    @GetMapping("/")
    String LocalHost() {

        return "Ingresar";
    }

    @GetMapping("/Cliente_Categorias")
    String Cliente_Categorias(Model modelo) {
        modelo.addAttribute("listaC", servicioCategoria.getAll());
        
        return "Cliente_Categorias";
    }
     

    @GetMapping("/Ejemplo")
    String ej() {
        return "ejemplo";
    }

    @GetMapping("/Cliente_Productos")
    String Cliente_Productos(Model modelo) {
        modelo.addAttribute("listaP", servicioProducto.getAll());
        return "Cliente_Productos";
    }

    @GetMapping("/Cliente_ProductoSeleccionado")
    String Cliente_ProductoSeleccionado() {
        return "Cliente_ProductoSeleccionado";
    }

    @GetMapping("/Cliente_Boleta")
    String Cliente_Boleta() {
        return "Cliente_Boleta";
    }

    @GetMapping("/Ingresar")
    String Ingresar() {
        return "Ingresar";
    }

    @GetMapping("/Admin")
    String IngresarAdmin() {
        return "IngresarAdmin";
    }

    @GetMapping("/Admin_Categorias")
    String desplegarOpcionesAdmin() {
        return "Admin_Categorias";
    }

    @GetMapping("/Admin_BuscarBoletas")
    String mostrarBoletasAdmin() {
        return "Admin_BuscarBoletas";
    }

    @GetMapping("/Boletas")
    String Boleta(@RequestParam(name = "Boleta", required = false, defaultValue = "asda") Boleta boleta, Model modelo) {
        modelo.addAttribute(this.boleta);
        return "Boleta";
    }

    @GetMapping("/AgregarProducto")
    String agregarProducto() {
        return "Admin_AgregarP";
    }

    @GetMapping("/Admin_Productos")
    String mostrarProductoAdmin() {
        return "Admin_Productos";
    }

    @GetMapping("/Admin_ProductoSeleccionado")
    String mostrarProductoSeleccionadoAdmin(Model modelo) {

        return "Admin_ProductoSeleccionado";
    }

    /*
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String consultaAdmin(String name, String password) {
        if ("1".equals(name) && "1".equals(password)) {
            return "Admin_Opciones";
        }

        return "IngresarAdmin";

   
     */
    @RequestMapping(value = "consultaAdmin", method = RequestMethod.POST)
    public String admin_Opciones(String usuario, String password, Model modelo) {
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        Administrador admins = (Administrador) servicioAdmin.obtener(usuario);

        try {
            if (admins.getPassword().equals(password)) {
                return "Admin_Opciones";
            }
        } catch (NullPointerException e) {
        }

        return "IngresarAdmin";
    }
    @RequestMapping(value = "buscarProducto", method = RequestMethod.POST)
    public String consultaProductos(String codigo, Model modelo) {
        ProductoModel producto = servicioProducto.obtener(Long.parseLong(codigo));
       modelo.addAttribute("listaAdmin", servicioAdmin.getAll()); 
       modelo.addAttribute("listaC", servicioCategoria.getAll());
       ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto);
       
       if(productos != null){ 
        modelo.addAttribute("lista", productos);
        return "Admin_Opciones";
       }else{
         modelo.addAttribute("lista", servicioProducto.getAll());
        return "Admin_Opciones";
       }
         
    }

    @RequestMapping(value = "guardarProducto", method = RequestMethod.POST)
    public String guardarProducto(String fURL, String fnombre, String fprecio, String categorias, Model modelo) {
        CategoriaModel categoria = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categorias)));

        ProductoModel producto = new ProductoModel(fnombre, Integer.parseInt(fprecio), categoria, fURL);

        servicioProducto.guardar(producto);
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "redirect:opciones";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id, Model modelo) {

        try {
            servicioProducto.eliminar(Long.parseLong(id));
        }
        catch(NumberFormatException e){
        }
        return "redirect:/opciones";
    }
    
    
       @RequestMapping(value = "actualizarProducto", method = RequestMethod.POST)
    public String actualizarProducto(String id,String url, String nombre, String precio, String categoria, Model modelo) {
        CategoriaModel categoriaProducto = (CategoriaModel) servicioCategoria.obtener((Long.parseLong(categoria)));

        ProductoModel producto = new ProductoModel(Long.parseLong(id),nombre, Integer.parseInt(precio), categoriaProducto, url);

        servicioProducto.guardar(producto);
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "redirect:opciones";
    }
    
        @RequestMapping(value = "mostrarProductoCategoria", method = RequestMethod.POST)
    public String actualizarProducto(String id, Model modelo) {
        ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));
      
             
       if(producto != null){
       ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto); 
        modelo.addAttribute("listaP", productos);
        return "/Cliente_ProductoSeleccionado";
       }else{
           modelo.addAttribute("listaC", servicioCategoria.getAll());
          return "Cliente_Categorias";
       }
         
    }
         @RequestMapping(value = "mostrarProducto", method = RequestMethod.POST)
    public String mostrarProductoCategoria(String id, Model modelo) {
        ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));
      
             
       if(producto != null){
       ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto); 
        modelo.addAttribute("listaP", productos);
        return "/Cliente_ProductoSeleccionado";
       }else{
           modelo.addAttribute("listaP", servicioProducto.getAll());
        return "Cliente_Productos";
       }
         
    }

   
    
     @GetMapping(value = "/mostrarProducto/{id}")
    public String mostrarProducto(@PathVariable String id, Model modelo) {
        ProductoModel producto = (ProductoModel) servicioProducto.obtener(Long.parseLong(id));
             
        
        ArrayList<ProductoModel> productos= new ArrayList<ProductoModel>();
       productos.add(producto);
        try {
            modelo.addAttribute("listaP", productos);
        }
        catch(NumberFormatException e){
        }
        return "/Cliente_ProductoSeleccionado";
    }

    /*
    
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

    @RequestMapping(value = "opciones")
    public String guardarProducto(String nombre, Model modelo) {
        modelo.addAttribute("listaAdmin", servicioAdmin.getAll());
        modelo.addAttribute("lista", servicioProducto.getAll());
        modelo.addAttribute("listaC", servicioCategoria.getAll());

        return "Admin_Opciones";

    }

    @RequestMapping(value = "productos")
    public String verProductoAdmin(String nombre) {

        return "Admin_Productos";

    }

}
