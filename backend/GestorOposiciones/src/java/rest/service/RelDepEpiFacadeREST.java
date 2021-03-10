/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.service;

import dominio.RelDepEpi;
import dominio.RelDepEpiPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author javie
 */
@Stateless
@Path("dominio.reldepepi")
public class RelDepEpiFacadeREST extends AbstractFacade<RelDepEpi> {
    @PersistenceContext(unitName = "GestorOposicionesPU")
    private EntityManager em;

    private RelDepEpiPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;nombreep=nombreepValue;etqdep=etqdepValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        dominio.RelDepEpiPK key = new dominio.RelDepEpiPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> nombreep = map.get("nombreep");
        if (nombreep != null && !nombreep.isEmpty()) {
            key.setNombreep(nombreep.get(0));
        }
        java.util.List<String> etqdep = map.get("etqdep");
        if (etqdep != null && !etqdep.isEmpty()) {
            key.setEtqdep(etqdep.get(0));
        }
        return key;
    }

    public RelDepEpiFacadeREST() {
        super(RelDepEpi.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(RelDepEpi entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, RelDepEpi entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        dominio.RelDepEpiPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public RelDepEpi find(@PathParam("id") PathSegment id) {
        dominio.RelDepEpiPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<RelDepEpi> findAll() {
        return super.findAll();
    }
    /*
    @GET
    @Path("Departamento/{id}")
    @Produces({"application/xml", "application/json"})
    public List<RelDepEpi> findAll() {
        return super.findAll();
    }*/

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<RelDepEpi> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
