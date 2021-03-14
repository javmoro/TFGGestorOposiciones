/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dominio.Oposicion;
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
import persistencia.OposicionFacadeLocal;

/**
 * REST Web Service
 *
 * @author javie
 */
@Path("oposiciones")
public class OposicionResource {
    OposicionFacadeLocal oposicionFacade = lookupOposicionFacadeLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OposicionResource
     */
    public OposicionResource() {
    }
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Oposicion> findAll() {
        return oposicionFacade.findAll();
    }
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Oposicion find(@PathParam("id") String id) {
        return oposicionFacade.find(id);
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
