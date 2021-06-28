/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.ReferenciaAnterior;
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
public class ReferenciaAnteriorFacade extends AbstractFacade<ReferenciaAnterior> implements ReferenciaAnteriorFacadeLocal {
    @PersistenceContext(unitName = "GestorOposicionesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @Override
                
    public List<ReferenciaAnterior> findReferenciaAnterior(String id){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM ReferenciaAnterior o WHERE o.referenciaAnteriorPK.idRefAnterior =:ID_REF_ANTERIOR").setParameter("ID_REF_ANTERIOR",id);
        
        return  q.getResultList(); 
        
    }   
    @Override
    public List<ReferenciaAnterior> findReferenciaPosterior(String id){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM ReferenciaAnterior o WHERE o.referenciaAnteriorPK.idRefPosterior =:ID_REF_POSTERIOR").setParameter("ID_REF_POSTERIOR",id);
        
        return  q.getResultList(); 
        
    }   

    public ReferenciaAnteriorFacade() {
        super(ReferenciaAnterior.class);
    }
    
}
