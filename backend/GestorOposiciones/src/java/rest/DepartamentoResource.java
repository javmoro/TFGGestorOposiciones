/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dominio.Departamento;
import dominio.Oposicion;
import dominio.RelDepEpi;
import java.sql.Date;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import persistencia.DepartamentoFacadeLocal;
import persistencia.OposicionFacadeLocal;
import persistencia.RelDepEpiFacadeLocal;

/**
 * REST Web Service
 *
 * @author javie
 */
@Path("departamento")
public class DepartamentoResource implements ContainerResponseFilter{
    OposicionFacadeLocal oposicionFacade = lookupOposicionFacadeLocal();
    int numValores = 20;
    DepartamentoFacadeLocal departamentoFacade = lookupDepartamentoFacadeLocal();

    @Context
    private UriInfo context;
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
    /**
     * Creates a new instance of GenericResource
     */
    public DepartamentoResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     * @return an instance of java.lang.String
     */
    /*
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Departamento> findAll() {
        return departamentoFacade.findAll();
    }
    */
    @GET
    @Produces({"application/xml", "application/json"})
    public Departamento[] findAll(@QueryParam("page") int page) {
        if(page==0){
            return departamentoFacade.findAll().toArray(new Departamento[0]);
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return departamentoFacade.findRange(array).toArray(new Departamento[0]);
        
        
    }
     
    @GET
    @Path("{id}")
    @Produces( "application/json")
    public Departamento find(@PathParam("id") String id) {
        return departamentoFacade.find(id);
    }
    
    @GET
    @Path("{id}/epigrafes")
    @Produces( "application/json")
    public List<RelDepEpi> findEpigrafe(@PathParam("id") String id,@QueryParam("page") int page) {
        
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return departamentoFacade.findEpi(id,array);
    }
    //?page=3
    @GET
    @Path("{id}/epigrafes/{nombreEp}/oposiciones")
    @Produces( "application/json")
    public List<Oposicion> findEpigrafeOposicion(@PathParam("id") String id,@PathParam("nombreEp") String nombreEp,@QueryParam("fecha") Date fecha,@QueryParam("page") int page) {
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findOposicion(id,nombreEp,array,fecha);
    }


    @GET
    @Path("search/{busqueda}")
    @Produces( "application/json")
    public Departamento[] findBusqueda(@PathParam("busqueda") String busqueda,@QueryParam("page") int page) {
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return departamentoFacade.findDepartamentoBusqueda(busqueda,array).toArray(new Departamento[0]);
        //return Response.status(Response.Status.OK).entity(oposicionFacade.findRange(array).toArray(new Oposicion[0])).status(oposicionFacade.count())
           //         .build();
    }
    
    

    private DepartamentoFacadeLocal lookupDepartamentoFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DepartamentoFacadeLocal) c.lookup("java:global/GestorOposiciones/DepartamentoFacade!persistencia.DepartamentoFacadeLocal");
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
