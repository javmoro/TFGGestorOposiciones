/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dominio.Oposicion;
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
import javax.ws.rs.core.Response.ResponseBuilder;
import persistencia.OposicionFacadeLocal;
import persistencia.ReferenciaAnteriorFacadeLocal;
import dominio.ReferenciaAnterior;

/**
 * REST Web Service
 *
 * @author javie
 */
@Path("oposicion")
public class OposicionResource implements ContainerResponseFilter{
    ReferenciaAnteriorFacadeLocal referenciaAnteriorFacade = lookupReferenciaAnteriorFacadeLocal();
    OposicionFacadeLocal oposicionFacade = lookupOposicionFacadeLocal();
    private static final String SERV_ERR="Error en el sistema al acceder a la configuracion";
    private static final String CONF_BORR_ERROR="Error al intentar borrar la configuracion seleccionada";
    private static final String CONF_ADD_MISSING="Faltan datos o los nombres de los parametros no son correctos";
    private static final String CONF_ADD_WRONG="Los tipos de datos pasados son incorrectos";
    private static final String CONF_ADD_FAIL="Configuracion no se pudo introducir";
    private static final String CONF_EDIT_NOT_FOUND="La configuracion que intentas editar no existe, o los parametros introducidos no son validos";
    private static final String ERR_AUTH="No tiene permisos para realizar esa operacion";
    @Context
    private UriInfo context;
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
    int numValores = 20;
    /**
     * Creates a new instance of OposicionResource
     */
    public OposicionResource() {
    }
    
    @GET
    @Path("{id}")
    @Produces( "application/json")
    public Oposicion find(@PathParam("id") String id) {
        return oposicionFacade.find(id);
    }
    
    @GET
    @Path("titulo/{id}")
    @Produces( "application/json")
    public List<Oposicion> findTitulo(@PathParam("id") String id) {
        return oposicionFacade.findTitulo(id);
    }

    
    @GET
    @Path("{id}/refAnterior")
    @Produces("applicaction/json")
    public List<ReferenciaAnterior> findRefAnteriores(@PathParam("id") String id) {
        return referenciaAnteriorFacade.findReferenciaAnterior(id);
    }
    
    @GET
    @Path("{id}/refPosterior")
    @Produces("applicaction/json")
    public List<ReferenciaAnterior> findRefPosteriores(@PathParam("id") String id) {
        return referenciaAnteriorFacade.findReferenciaPosterior(id);
    }
    
    
    @GET
    @Produces( "application/json")
    public Oposicion[] findAll(@QueryParam("fecha") Date fecha,@QueryParam("page") int page) {
        System.out.println("find oposiciones");
        if(page==0){
            return oposicionFacade.findAll().toArray(new Oposicion[0]);
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findRange(array,fecha).toArray(new Oposicion[0]);

    }
    
 
    @GET
    @Path("search/")
    @Produces( "application/json")
    
        public List<Oposicion> findBusquedaCompleta(@QueryParam("titulo") String titulo,@QueryParam("departamento") String departamento,@QueryParam("epigrafe") String epigrafe,@QueryParam("fecha1") Date fecha1,@QueryParam("fecha2") Date fecha2,@QueryParam("estado") String estado, @QueryParam("page") int page) {
        System.out.println("find busqueda");
        if(page<1){
            return oposicionFacade.findOposicionAvanzada(estado,fecha1,fecha2,departamento,epigrafe,titulo);
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findOposicionAvanzadaPage(estado,fecha1,fecha2,departamento,epigrafe,titulo,array);
    }        
    
    
    @GET
    @Path("count")
    @Produces( "text/plain")
    public int getSize() {
        return oposicionFacade.count();
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

    private ReferenciaAnteriorFacadeLocal lookupReferenciaAnteriorFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReferenciaAnteriorFacadeLocal) c.lookup("java:global/GestorOposiciones/ReferenciaAnteriorFacade!persistencia.ReferenciaAnteriorFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
