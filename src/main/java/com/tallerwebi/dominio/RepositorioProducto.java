package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProducto {
    List<Producto> buscarStockMayorA(Integer stock);
    List<Producto> buscarMayorOIgual(Integer stock);
    List<Producto> buscarMenorOIgual(Integer stock);
    List<Producto> buscarStockIgual(Integer stock);

    Producto buscarProducto(String productoAEliminar);

    void eliminarStock(Producto productoEncontrado);

    List<Producto> obtenerTodosLosProductos();

    Producto buscarProductoPorId(Long productoId);

    void modificar(Producto producto);

    void agregarProducto(Producto producto);
}
