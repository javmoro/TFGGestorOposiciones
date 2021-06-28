/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.RelDepEpi;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface RelDepEpiFacadeLocal {

    void create(RelDepEpi relDepEpi);

    void edit(RelDepEpi relDepEpi);

    void remove(RelDepEpi relDepEpi);

    RelDepEpi find(Object id);

    List<RelDepEpi> findAll();

    List<RelDepEpi> findRange(int[] range);

    int count();
    
}
