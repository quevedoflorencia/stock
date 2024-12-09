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
import org.springframework.web.servlet.View;

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


    @GetMapping("/consulta-stock")
    public ModelAndView consultarStock(Integer stock) {
        ModelMap model = new ModelMap();

        List<Producto> listaProductos = servicioProducto.buscarStockMayorA(stock);


        model.put("resultado", listaProductos);
        return new ModelAndView("formulario-consulta", model);
    }


    @GetMapping("/menu-consultas")
    public ModelAndView irAMenuConsultas() {

        ModelMap modelo = new ModelMap();

        return new ModelAndView("menu-consultas", modelo);
    }


    @GetMapping("/procesar-consulta")
    public ModelAndView irAMenuConsultas(@RequestParam(name = "tipoConsulta") String tipoConsulta) {

        ModelMap modelo = new ModelMap();

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

            case "4":
                modelo.put("producto", new Producto());
                return new ModelAndView("nuevo-producto", modelo);

            default:
                modelo.put("error", "Opción no válida. Por favor selecciona una opción correcta.");
                break;

        }

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


/*
    @RequestMapping(path = "/nuevo-producto", method = RequestMethod.POST)
    public ModelAndView ingresarNuevoProducto(@ModelAttribute("producto") Producto producto) {
        ModelMap model = new ModelMap();

       servicioProducto.ingresarProducto(producto);

        return new ModelAndView("menu-consultas", model);
    }*/

    @RequestMapping(path = "/nuevo-producto", method = RequestMethod.POST)
    public ModelAndView ingresarNuevoProducto(@ModelAttribute("producto") Producto producto) {
        ModelMap model = new ModelMap();

        servicioProducto.ingresarProducto(producto);
        model.put("exito", "Ingresó el producto correctamente");
        return new ModelAndView("nuevo-producto", model);
    }





}

