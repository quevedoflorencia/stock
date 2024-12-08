package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorProducto {

    private ServicioProducto servicioProducto;

    @Autowired
    public ControladorProducto(ServicioProducto servicioProducto){
        this.servicioProducto = servicioProducto;
    }

    @RequestMapping("/formulario-consulta")
    public ModelAndView irAConsultatStock() {

        ModelMap modelo = new ModelMap();

        return new ModelAndView("formulario-consulta", modelo);
    }
/*
    @RequestMapping(path = "/consulta-stock", method = RequestMethod.POST)
    public ModelAndView consultarStock(@ModelAttribute("producto") Producto producto) {
        ModelMap model = new ModelMap();

      List<Producto> listaProductos = servicioProducto.buscarStockMayorA(producto.getStock());
     //List<Producto> listaProductos = servicioProducto.buscarMenorOIgual(producto.getStock());
     //List<Producto> listaProductos = servicioProducto.buscarStockIgual(producto.getStock());
     //List<Producto> listaProductos = servicioProducto.buscarMayorOIgual(producto.getStock());
      model.put("valor", producto.getStock());
      model.put("resultado", listaProductos);
      return new ModelAndView("formulario-consulta", model);
    }
*/

    @GetMapping("/consulta-stock")
    public ModelAndView consultarStock(Integer stock) {
        ModelMap model = new ModelMap();

        List<Producto> listaProductos = servicioProducto.buscarStockMayorA(stock);


        model.put("resultado", listaProductos);
        return new ModelAndView("formulario-consulta", model);
    }


    @RequestMapping(path = "/incrementar-stock", method = RequestMethod.POST)
    public ModelAndView incrementar(@ModelAttribute("productoIncrementar") ProductoIncrementar productoIncrementar) {
        ModelMap model = new ModelMap();

        return new ModelAndView("redirect:/login");
    }

    /*
    @GetMapping("/eliminar-stock")
    public ModelAndView eliminarStock(String eliminarProducto) {
        ModelMap model = new ModelMap();

        Producto productoEncontrado=servicioProducto.buscarProducto(eliminarProducto);

         if (productoEncontrado!=null){
          servicioProducto.eliminarStock(productoEncontrado);
          model.put("exito", "eliminó correctamente");
       //  return new ModelAndView("redirect:/home");

         }else if(productoEncontrado==null){
             model.put("error", "El producto no existe");
        }

        return new ModelAndView("eliminar-producto", model);
    }*/

    @GetMapping("/eliminar-stock")
    public ModelAndView eliminarStock(@RequestParam(name = "productoId") Long productoId) {
        ModelMap model = new ModelMap();

        Producto productoEncontrado=servicioProducto.buscarProductoPorId(productoId);

        if (productoEncontrado!=null){
            servicioProducto.eliminarStock(productoEncontrado);
            model.put("exito", "eliminó correctamente");
            //  return new ModelAndView("redirect:/home");

        }else if(productoEncontrado==null){
            model.put("error", "El producto no existe");
        }

        return new ModelAndView("eliminar-producto", model);

    }


    @GetMapping("/menu-consultas")
    public ModelAndView irAMenuConsultas(/*@RequestParam(name = "tipoConsulta", required = false) String tipoConsulta*/) {

        ModelMap modelo = new ModelMap();

     /*  servicioProducto.menuConsultas(opcionSeleccionada);
        if(tipoConsulta  '1'){
          List<Producto> listaProductosEnStock = servicioProducto.buscarStockMayorA(0);
          modelo.put ("listado", listaProductosEnStock);
        }*/
/*
        switch (tipoConsulta){
            case "1":
                List<Producto>listado=servicioProducto.buscarStockMayorA(0);
                break;
            case "2":
                List<Producto>listado2=servicioProducto.buscarStockMayorA(0);
                break;
            case "3":
                List<Producto>listado3=servicioProducto.buscarStockMayorA(0);
                break;
        }*/

        return new ModelAndView("menu-consultas", modelo);
    }


    @GetMapping("/procesar-consulta")
    public ModelAndView irAMenuConsultas(@RequestParam(name = "tipoConsulta") String tipoConsulta) {

        ModelMap modelo = new ModelMap();

     /*  servicioProducto.menuConsultas(opcionSeleccionada);
        if(tipoConsulta  '1'){
          List<Producto> listaProductosEnStock = servicioProducto.buscarStockMayorA(0);
          modelo.put ("listado", listaProductosEnStock);
        }*/

        switch (tipoConsulta){
            case "1":
                modelo.put("ProductosStockMayorACero", servicioProducto.buscarStockMayorA(0));
                break;

            case "2":
                modelo.put("productos", servicioProducto.obtenerTodosLosProductos());
                return new ModelAndView("agregar-stock", modelo);

            case "3":
                modelo.put("productos", servicioProducto.obtenerTodosLosProductos());
                return new ModelAndView("eliminar-producto", modelo);

            default:
            modelo.put("error", "Opción no válida. Por favor selecciona una opción correcta.");
            break;

        }


        /*
            if(tipoConsulta.equals("1")) {
                modelo.put("ProductosStockMayorACero", servicioProducto.buscarStockMayorA(0));
    }*/
        return new ModelAndView("menu-consultas", modelo);
    }


    // Procesar el formulario y actualizar el stock

    @GetMapping("/incrementar-stock")
    public ModelAndView procesarIncrementoStock(@RequestParam(name = "productoId") Long productoId, @RequestParam(name = "cantidad") int cantidad) {
        ModelMap modelo = new ModelMap();

        Producto productoEncontrado = servicioProducto.buscarProductoPorId(productoId);

        servicioProducto.actualizarProducto(productoEncontrado, cantidad);

        modelo.put("mensaje", "Producto actualizado exitosamente");
        return new ModelAndView("agregar-stock", modelo);
    }



}

