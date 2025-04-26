package DAO;

import modelo.Articulo;
import modelo.Pedido;
import modelo.cliente.Cliente;
import modelo.enums.TipoEstado;
import util.ConexionBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoDAO implements IDao<Pedido> {

    private final IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
    private final IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");

    @Override
    public Optional<Pedido> getById(String id) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "SELECT * FROM pedido WHERE numeroPedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Articulo articulo = articuloDAO.getById(rs.getString("codigoArticulo")).orElse(null);
                Cliente cliente = (Cliente) clienteDAO.getById(rs.getString("emailCliente")).orElse(null);

                if (articulo != null && cliente != null) {
                    Pedido pedido = new Pedido(
                            rs.getInt("numeroPedido"),
                            articulo,
                            rs.getInt("cantidadArticulos"),
                            cliente,
                            rs.getTimestamp("fechaPedido").toLocalDateTime(),
                            TipoEstado.valueOf(rs.getString("estado"))
                    );
                    return Optional.of(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Pedido> getAll() {
        List<Pedido> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "SELECT * FROM pedido";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Articulo articulo = articuloDAO.getById(rs.getString("codigoArticulo")).orElse(null);
                Cliente cliente = (Cliente) clienteDAO.getById(rs.getString("emailCliente")).orElse(null);

                if (articulo != null && cliente != null) {
                    Pedido pedido = new Pedido(
                            rs.getInt("numeroPedido"),
                            articulo,
                            rs.getInt("cantidadArticulos"),
                            cliente,
                            rs.getTimestamp("fechaPedido").toLocalDateTime(),
                            TipoEstado.valueOf(rs.getString("estado"))
                    );
                    lista.add(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Object o) {

        if (o instanceof Pedido) {
            Pedido pedido = (Pedido) o;

            try (Connection conexion = ConexionBD.getConexion()) {
                String sql = "INSERT INTO pedido (codigoArticulo, emailCliente, cantidadArticulos, precioTotal, fechaPedido, estado) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, pedido.getArticulo().getCodigoArticulo());
                stmt.setString(2, pedido.getCliente().getEmail());
                stmt.setInt(3, pedido.getCantidadArticulos());
                stmt.setDouble(4, pedido.getPrecioTotal());
                stmt.setTimestamp(5, Timestamp.valueOf(pedido.getFechaPedido()));
                stmt.setString(6, pedido.getEstado().name());
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Pedido pedido) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "UPDATE pedido SET codigoArticulo = ?, emailCliente = ?, cantidadArticulos = ?, precioTotal = ?, fechaPedido = ?, estado = ? " +
                    "WHERE numeroPedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, pedido.getArticulo().getCodigoArticulo());
            stmt.setString(2, pedido.getCliente().getEmail());
            stmt.setInt(3, pedido.getCantidadArticulos());
            stmt.setDouble(4, pedido.getPrecioTotal());
            stmt.setTimestamp(5, Timestamp.valueOf(pedido.getFechaPedido()));
            stmt.setString(6, pedido.getEstado().name());
            stmt.setInt(7, pedido.getNumeroPedido());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Pedido pedido) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "DELETE FROM pedido WHERE numeroPedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, pedido.getNumeroPedido());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
