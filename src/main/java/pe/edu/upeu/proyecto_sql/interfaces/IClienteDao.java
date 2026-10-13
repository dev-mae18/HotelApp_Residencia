package pe.edu.upeu.proyecto_sql.interfaces;

import pe.edu.upeu.proyecto_sql.modelo.Cliente;

import java.util.List;

public interface IClienteDao {
    public boolean insert(Cliente e);
    public boolean update(Cliente e);
    public Cliente searchById(int id);
    public boolean delete(Cliente e);
    public List<Cliente> listAll();

    public Cliente searchByDni(String dni);

}
