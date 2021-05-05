/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Oposicion;
import java.util.List;
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
    public List<Oposicion> findRange(int array[]){
        javax.persistence.Query q = getEntityManager().createQuery("SELECT o FROM Oposicion o ORDER BY o.fecha DESC");
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList();
    }
    @Override
    public List<Oposicion> findOposicion(String etqDep, String nombreEp,int[]array) {
        javax.persistence.Query q = getEntityManager().createQuery("SELECT o FROM Oposicion o ORDER BY o.fecha DESC WHERE o.relDepEpi.relDepEpiPK.etqdep = :etqdep AND o.relDepEpi.relDepEpiPK.nombreep = :nombreEp").setParameter("etqdep",etqDep).setParameter("nombreEp",nombreEp);

        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
}
