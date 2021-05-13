/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Oposicion;
import java.util.List;
import java.sql.Date;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistencia.AbstractFacade;

/**
 *
 * @author javie
 */
@Stateless
public class OposicionFacade extends AbstractFacade<Oposicion> implements OposicionFacadeLocal {
    @PersistenceContext(unitName = "GestorOposicionesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OposicionFacade() {
        super(Oposicion.class);
    }
    
    @Override
    public List<Oposicion> findRange(int array[],Date fecha){
        javax.persistence.Query q;
        if(fecha==null){
            q = getEntityManager().createQuery("SELECT o FROM Oposicion o ORDER BY o.fecha DESC");
        }else{
            q = getEntityManager().createQuery("SELECT o FROM Oposicion o WHERE o.fecha = :fecha ORDER BY o.fecha DESC ").setParameter("fecha",fecha);
        }
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList();
    }
    @Override
    public List<Oposicion> findOposicion(String etqDep, String nombreEp,int[]array,Date fecha) {
        javax.persistence.Query q;
        if(fecha==null){
            q = getEntityManager().createQuery("SELECT o FROM Oposicion o WHERE o.relDepEpi.relDepEpiPK.etqdep = :etqdep AND o.relDepEpi.relDepEpiPK.nombreep = :nombreEp ORDER BY o.fecha DESC ").setParameter("etqdep",etqDep).setParameter("nombreEp",nombreEp);
         }else{
            q = getEntityManager().createQuery("SELECT o FROM Oposicion o WHERE o.fecha = :fecha AND o.relDepEpi.relDepEpiPK.etqdep = :etqdep AND o.relDepEpi.relDepEpiPK.nombreep = :nombreEp ORDER BY o.fecha DESC ").setParameter("etqdep",etqDep).setParameter("nombreEp",nombreEp).setParameter("fecha", fecha);
        }
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    @Override
    public List<Oposicion> findOposicionBusqueda(String busqueda, int[]array){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o  WHERE LOWER(o.relDepEpi.relDepEpiPK.nombreep)  LIKE LOWER(CONCAT('%',:busqueda,'%')) or LOWER(o.relDepEpi.departamento.nombre) LIKE CONCAT('%',:busqueda,'%') ORDER BY o.fecha DESC ").setParameter("busqueda",busqueda);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
}
