/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Oposicion;
import java.util.List;
import java.sql.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public List<Oposicion> findAll(){
        javax.persistence.Query q = getEntityManager().createQuery("SELECT o FROM Oposicion o ORDER BY o.fecha DESC");
        return  q.getResultList();
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
    public List<Oposicion> findTitulo(String busqueda){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o  WHERE LOWER(o.titulo)  LIKE LOWER(CONCAT('%',:busqueda,'%')) ORDER BY o.fecha DESC ").setParameter("busqueda",busqueda);

        return  q.getResultList(); 
    }
    
    @Override
    public List<Oposicion> findOposicionBusqueda(String busqueda, int[]array){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o  WHERE LOWER(o.relDepEpi.epigrafe.nombre)  LIKE LOWER(CONCAT('%',:busqueda,'%')) OR LOWER(o.relDepEpi.departamento.nombre) LIKE LOWER(CONCAT('%',:busqueda,'%')) ORDER BY o.fecha DESC ").setParameter("busqueda",busqueda);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    @Override
    public List<Oposicion> findOposicionFechas(Date fecha1,Date fecha2,int array[]){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o  WHERE o.fecha BETWEEN :fecha1 AND :fecha2 ORDER BY o.fecha DESC ").setParameter("fecha1",fecha1).setParameter("fecha2",fecha2);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
    @Override
    public List<Oposicion> findOposicionFechasId(String busqueda,Date fecha1,Date fecha2,int array[]){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o  WHERE (LOWER(o.relDepEpi.relDepEpiPK.nombreep)  LIKE LOWER(CONCAT('%',:busqueda,'%')) or LOWER(o.relDepEpi.departamento.nombre) LIKE LOWER(CONCAT('%',:busqueda,'%'))) AND o.fecha BETWEEN :fecha1 AND :fecha2 ORDER BY o.fecha DESC ").setParameter("busqueda",busqueda).setParameter("fecha1",fecha1).setParameter("fecha2",fecha2);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
    @Override
    public List<Oposicion> findOposicionFechaId(String busqueda,Date fecha,int array[]){
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT o FROM Oposicion o   WHERE (LOWER(o.relDepEpi.relDepEpiPK.nombreep)  LIKE LOWER(CONCAT('%',:busqueda,'%')) or LOWER(o.relDepEpi.departamento.nombre) LIKE LOWER(CONCAT('%',:busqueda,'%'))) AND o.fecha = :fecha ORDER BY o.fecha DESC ").setParameter("busqueda",busqueda).setParameter("fecha",fecha);
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    @Override
    public List<Oposicion> findOposicionAvanzada(String estado, Date fecha1, Date fecha2, String departamento, String epigrafe, String busqueda){
        String entrada ="SELECT o FROM Oposicion o   ";
        boolean temp =false;
        if((estado!=null&&!estado.equals(""))|fecha1!=null||(departamento!=null&&!departamento.equals(""))||(epigrafe!=null&&!epigrafe.equals(""))||(busqueda!=null&&!busqueda.equals(""))){
            entrada += " WHERE ";
        }
        if(estado!=null&&!estado.equals("")){
            entrada += " o.estado = \""+estado+"\" ";
            temp =true;
        }
        if(departamento!=null&&!departamento.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " (LOWER(o.relDepEpi.departamento.nombre) LIKE LOWER(CONCAT('%','"+departamento+"','%')))";
            temp =true;
        }
        if(epigrafe!=null&&!epigrafe.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " LOWER(o.relDepEpi.epigrafe.nombre) LIKE LOWER(CONCAT('%','"+epigrafe+"','%'))";
            temp =true;
        }
        if(busqueda!=null&&!busqueda.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " LOWER(o.titulo)  LIKE LOWER(CONCAT('%','"+busqueda+"','%'))";
            temp =true;
        }

        
        if(fecha1!=null){
            if(temp){
                entrada += " AND ";
            }
            if(fecha2!=null){
                entrada += " o.fecha BETWEEN \'"+fecha1+"\' AND \'"+fecha2+"\'";
            }else{
                entrada += " o.fecha = \'"+fecha1+"\'";
            }
            
            temp =true;
        }
        entrada+= " ORDER BY o.fecha DESC ";
        javax.persistence.Query q;
        System.out.println(entrada);
        q = getEntityManager().createQuery(entrada);
        return  q.getResultList(); 
    }
    @Override
    public List<Oposicion> findOposicionAvanzadaPage(String estado, Date fecha1, Date fecha2, String departamento, String epigrafe, String busqueda, int array[]){
        String entrada ="SELECT o FROM Oposicion o   ";
        boolean temp =false;
        if((estado!=null&&!estado.equals(""))|fecha1!=null||fecha2!=null||(departamento!=null&&!departamento.equals(""))||(epigrafe!=null&&!epigrafe.equals(""))||(busqueda!=null&&!busqueda.equals(""))){
            entrada += " WHERE ";
        }
        if(estado!=null&&!estado.equals("")){
            entrada += " o.estado = \""+estado+"\" ";
            temp =true;
        }
        if(departamento!=null&&!departamento.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " (LOWER(o.relDepEpi.departamento.nombre) LIKE LOWER(CONCAT('%','"+departamento+"','%')))";
            temp =true;
        }
        if(epigrafe!=null&&!epigrafe.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " LOWER(o.relDepEpi.epigrafe.nombre) LIKE LOWER(CONCAT('%','"+epigrafe+"','%'))";
            temp =true;
        }
        if(busqueda!=null&&!busqueda.equals("")){
            if(temp){
                entrada += " AND ";
            }
            entrada += " LOWER(o.titulo)  LIKE LOWER(CONCAT('%','"+busqueda+"','%'))";
            temp =true;
        }

        
        if(fecha1!=null){
            if(temp){
                entrada += " AND ";
            }
            if(fecha2!=null){
                entrada += " o.fecha BETWEEN \'"+fecha1+"\' AND \'"+fecha2+"\'";
            }else{
                entrada += " o.fecha = \'"+fecha1+"\'";
            }
            
            temp =true;
        }else{
           
            if(fecha2!=null){
                 if(temp){
                    entrada += " AND ";
                }
                entrada += " o.fecha <= \'"+fecha2+"\'";
            }
        }
        entrada+= " ORDER BY o.fecha DESC ";
        javax.persistence.Query q;
        System.out.println(entrada);
        q = getEntityManager().createQuery(entrada);
        
        q.setMaxResults(array[1] - array[0] + 1);
        q.setFirstResult(array[0]);
        return  q.getResultList(); 
    }
    
    
}
