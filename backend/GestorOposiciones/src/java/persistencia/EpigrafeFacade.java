/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import dominio.Epigrafe;
import dominio.Oposicion;
import dominio.RelDepEpi;
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
public class EpigrafeFacade extends AbstractFacade<Epigrafe> implements EpigrafeFacadeLocal {
    @PersistenceContext(unitName = "GestorOposicionesPU")
    private EntityManager em;
    
    
    @Override
    public List<Epigrafe> findAll(){
        javax.persistence.Query q = getEntityManager().createQuery("SELECT o FROM Epigrafe o ORDER BY o.nombre ASC");
        return  q.getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EpigrafeFacade() {
        super(Epigrafe.class);
    }
    @Override
    public List<Epigrafe> findRange(int array[]){
        javax.persistence.Query q = getEntityManager().createQuery("SELECT o FROM Epigrafe o ORDER BY o.nombre ASC");
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList();
    }


     public List<RelDepEpi> findDep(String nombreEp, int[] array){
        javax.persistence.Query q = getEntityManager().createQuery("SELECT r FROM RelDepEpi r WHERE r.relDepEpiPK.nombreep = :nombreep  ORDER BY r.departamento.nombre ASC, r.relDepEpiPK.nombreep ASC").setParameter("nombreep",nombreEp);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
     
     @Override
    public List<Epigrafe> findEpigrafeBusqueda(String busqueda, int[]array){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT e FROM Epigrafe e  WHERE LOWER(e.nombre)  LIKE LOWER(CONCAT('%',:busqueda,'%')) ORDER BY e.nombre ASC").setParameter("busqueda",busqueda);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
}
