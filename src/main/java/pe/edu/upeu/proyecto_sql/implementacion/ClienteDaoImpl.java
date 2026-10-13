package pe.edu.upeu.proyecto_sql.implementacion;
import pe.edu.upeu.proyecto_sql.interfaces.IClienteDao;
import pe.edu.upeu.proyecto_sql.modelo.Cliente;
import pe.edu.upeu.proyecto_sql.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements IClienteDao {
    //Variable Global De Conexion
    private static Connection cn;


    @Override
    public boolean delete(Cliente e) {
        boolean flag = false;

        String query = "DELETE FROM clientes WHERE idcliente=?";

        try (
                Connection cn = ConexionDB.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {

            st.setInt(1, e.getIdcliente());
            flag = st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
        }

        return flag;
    }

    @Override
    public boolean insert(Cliente e) {
        boolean flag = false;

        String query = "INSERT INTO clientes(nombre, apellidos, dni , email, genero)VALUES(?,?,?,?,?)";

        try (
                Connection cn = ConexionDB.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setString(1, e.getNombre());
            st.setString(2, e.getApellido());
            st.setString(3, e.getDni());
            st.setString(4, e.getEmail());
            st.setString(5, e.getGenero());

            flag = st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al insertar cliente: " + ex.getMessage());
        }

        return flag;
    }

    @Override
    public List<Cliente> listAll() {
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT * FROM clientes;";
        try(
                Connection cn = ConexionDB.getConnection();
                PreparedStatement st = cn.prepareStatement(query);
                ResultSet rs = st.executeQuery();
        ){
            while (rs.next()){
                Cliente cl = new Cliente();
                cl.setIdcliente(rs.getInt("idcliente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellidos"));
                cl.setDni(rs.getString("dni"));
                cl.setEmail(rs.getString("email"));
                cl.setGenero(rs.getString("genero"));
                lista.add(cl);
            }
        }catch (SQLException e){
            System.out.println("Error al listar cliente: "+ e.getMessage());
        }
        return lista;
    }

    @Override
    public Cliente searchByDni(String dni) {
        return buscarCliente("SELECT * FROM clientes WHERE dni=?", dni);
    }

    @Override
    public boolean update(Cliente e) {
        String query = "UPDATE clientes SET nombre=?, apellidos=?, dni=?, email=?, genero=? WHERE idcliente=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, e.getNombre());
            st.setString(2, e.getApellido());
            st.setString(3, e.getDni());
            st.setString(4, e.getEmail());
            st.setString(5, e.getGenero());
            st.setInt(6, e.getIdcliente());
            return st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar cliente: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Cliente searchById(int id) {
        Cliente cl = null;
        String query = "SELECT * FROM clientes WHERE idcliente=?;";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    cl = new Cliente();
                    cl.setIdcliente(rs.getInt("idcliente"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setApellido(rs.getString("apellidos"));
                    cl.setDni(rs.getString("dni"));
                    cl.setEmail(rs.getString("email"));
                    cl.setGenero(rs.getString("genero"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cliente por Id:" + e.getMessage());
        }
        return cl;
    }

    private Cliente buscarCliente(String query, Object valor) {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setObject(1, valor);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Cliente cl = new Cliente();
                    cl.setIdcliente(rs.getInt("idcliente"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setApellido(rs.getString("apellidos"));
                    cl.setDni(rs.getString("dni"));
                    cl.setEmail(rs.getString("email"));
                    cl.setGenero(rs.getString("genero"));
                    return cl;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar cliente: " + ex.getMessage());
        }
        return null;
    }
}
