package pe.edu.upeu.proyecto_sql.interfaces;

import pe.edu.upeu.proyecto_sql.modelo.Habitacion;
import java.util.List;

public interface IHabitacionDao {
    public boolean insert(Habitacion e);
    public boolean update(Habitacion e);
    public boolean delete(Habitacion e);
    public List<Habitacion> listAll();
    public Habitacion searchById(int id);
}
