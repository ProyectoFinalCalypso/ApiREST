/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.AgencyJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import repository.Agency;

/**
 * REST Web Service
 *
 * @author Oleksandr Veretennykov
 */
@Path("agency")
public class AgencyServiceREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AgenciaServiceREST
     */
    public AgencyServiceREST() {
    }

    /**
     * Devuelve el listado de todas las Agencias guardadas en la BBDD
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<Agency> listAgencies = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            AgencyJpaController dao = new AgencyJpaController(emf);
            listAgencies = dao.findAgencyEntities();
            
            if (listAgencies == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listAgencies)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve la agencia solicitada
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogFromId(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Agency agencia = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            AgencyJpaController dao = new AgencyJpaController(emf);
            agencia = dao.findAgency(id);
            
            if (agencia == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(agencia)
                    .build();
            
            return response;
        }
    }

    /**
     * PUT method for updating or creating an instance of AgencyServiceREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
