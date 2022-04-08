/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

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
import repository.User;

/**
 * REST Web Service
 *
 * @author Oleksandr Veretennykov
 */
@Path("user")
public class UserServiceREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioServiceREST
     */
    public UserServiceREST() {
    }

    /**
     * Devuelve el listado de todos los usuarios registrados
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<User> listUsers = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            UserJpaController dao = new UserJpaController(emf);
            listUsers = dao.findUserEntities();
            
            if (listUsers == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listUsers)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve el listado de todos los usuarios que tengan el perfil PUBLICO
     */
    @GET
    @Path("/public/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPublicUsers() {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        List<User> listUsers = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            UserJpaController dao = new UserJpaController(emf);
            listUsers = dao.getPublicUsers();
            
            if (listUsers == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(listUsers)
                    .build();
            
            return response;
        }
    }
    
    /**
     * Devuelve el objeto Usuario
     */
    @GET
    @Path("/nickname/{nickName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLogs(@PathParam("nickName") String nickName) {
        EntityManagerFactory emf = null;
        Response.Status statusResul = Response.Status.OK;
        User user = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("ApiRestCalypsoPU");
            UserJpaController daoUsuario = new UserJpaController(emf);
            user = daoUsuario.getUserFromNickName(nickName);
            
            if (user == null) {
                statusResul = Response.Status.NO_CONTENT;
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
        } finally {
            emf.close();
            Response response = Response
                    .status(statusResul)
                    .entity(user)
                    .build();
            
            return response;
        }
    }

    

    /**
     * PUT method for updating or creating an instance of UserServiceREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
