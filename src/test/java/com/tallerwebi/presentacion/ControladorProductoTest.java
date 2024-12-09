package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ControladorProductoTest {

	private ControladorProducto controladorProducto;
	private Producto productoMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioProducto servicioProductoMock;


	@BeforeEach
	public void init(){
		//datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		productoMock = mock(Producto.class);
		//when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioProductoMock = mock(ServicioProducto.class);
		controladorProducto = new ControladorProducto(servicioProductoMock);
	}

	@Test
	public void queAlIngresarMuestreElFormularioDeConsultaStock(){
		// preparacion

		// ejecucion
		ModelAndView modelAndView = controladorProducto.irAConsultatStock();

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("formulario-consulta"));

	}


	@Test
	public void queAlConsultarLosProductosConStockMayorAVeinteDevuelvaLaCantidadDeProductosQueCumplenEsaCondicion(){

		//Preparacion
		Producto productoCero = new Producto();
		productoCero.setStock(50);

		Producto productoUno = new Producto();
		productoUno.setStock(24);

		Producto productoDos = new Producto();
		productoDos.setStock(65);

		Producto productoTres = new Producto();
		productoTres.setStock(88);

		List<Producto> listaMock = Arrays.asList(productoCero, productoUno, productoDos, productoTres);

		when(servicioProductoMock.buscarStockMayorA(20)).thenReturn(listaMock);


		//Ejecucion
		ModelAndView modelAndView = controladorProducto.consultarStock(20);


		//Verificacion: (distintas verificaciones que podemos hacer)

		//assertNotNull(modelAndView); // Verifica que el ModelAndView no sea nulo
		//assertEquals("formulario-consulta", modelAndView.getViewName()); // Verifica que la vista devuelta sea "formulario-consulta"

		List<Producto> resultado = (List<Producto>) modelAndView.getModel().get("resultado"); /* Verifica que el modelo contiene la lista de productos bajo la clave "resultado"*/
		assertEquals(4, resultado.size()); // Verifica que haya 4 productos en la lista

		//assertNotNull(resultado); // Verifica que la lista no sea nula

		//assertEquals(productoCero, resultado.get(0)); // Verifica que en la posicion 0 esté productoCero
		//assertEquals(productoDos, resultado.get(2)); // Verifica que en la posicion 2 esté productoDos


		//Mockito.verify(servicioProductoMock).buscarStockMayorA(20); // Verificamos que el servicio fue llamado con el parámetro correcto
		
	}

	/*@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(usuarioMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		// preparacion
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		// preparacion
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}*/
}
