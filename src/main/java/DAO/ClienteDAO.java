package DAO;

import modelo.cliente.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ClienteDAO implements IDao<Cliente> {

    @Override
    public Optional<Cliente> getById(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Cliente cliente = session.get(Cliente.class, email); // El email es la PK de la tabla cliente
            return Optional.ofNullable(cliente);
        } catch (Exception e) {
            System.err.println("Error al obtener cliente con email: " + email + " " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Cliente> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Cliente> clientes = session.createQuery("FROM Cliente", Cliente.class).getResultList(); // JPA
            return clientes;
        } catch (Exception e) {
            System.err.println("Error al obtener todos los clientes " + e.getMessage());
        }
        return List.of();
    }


    @Override
    public void save(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {  // TODO Pendiente verificar &&
                transaction.rollback();
            }
            System.err.println("Error al guardar cliente " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void update(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cliente);   // TODO Quizas deberia usar merge?
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar cliente " + e.getMessage());
        }
    }


    @Override
    public void delete(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar cliente " + e.getMessage());
        }
    }
}
