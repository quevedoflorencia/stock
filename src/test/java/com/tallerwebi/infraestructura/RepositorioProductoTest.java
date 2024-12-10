package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioProductoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProducto repositorioProducto;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    @BeforeEach
    public void init(){
        this.repositorioProducto = new RepositorioProductoImpl(this.sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnProducto() {
        //preparación

        Producto producto= new Producto();
        producto.setDescripcion("Aceitunas");
        producto.setStock(130);
        producto.setPrecio(23.50);

        //Ejecución
        this.repositorioProducto.agregarProducto(producto);

        //validación
        sessionFactory.getCurrentSession().save(producto);
       // assertThat(producto.getStock(), is(notNullValue()));
        assertThat(producto.getStock(), equalTo(130));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerStockDeProductosMayorASetenta() {
        //preparación

        Producto producto= new Producto();
        producto.setDescripcion("milanesas");
        producto.setStock(90);
        producto.setPrecio(23.50);

        Producto productoDos= new Producto();
        producto.setDescripcion("pasta dental");
        producto.setStock(120);
        producto.setPrecio(89.50);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria(Producto.class)).thenReturn(criteria);
        when(criteria.add(Restrictions.gt("stock", 70))).thenReturn(criteria);


        List<Producto> listaProductos= Arrays.asList(producto,productoDos);
        when(criteria.list()).thenReturn(listaProductos);

        //Ejecución
        List<Producto> productos = repositorioProducto.buscarStockMayorA(70);

        //validación
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createCriteria(Producto.class);
        verify(criteria, times(1)).add(Restrictions.gt("stock", 70));
        verify(criteria, times(1)).list();

    }
}
