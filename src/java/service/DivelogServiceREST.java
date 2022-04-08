/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.DivelogJpaController;
import controller.UserJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import repository.Divelog;

/**
 * REST Web Service
 *
 * @author Oleksandr Veretennykov
 */
@Path("divelog")
public class DivelogServiceREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServiceRESTCalypso
     */
    public DivelogServiceREST() {
    }

    /**
     * Devuelve el listado de todas las Inmersiones guardadas en la BBDD
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Status statusResul = Response.Status.OK;
        List<Divelog> listLogs = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            DivelogJpaController dao = new DivelogJpaController(emf);
            listLogs = dao.findDivelogEntities();
            
            if (listLogs == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listLogs)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve el listado de Inmersiones del usuario indicado
     */
    @GET
    @Path("/nickname/{nickName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLogs(@PathParam("nickName") String nickName) {
        EntityManagerFactory emf = null;
        Status statusResul = Response.Status.OK;
        List<Divelog> listaLogs = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            DivelogJpaController daoInmersiones = new DivelogJpaController(emf);
            UserJpaController daoUsuario = new UserJpaController(emf);
            
            // Obtiene la ID del usuario
            int idUser = daoUsuario.getUserId(nickName);
            // Recupera el logBook del usuario 
            listaLogs = daoInmersiones.findInmersionEntities(idUser);
            
            if (listaLogs == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listaLogs)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve la inmersion de la ID solicitada
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogFromId(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Status statusResul = Response.Status.OK;
        Divelog divelog = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            DivelogJpaController daoInmersiones = new DivelogJpaController(emf);
            divelog = daoInmersiones.findDivelog(id);
            
            if (divelog == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(divelog)
                    .build();
            
            return response;
        }
    }
    
    /**
     * PUT method for updating or creating an instance of DivelogServiceREST
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
