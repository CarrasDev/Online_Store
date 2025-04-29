package DAO;

import modelo.Pedido;

import java.util.List;
import java.util.Optional;

public class PedidoDAO implements IDao<Pedido> {


    @Override
    public Optional<Pedido> getById(String id) {
        // TODO Pendiente implementar
        return Optional.empty();
    }


    @Override
    public List<Pedido> getAll() {
        // TODO Pendiente implementar
        return List.of();
    }

    @Override
    public void save(Pedido pedido) {
        // TODO Pendiente implementar
    }

    @Override
    public void update(Pedido pedido) {
        // TODO Pendiente implementar
    }

    @Override
    public void delete(Pedido pedido) {
        // TODO Pendiente implementar
    }
}
