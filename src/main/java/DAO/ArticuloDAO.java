package DAO;

import modelo.Articulo;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticuloDAO implements IDao<Articulo> {

    @Override
    public Optional<Articulo> getById(String codigo) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "SELECT * FROM articulo WHERE codigoArticulo = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Articulo articulo = new Articulo(
                        rs.getString("codigoArticulo"),
                        rs.getString("descripcion"),
                        rs.getFloat("precioVenta"),
                        rs.getFloat("gastosEnvio"),
                        rs.getInt("tiempoPreparacion")
                );
                return Optional.of(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Articulo> getAll() {
        List<Articulo> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "SELECT * FROM articulo";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Articulo articulo = new Articulo(
                        rs.getString("codigoArticulo"),
                        rs.getString("descripcion"),
                        rs.getFloat("precioVenta"),
                        rs.getFloat("gastosEnvio"),
                        rs.getInt("tiempoPreparacion")
                );
                lista.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Object o) {

        if (o instanceof Articulo) {
            Articulo articulo = (Articulo) o;

            try (Connection conexion = ConexionBD.getConexion()) {
                String sql = "INSERT INTO articulo (codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, articulo.getCodigoArticulo());
                stmt.setString(2, articulo.getDescripcion());
                stmt.setFloat(3, articulo.getPrecioVenta());
                stmt.setFloat(4, articulo.getGastosEnvio());
                stmt.setInt(5, articulo.getTiempoPreparacion());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Articulo articulo) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "UPDATE articulo SET descripcion = ?, precioVenta = ?, gastosEnvio = ?, tiempoPreparacion = ? WHERE codigoArticulo = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, articulo.getDescripcion());
            stmt.setFloat(2, articulo.getPrecioVenta());
            stmt.setFloat(3, articulo.getGastosEnvio());
            stmt.setInt(4, articulo.getTiempoPreparacion());
            stmt.setString(5, articulo.getCodigoArticulo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Articulo articulo) {
        try (Connection conexion = ConexionBD.getConexion()) {
            String sql = "DELETE FROM articulo WHERE codigoArticulo = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, articulo.getCodigoArticulo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
