package DAO;

import modelo.cliente.Cliente;
import modelo.cliente.ClienteEstandar;
import modelo.cliente.ClientePremium;
import modelo.enums.TipoCliente;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ClienteDAO implements IDao<Cliente> {
    // Connection conexion = null;

    @Override
    public Optional<Cliente> getById(String mail) {

        String sql = "SELECT * FROM cliente WHERE email = ?";

        try (Connection conexion = ConexionBD.getConexion()) {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("idCliente");
                String email = rs.getString("email");
                String nif = rs.getString("nif");
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                TipoCliente tipoCliente = TipoCliente.valueOf(rs.getString("tipoCliente"));
                Integer descuento = rs.getInt("descuento");
                Float cuotaAnual = rs.getFloat("cuotaAnual");

                if (tipoCliente == TipoCliente.PREMIUM) {
                    // Creamos un cliente de tipo Premium
                    ClientePremium clientePremium = new ClientePremium(
                            id, nombre, domicilio, nif, email, descuento, cuotaAnual);
                    return Optional.of(clientePremium);
                }
                if (tipoCliente == TipoCliente.ESTANDAR) {
                    // Creamos un cliente de tipo Estandar
                    ClienteEstandar clienteEstandar = new ClienteEstandar(
                            id, nombre, domicilio, nif, email);
                    return Optional.of(clienteEstandar);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al acceder a la BBDD con email: " + mail + " " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public ArrayList getAll() {

        String sql = "SELECT * FROM cliente";
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try (Connection conexion = ConexionBD.getConexion()) {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("idCliente");
                String email = rs.getString("email");
                String nif = rs.getString("nif");
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                TipoCliente tipoCliente = TipoCliente.valueOf(rs.getString("tipoCliente"));
                Integer descuento = rs.getInt("descuento");
                Float cuotaAnual = rs.getFloat("cuotaAnual");

                if (tipoCliente == TipoCliente.PREMIUM) {
                    // Creamos un cliente de tipo Premium
                    ClientePremium clientePremium = new ClientePremium(
                            id, nombre, domicilio, nif, email, descuento, cuotaAnual);
                    listaClientes.add(clientePremium);
                }
                if (tipoCliente == TipoCliente.ESTANDAR) {
                    // Creamos un cliente de tipo Estandar
                    ClienteEstandar clienteEstandar = new ClienteEstandar(
                            id, nombre, domicilio, nif, email);
                    listaClientes.add(clienteEstandar);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al acceder a la BBDD" + e.getMessage());
        }
        return listaClientes;
    }

    @Override
    public void save(Object o) {

        String sql = "INSERT INTO cliente (email, nif, nombre, domicilio, tipoCliente, descuento, cuotaAnual) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (o instanceof ClientePremium) {
            ClientePremium clientePremium = (ClientePremium) o;
            try (Connection conexion = ConexionBD.getConexion()) {
                conexion.setAutoCommit(false); // Deshabilitar autocommit en la BBDD

                try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                    stmt.setString(1, clientePremium.getEmail());
                    stmt.setString(2, clientePremium.getNif());
                    stmt.setString(3, clientePremium.getNombre());
                    stmt.setString(4, clientePremium.getDomicilio());
                    stmt.setString(5, clientePremium.getTipoCliente().toString());
                    stmt.setFloat(6, clientePremium.getDescuento());
                    stmt.setFloat(7, clientePremium.getCuotaAnual());
                    stmt.executeUpdate();

                    conexion.commit(); // Confirmar cambios en BBDD
                    conexion.setAutoCommit(true);

                } catch (SQLException e) {
                    conexion.rollback();
                    System.err.println("Aplicado rollback por error en BBDD, save cliente" + e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println("Error al acceder a la BBDD " + e.getMessage());
            }
        }
        if (o instanceof ClienteEstandar) {
            ClienteEstandar clienteEstandar = (ClienteEstandar) o;
            try (Connection conexion = ConexionBD.getConexion()) {
                conexion.setAutoCommit(false); // Deshabilitar autocommit en la BBDD

                try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                    stmt.setString(1, clienteEstandar.getEmail());
                    stmt.setString(2, clienteEstandar.getNif());
                    stmt.setString(3, clienteEstandar.getNombre());
                    stmt.setString(4, clienteEstandar.getDomicilio());
                    stmt.setString(5, clienteEstandar.getTipoCliente().toString());
                    stmt.setFloat(6, 0.00F);          // Descuento 0 por defecto
                    stmt.setFloat(7, 0.00F);    // CuotaAnual 0 por defecto
                    stmt.executeUpdate();

                    conexion.commit(); // Confirmar cambios en BBDD
                    conexion.setAutoCommit(true);

                } catch (SQLException e) {
                    conexion.rollback();
                    System.err.println("Aplicado rollback por error en BBDD, save cliente" + e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println("Error al acceder a la BBDD " + e.getMessage());
            }
        }
    }

    // TODO UPDATE Falta revisar e implementar con commit y rollback y teniendo en cuenta PREMIUM y ESTANDAR
    @Override
    public void update(Cliente cliente) {

        try (Connection conexion = ConexionBD.getConexion()) {

            String sql = "UPDATE Cliente" +
                             "SET nombre = ?, domicilio = ?, nif = ?, email = ?" +
                             "WHERE id_cliente = ?";
            PreparedStatement sqlOriginal = conexion.prepareStatement(sql);
            sqlOriginal.setString(1, cliente.getNombre());
            sqlOriginal.setString(2, cliente.getDomicilio());
            sqlOriginal.setString(3, cliente.getNif());
            sqlOriginal.setString(4, cliente.getEmail());
            sqlOriginal.setLong(5, cliente.getId());
            sqlOriginal.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al acceder a la BBDD " + e.getMessage());
        }
    }

    // TODO DELETE Falta revisar e implementar con commit y rollback
    @Override
    public void delete(Cliente cliente) {

        try (Connection conexion = ConexionBD.getConexion()) {

                String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
                PreparedStatement sqlOriginal = conexion.prepareStatement(sql);
                sqlOriginal.setLong(1, cliente.getId());

        }
        catch (Exception e) {
            System.err.println("Error al acceder a la BBDD " + e.getMessage());
        }
    }
}
