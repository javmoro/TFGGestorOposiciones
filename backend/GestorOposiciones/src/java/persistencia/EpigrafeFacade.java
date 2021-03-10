/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Epigrafe;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
