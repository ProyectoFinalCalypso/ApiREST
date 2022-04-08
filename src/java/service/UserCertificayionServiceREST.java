/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.UserCertificationJpaController;
import controller.UserJpaController;
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
import repository.UserCertification;

/**
 * REST Web Service
 *
 * @author Oleksandr Veretennykov
 */
@Path("usercertification")
public class UserCertificayionServiceREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioCertificacionServiceREST
     */
    public UserCertificayionServiceREST() {
    }

    /*
     *  Devuelve todas las certificaciones del usuario indicado  
    */
    @GET
    @Path("/{nickName}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCertificationsFromAgencyId(@PathParam("nickName") String nickName) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<UserCertification> list = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            UserCertificationJpaController daoUsuarioCerticicacion = new UserCertificationJpaController(emf);
            UserJpaController daoUsuario = new UserJpaController(emf);
            
            // Obtiene la ID del usuario
            int idUser = daoUsuario.getUserId(nickName);
            // Recupera el listado de certificaciones del usuario
            list = daoUsuarioCerticicacion.findUserCertificationsList(idUser);
            
            if (list == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(list)
                    .build();
            
            return response;
        }
    }
    
    /*
     *  Devuelve la certificacion solicitada 
    */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCertificationsFromAgencyId(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        UserCertification uc = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            UserCertificationJpaController dao = new UserCertificationJpaController(emf);

            uc = dao.findUserCertification(id);
            
            if (uc == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(uc)
                    .build();
            
            return response;
        }
    }

    /**
     * PUT method for updating or creating an instance of UserCertificayionServiceREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
