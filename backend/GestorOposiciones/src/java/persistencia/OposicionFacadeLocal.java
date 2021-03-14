/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Oposicion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface OposicionFacadeLocal {

    void create(Oposicion oposicion);

    void edit(Oposicion oposicion);

    void remove(Oposicion oposicion);

    Oposicion find(Object id);

    List<Oposicion> findAll();

    List<Oposicion> findRange(int[] range);

    int count();
    List<Oposicion> findOposicion(String etqDep, String nombreEp);

}
