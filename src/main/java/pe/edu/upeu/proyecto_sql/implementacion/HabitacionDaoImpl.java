package pe.edu.upeu.proyecto_sql.implementacion;

import pe.edu.upeu.proyecto_sql.interfaces.IHabitacionDao;
import pe.edu.upeu.proyecto_sql.modelo.Habitacion;
import pe.edu.upeu.proyecto_sql.utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDaoImpl implements IHabitacionDao {
    @Override
    public boolean insert(Habitacion e) {
        boolean flag = false;
        String query = "INSERT INTO habitacion(numero, numeroPiso, numeroHabitacion, tipo, precio, estado) VALUES(?,?,?,?,?,?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, e.getNumero());
            st.setString(2, e.getNumeroPiso());
            st.setString(3, e.getNumeroHabitacion());
            st.setString(4, e.getTipo());
            st.setDouble(5, e.getPrecio());
            st.setString(6, e.getEstado());
            flag = st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al insertar habitacion: " + ex.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Habitacion e) {
        String query = "UPDATE habitacion SET numero=?, numeroPiso=?, numeroHabitacion=?, tipo=?, precio=?, estado=? WHERE idhabitacion=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, e.getNumero());
            st.setString(2, e.getNumeroPiso());
            st.setString(3, e.getNumeroHabitacion());
            st.setString(4, e.getTipo());
            st.setDouble(5, e.getPrecio());
            st.setString(6, e.getEstado());
            st.setInt(7, e.getIdhabitacion());
            return st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar habitacion: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Habitacion e) {
        boolean flag = false;
        String query = "DELETE FROM habitacion WHERE idhabitacion=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, e.getIdhabitacion());
            flag = st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar habitacion: " + ex.getMessage());
        }
        return flag;
    }

    @Override
    public List<Habitacion> listAll() {
        List<Habitacion> lista = new ArrayList<>();
        String query = "SELECT * FROM habitacion ORDER BY idhabitacion";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Habitacion ha = new Habitacion();
                ha.setIdhabitacion(rs.getInt("idhabitacion"));
                ha.setNumero(rs.getString("numero"));
                ha.setNumeroPiso(rs.getString("numeroPiso"));
                ha.setNumeroHabitacion(rs.getString("numeroHabitacion"));
                ha.setTipo(rs.getString("tipo"));
                ha.setPrecio(rs.getDouble("precio"));
                ha.setEstado(rs.getString("estado"));
                lista.add(ha);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar habitacion: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Habitacion searchById(int id) {
        String query = "SELECT * FROM habitacion WHERE idhabitacion=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Habitacion ha = new Habitacion();
                    ha.setIdhabitacion(rs.getInt("idhabitacion"));
                    ha.setNumero(rs.getString("numero"));
                    ha.setNumeroPiso(rs.getString("numeroPiso"));
                    ha.setNumeroHabitacion(rs.getString("numeroHabitacion"));
                    ha.setTipo(rs.getString("tipo"));
                    ha.setPrecio(rs.getDouble("precio"));
                    ha.setEstado(rs.getString("estado"));
                    return ha;
                }
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar habitacion: " + ex.getMessage());
            return null;
        }
    }

}
