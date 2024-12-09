package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto{

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioProductoImpl (RepositorioProducto repositorioProducto){this.repositorioProducto=repositorioProducto;}

    @Override
    public List<Producto> buscarStockMayorA(Integer stock) {
       return repositorioProducto.buscarStockMayorA(stock);

    }

    @Override
    public List<Producto> buscarMayorOIgual(Integer stock) {
        return repositorioProducto.buscarMayorOIgual(stock);
    }

    @Override
    public List<Producto> buscarMenorOIgual(Integer stock) {
        return repositorioProducto.buscarMenorOIgual(stock);
    }

    @Override
    public List<Producto> buscarStockIgual(Integer stock) {
        return repositorioProducto.buscarStockIgual(stock);
    }

    @Override
    public Producto buscarProducto(String productoAEliminar) {
        return repositorioProducto.buscarProducto(productoAEliminar);
    }

    @Override
    public void eliminarStock(Producto productoEncontrado) {
        repositorioProducto.eliminarStock(productoEncontrado);
    }

    @Override
    public void incrementarStock(Producto productoEncontrado) {

    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.obtenerTodosLosProductos();
    }


    public Producto buscarProductoPorId(Long productoId){
        return repositorioProducto.buscarProductoPorId(productoId);
    }

    public void actualizarProducto (Producto producto, int cantidad){
        producto.setStock(producto.getStock()+cantidad);
        repositorioProducto.modificar(producto);
    }

    @Override
    public void ingresarProducto(Producto producto) {
        repositorioProducto.agregarProducto(producto);
    }
}
