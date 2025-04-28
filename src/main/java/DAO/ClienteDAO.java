package DAO;

import modelo.cliente.Cliente;
import org.hibernate.Session;
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

    // TODO Pendiente de implementar
    @Override
    public void save(Object o) {

    }

    // TODO Pendiente de implementar
    @Override
    public void update(Cliente cliente) {

    }

    // TODO Pendiente de implementar
    @Override
    public void delete(Cliente cliente) {

    }
}
