package pe.edu.upeu.proyecto_sql.test;

import pe.edu.upeu.proyecto_sql.implementacion.ClienteDaoImpl;
import pe.edu.upeu.proyecto_sql.interfaces.IClienteDao;
import pe.edu.upeu.proyecto_sql.modelo.Cliente;

import java.util.List;

public class TestCliente {
    static IClienteDao cl = new ClienteDaoImpl();

    public static void listar(){
        List<Cliente> lista = cl.listAll();
        if (lista != null && !lista.isEmpty()){
            for (Cliente cliente : lista){
                System.out.println("ID_CLIENTE: "+cliente.getIdcliente());
                System.out.println("NOMBRE: "+cliente.getNombre());
                System.out.println("APELLIDOS: "+cliente.getApellido());
                System.out.println("DNI: "+cliente.getDni());
                System.out.println("EMAIL: "+cliente.getEmail());
                System.out.println("GENERO: "+cliente.getGenero());
            }
        } else {
            System.out.println("No hay registros");
        }
    }

    public static void eliminar() {
        Cliente c = new Cliente();
        c.setIdcliente(2);
        boolean result = cl.delete(c);
        if (result){
            System.out.println("delete sucess");
        } else {
            System.out.println("delete error");
        }
    }

    public static void main(String[] args) {
        listar();
    }
}
