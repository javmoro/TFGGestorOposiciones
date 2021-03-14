/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dominio.Epigrafe;
import dominio.Oposicion;
import dominio.RelDepEpi;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import persistencia.EpigrafeFacadeLocal;
import persistencia.OposicionFacadeLocal;

/**
 * REST Web Service
 *
 * @author javie
 */
@Path("epigrafe")
public class EpigrafeResource {
    OposicionFacadeLocal oposicionFacade = lookupOposicionFacadeLocal();
    EpigrafeFacadeLocal epigrafeFacade = lookupEpigrafeFacadeLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EpigrafeResource
     */
    public EpigrafeResource() {
    }
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Epigrafe> findAll() {
        return epigrafeFacade.findAll();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Epigrafe find(@PathParam("id") String id) {
        return epigrafeFacade.find(id);
    }
    
    @GET
    @Path("{id}/epigrafes")
    @Produces({"application/xml", "application/json"})
    public List<RelDepEpi> findEpigrafe(@PathParam("id") String id) {
        return epigrafeFacade.findDep(id);
    }
    
    @GET
    @Path("{id}/epigrafes/{nombreEp}/oposiciones")
    @Produces({"application/xml", "application/json"})
    public List<Oposicion> findEpigrafeOposicion(@PathParam("id") String id,@PathParam("nombreEp") String nombreEp) {
        return oposicionFacade.findOposicion(id,nombreEp);
    }
    
    private EpigrafeFacadeLocal lookupEpigrafeFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (EpigrafeFacadeLocal) c.lookup("java:global/GestorOposiciones/EpigrafeFacade!persistencia.EpigrafeFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OposicionFacadeLocal lookupOposicionFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OposicionFacadeLocal) c.lookup("java:global/GestorOposiciones/OposicionFacade!persistencia.OposicionFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
