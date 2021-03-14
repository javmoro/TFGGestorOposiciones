/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Departamento;
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
public class DepartamentoFacade extends AbstractFacade<Departamento> implements DepartamentoFacadeLocal {
    @PersistenceContext(unitName = "GestorOposicionesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }
      public List<RelDepEpi> findEpi(String etqDep){
        return  getEntityManager().createQuery("SELECT r FROM RelDepEpi r WHERE r.relDepEpiPK.etqdep = :etqdep").setParameter("etqdep",etqDep).getResultList(); 
    }

    
}
