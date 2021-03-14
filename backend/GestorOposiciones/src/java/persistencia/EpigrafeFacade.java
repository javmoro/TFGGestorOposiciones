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
    protected EntityManager getEntityManager() {
        return em;
    }

    public EpigrafeFacade() {
        super(Epigrafe.class);
    }



     public List<RelDepEpi> findDep(String nombreEp){
        return  getEntityManager().createQuery("SELECT r FROM RelDepEpi r WHERE r.relDepEpiPK.nombreep = :nombreep").setParameter("nombreep",nombreEp).getResultList(); 
    }
    
}
