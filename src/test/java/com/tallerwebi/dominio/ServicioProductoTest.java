package com.tallerwebi.dominio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
public class ServicioProductoTest {

    @Mock
    private RepositorioProducto repositorioProducto;

    @InjectMocks
    private ServicioProducto servicioProducto;

    @BeforeEach
    public void init(){
        this.repositorioProducto=mock(RepositorioProducto.class);
        this.servicioProducto=new ServicioProductoImpl(this.repositorioProducto);
    }

    @Test
    public void dadoQueExisteUnServicioProductoPuedoGuardarUnProducto(){
       //Preparación
        Producto producto= new Producto();
        producto.setDescripcion("Aceitunas");
        producto.setStock(130);
        producto.setPrecio(23.50);
        doNothing().when(this.repositorioProducto).agregarProducto(producto);

        //Ejecución
        this.servicioProducto.ingresarProducto(producto);

        //Validación
        verify(this.repositorioProducto, times(1)).agregarProducto(producto);
    }

    @Test
    public void queSePuedanObtenerTodosLosProductos(){
        //Preparación
        Producto producto= new Producto();
        producto.setDescripcion("Aceitunas");
        producto.setStock(130);
        producto.setPrecio(23.50);

        Producto producto1= new Producto();
        producto1.setDescripcion("tomates");
        producto1.setStock(23);
        producto1.setPrecio(45.20);

        Producto producto2= new Producto();
        producto2.setDescripcion("oregano");
        producto2.setStock(38);
        producto2.setPrecio(60.80);

        List<Producto>listaDeProductos = Arrays.asList(producto,producto1, producto2);

        when(repositorioProducto.obtenerTodosLosProductos()).thenReturn(listaDeProductos);

        //Ejecución
        List<Producto>productos=servicioProducto.obtenerTodosLosProductos();

        //Validación
        assertThat(productos,is(notNullValue()));
        assertThat(productos.size(), is(3));
        assertThat(productos.get(1).getStock(),is(23));

    }

    @Test
    public void dadoQueExisteUnServicioProductoPuedoBuscarStockMayorACien(){
        //Preparación
        Producto producto= new Producto();
        producto.setDescripcion("Aceitunas");
        producto.setStock(130);
        producto.setPrecio(23.50);

        Producto producto1= new Producto();
        producto1.setDescripcion("tomates");
        producto1.setStock(230);
        producto1.setPrecio(45.20);

        List<Producto> listaMock = Arrays.asList(producto, producto1);
        when(repositorioProducto.buscarStockMayorA(100)).thenReturn(Arrays.asList(producto,producto1));

        //Ejecución
        List<Producto> productos= servicioProducto.buscarStockMayorA(100);

       //Validación
        assertThat(productos.size(), is(2));
        assertThat(productos.get(0).getDescripcion(), is("Aceitunas"));
        verify(repositorioProducto, times(1)).buscarStockMayorA(100);
    }



}
