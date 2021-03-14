package persistencia;

import dominio.Departamento;
import dominio.Oposicion;
import dominio.RelDepEpi;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface DepartamentoFacadeLocal {

    void create(Departamento departamento);

    void edit(Departamento departamento);

    void remove(Departamento departamento);

    Departamento find(Object id);

    List<Departamento> findAll();

    List<Departamento> findRange(int[] range);
    List<RelDepEpi> findEpi(String nombre);
    int count();
    
}
