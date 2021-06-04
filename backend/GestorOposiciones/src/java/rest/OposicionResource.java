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


/**
 * REST Web Service
 *
 * @author javie
 */
@Path("oposicion")
public class OposicionResource implements ContainerResponseFilter{
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
    /*@GET
    @Produces({"application/xml", "application/json"})
    public Response findAll() {
        return Response.status(Response.Status.OK)// Si si esta autenticado
                    .entity(oposicionFacade.findAll().toArray(new Oposicion[0]))
                    .build();
        //return oposicionFacade.findAll();
    }*/
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
    @Path("fechas")
    @Produces( "application/json")
    public List<Oposicion> findBetween(@QueryParam("fecha1") Date fecha1,@QueryParam("fecha2") Date fecha2,@QueryParam("page") int page) {
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findOposicionFechas(fecha1,fecha2,array);
        //return Response.status(Response.Status.OK).entity(oposicionFacade.findRange(array).toArray(new Oposicion[0])).status(oposicionFacade.count())
           //         .build();
    }
    
    @GET
    @Produces( "application/json")
    public Oposicion[] findAll(@QueryParam("fecha") Date fecha,@QueryParam("page") int page) {
        if(page==0){
            return oposicionFacade.findAll().toArray(new Oposicion[0]);
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        return oposicionFacade.findRange(array,fecha).toArray(new Oposicion[0]);
        //return Response.status(Response.Status.OK).entity(oposicionFacade.findRange(array).toArray(new Oposicion[0])).status(oposicionFacade.count())
           //         .build();
    }
    
    //Devuelve 1 si la peticion es solo para el Id, 2 si es una fecha, 3 si es id y fecha, 4 si es entre fechas,5 si es id y entre fechas
    int tipoPeticion(String id, Date fecha1, Date fecha2){        
        if(id.equals("")&&fecha1!=null&&fecha2==null){
            return 2;
        }
        if(id.equals("")&&fecha1!=null&&fecha2!=null){
            return 4;
        }
        if(!id.equals("")&&fecha1!=null&&fecha2==null){
            return 3;
        }
        if(!id.equals("")&&fecha1!=null&&fecha2!=null){
            return 5;
        }
        return 1;
    }
    @GET
    @Path("search")
    @Produces( "application/json")
    public List<Oposicion> findBusquedaCompleta(@QueryParam("busqueda") String busqueda,@QueryParam("departamento") String departamento,@QueryParam("fecha1") Date fecha1,@QueryParam("fecha2") Date fecha2,@QueryParam("page") int page) {
        if(page<1){
            page =0;
        }
        page--;
        int array[] = new int[2];
        array[0] = page*numValores;
        array[1] = array[0]+numValores-1;
        int tipo = tipoPeticion(busqueda,fecha1,fecha2);

        switch(tipo){
            //Busqueda de una fecha
            case 2:
                System.out.println("fecha");
                return oposicionFacade.findRange(array, fecha1);
            //Busqueda por id en una fecha
            case 3:
                System.out.println("busqueda fecha ");
                return oposicionFacade.findOposicionFechaId(busqueda, fecha1, array);
            //Busqueda entre fechas
            case 4:
                System.out.println("fecha1 fecha2");
                return oposicionFacade.findOposicionFechas(fecha1, fecha2, array);
            //Busqueda por id entre fechas
            case 5:
                System.out.println("busqueda fecha fecha2");
                return oposicionFacade.findOposicionFechasId(busqueda, fecha1, fecha2, array);
            
        }
        
        return oposicionFacade.findOposicionBusqueda(busqueda, array);
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

    
    
}
