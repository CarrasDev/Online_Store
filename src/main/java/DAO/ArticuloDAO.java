package DAO;

import modelo.Articulo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


import java.util.List;
import java.util.Optional;

public class ArticuloDAO implements IDao<Articulo> {

    @Override
    public Optional<Articulo> getById(String codigo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Articulo articulo = session.get(Articulo.class, codigo);
            return Optional.ofNullable(articulo);
        } catch (Exception e) {
            System.err.println("Error al acceder a la BBDD con email: " + codigo + " " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Articulo> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Articulo> listaArticulos = session.createQuery("FROM Articulo", Articulo.class).list(); // TODO Pendiente verificar uso
            return listaArticulos;
        } catch (Exception e) {
            System.err.println("Error al acceder a toda la BBDD " + e.getMessage());
        }
        return null;
    }


    @Override
    public void save(Articulo articulo) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(articulo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
               transaction.rollback();
            }
            System.err.println("Error al guardar en la BBDD " + e.getMessage());
        }
    }


    @Override
    public void update(Articulo articulo) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(articulo);  // update() ya no se usa.
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar la BBDD " + e.getMessage());
        }
    }


    @Override
    public void delete(Articulo articulo) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(articulo);  // delete() ya no se usa.
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar de la BBDD " + e.getMessage());
        }
    }
}
