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
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface EpigrafeFacadeLocal {

    void create(Epigrafe epigrafe);

    void edit(Epigrafe epigrafe);

    void remove(Epigrafe epigrafe);

    Epigrafe find(Object id);

    List<Epigrafe> findAll();

    List<Epigrafe> findRange(int[] range);

    int count();


    List<RelDepEpi> findDep(String id,int array[]);

    
}
