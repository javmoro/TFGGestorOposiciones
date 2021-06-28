/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Oposicion;
import java.sql.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface OposicionFacadeLocal {
    List<Oposicion> findTitulo(String busqueda);
    void create(Oposicion oposicion);

    void edit(Oposicion oposicion);

    void remove(Oposicion oposicion);

    Oposicion find(Object id);

    List<Oposicion> findAll();

    List<Oposicion> findRange(int[] range,Date fecha);

    int count();
    List<Oposicion> findOposicion(String etqDep, String nombreEp,int array[],Date fecha);
    List<Oposicion> findOposicionBusqueda(String busqueda,int array[]);
    List<Oposicion> findOposicionFechasId(String busqueda,Date fecha1,Date fecha2,int array[]);
    List<Oposicion> findOposicionFechaId(String busqueda,Date fecha,int array[]);
    List<Oposicion> findOposicionFechas(Date fecha1,Date fecha2,int array[]);
    List<Oposicion> findOposicionAvanzada(String estado, Date fecha1, Date fecha2, String departamento, String epigrafe, String busqueda);
    List<Oposicion> findOposicionAvanzadaPage(String estado, Date fecha1, Date fecha2, String departamento, String epigrafe, String busqueda, int array[]);

}
