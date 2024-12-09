package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioProducto {
    List <Producto> buscarStockMayorA(Integer stock);
    List <Producto> buscarMayorOIgual(Integer stock);
    List <Producto> buscarMenorOIgual(Integer stock);
    List <Producto> buscarStockIgual(Integer stock);

    Producto buscarProducto(String productoAEliminar);

    void eliminarStock(Producto productoEncontrado);

    void incrementarStock(Producto productoEncontrado);

    List<Producto> obtenerTodosLosProductos();

    Producto buscarProductoPorId(Long ProductoId);

    void actualizarProducto(Producto productoEncontrado, int cantidad);

    void ingresarProducto(Producto producto);
}
