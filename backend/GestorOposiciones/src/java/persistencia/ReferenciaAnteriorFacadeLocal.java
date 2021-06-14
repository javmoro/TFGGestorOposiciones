/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.ReferenciaAnterior;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author javie
 */
@Local
public interface ReferenciaAnteriorFacadeLocal {

    void create(ReferenciaAnterior referenciaAnterior);

    void edit(ReferenciaAnterior referenciaAnterior);

    void remove(ReferenciaAnterior referenciaAnterior);

    ReferenciaAnterior find(Object id);
    List<ReferenciaAnterior> findReferenciaAnterior(String id);   
    List<ReferenciaAnterior> findReferenciaPosterior(String id);
    List<ReferenciaAnterior> findAll();

    List<ReferenciaAnterior> findRange(int[] range);

    int count();
    
}
