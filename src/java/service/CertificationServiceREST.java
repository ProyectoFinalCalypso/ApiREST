/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.CertificationJpaController;
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
import repository.Certification;

/**
 * REST Web Service
 *
 * @author Oleksandr Veretennykov
 */
@Path("certification")
public class CertificationServiceREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CertificacionServiceREST
     */
    public CertificationServiceREST() {
    }

    /**
     * Devuelve el listado de todas las Inmersiones guardadas en la BBDD
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<Certification> listCertifications = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            CertificationJpaController dao = new CertificationJpaController(emf);
            listCertifications = dao.findCertificationEntities();
            
            if (listCertifications == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listCertifications)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve el listado de Certificaciones de la Agencia indicada (ID)
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCertificationsFromAgencyId(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        Certification c = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            CertificationJpaController dao = new CertificationJpaController(emf);
            c = dao.findCertification(id);
            
            if (c == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(c)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve el listado de Certificaciones de la Agencia indicada (ID)
     */
    @GET
    @Path("/agencyid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogFromId(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<Certification> listCertifications = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            CertificationJpaController dao = new CertificationJpaController(emf);
            listCertifications = dao.findCertificationsFromAgencyID(id);
            
            if (listCertifications == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listCertifications)
                    .build();
            
            return response;
        }
    }

    /**
     * PUT method for updating or creating an instance of CertificationServiceREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
