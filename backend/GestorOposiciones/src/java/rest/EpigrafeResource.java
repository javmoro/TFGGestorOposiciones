/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dominio.Departamento;
import dominio.Epigrafe;
import dominio.Oposicion;
import dominio.RelDepEpi;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import persistencia.EpigrafeFacadeLocal;
import persistencia.OposicionFacadeLocal;

/**
 * REST Web Service
 *
 * @author javie
 */
@Path("epigrafe")
public class EpigrafeResource implements ContainerResponseFilter{
    OposicionFacadeLocal oposicionFacade = lookupOposicionFacadeLocal();
    EpigrafeFacadeLocal epigrafeFacade = lookupEpigrafeFacadeLocal();
    private static final String SERV_ERR="Error en el sistema al acceder a la configuracion";
    private static final String CONF_BORR_ERROR="Error al intentar borrar la configuracion seleccionada";
    private static final String CONF_ADD_MISSING="Faltan datos o los nombres de los parametros no son correctos";
    private static final String CONF_ADD_WRONG="Los tipos de datos pasados son incorrectos";
    private static final String CONF_ADD_FAIL="Configuracion no se pudo introducir";
    private static final String CONF_EDIT_NOT_FOUND="La configuracion que intentas editar no existe, o los parametros introducidos no son validos";
    private static final String ERR_AUTH="No tiene permisos para realizar esa operacion";
    @Context
    private UriInfo context;
    int numValores = 20;
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
    /**
     * Creates a new instance of EpigrafeResource
     */
    public EpigrafeResource() {
    }
    @GET
    @Produces( "application/json")
    public Epigrafe[] findAll(@QueryParam("page") int page) {
        if(page==0){
            return epigrafeFacade.findAll().toArray(new Epigrafe[0]);
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return epigrafeFacade.findRange(array).toArray(new Epigrafe[0]);
    }
    
    @GET
    @Path("{id}")
    @Produces( "application/json")
    public Epigrafe find(@PathParam("id") String id) {
        return epigrafeFacade.find(id);
    }
    
    @GET
    @Path("{id}/departamentos")
    @Produces( "application/json")
    public List<RelDepEpi> findEpigrafe(@PathParam("id") String id,@QueryParam("page") int page) {
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return epigrafeFacade.findDep(id,array);
    }
    
    @GET
    @Path("{id}/departamentos/{etqDep}/oposiciones")
    @Produces( "application/json")
    public List<Oposicion> findEpigrafeOposicion(@PathParam("id") String id,@PathParam("etqDep") String etqDep,@QueryParam("page") int page) {
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findOposicion(etqDep,id,array,null);
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
    @GET
    @Path("search/{busqueda}")
    @Produces( "application/json")
    public Epigrafe[] findBusqueda(@PathParam("busqueda") String busqueda,@QueryParam("page") int page) {
        page--;
        if(page<0){
            page =0;
        }
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return epigrafeFacade.findEpigrafeBusqueda(busqueda,array).toArray(new Epigrafe[0]);
        //return Response.status(Response.Status.OK).entity(oposicionFacade.findRange(array).toArray(new Oposicion[0])).status(oposicionFacade.count())
           //         .build();
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
