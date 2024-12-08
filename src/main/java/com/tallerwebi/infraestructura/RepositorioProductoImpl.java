package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl(SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}

    @Override

    public List<Producto> buscarStockMayorA(Integer stock) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.gt("stock", stock))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override

    public List<Producto> buscarMayorOIgual(Integer stock) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.ge("stock", stock))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<Producto> buscarMenorOIgual(Integer stock) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.le("stock", stock))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<Producto> buscarStockIgual(Integer stock) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.eq("stock", stock))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public Producto buscarProducto(String productoAEliminar) {
        return (Producto) sessionFactory.getCurrentSession().createCriteria(Producto.class)
                .add(Restrictions.eq("descripcion", productoAEliminar))
                .uniqueResult();
    }

    @Override
    public void eliminarStock(Producto productoEncontrado) {
        sessionFactory.getCurrentSession().delete(productoEncontrado);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }


    @Override
    public Producto buscarProductoPorId(Long productoId) {
        return (Producto) sessionFactory.getCurrentSession().createCriteria(Producto.class)
                .add(Restrictions.eq("id", productoId))
                .uniqueResult();
    }


    @Override
    public void modificar(Producto producto) {
        sessionFactory.getCurrentSession().update(producto);
    }

}
