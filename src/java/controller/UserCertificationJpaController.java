/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import repository.Certification;
import repository.User;
import repository.UserCertification;

public class UserCertificationJpaController implements Serializable {

    public UserCertificationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserCertification userCertification) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Certification certificationId = userCertification.getCertificationId();
            if (certificationId != null) {
                certificationId = em.getReference(certificationId.getClass(), certificationId.getIdCertification());
                userCertification.setCertificationId(certificationId);
            }
            User userId = userCertification.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getIdUser());
                userCertification.setUserId(userId);
            }
            em.persist(userCertification);
            if (certificationId != null) {
                certificationId.getUserCertificationCollection().add(userCertification);
                certificationId = em.merge(certificationId);
            }
            if (userId != null) {
                userId.getUserCertificationCollection().add(userCertification);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserCertification userCertification) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserCertification persistentUserCertification = em.find(UserCertification.class, userCertification.getIdUserCertification());
            Certification certificationIdOld = persistentUserCertification.getCertificationId();
            Certification certificationIdNew = userCertification.getCertificationId();
            User userIdOld = persistentUserCertification.getUserId();
            User userIdNew = userCertification.getUserId();
            if (certificationIdNew != null) {
                certificationIdNew = em.getReference(certificationIdNew.getClass(), certificationIdNew.getIdCertification());
                userCertification.setCertificationId(certificationIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getIdUser());
                userCertification.setUserId(userIdNew);
            }
            userCertification = em.merge(userCertification);
            if (certificationIdOld != null && !certificationIdOld.equals(certificationIdNew)) {
                certificationIdOld.getUserCertificationCollection().remove(userCertification);
                certificationIdOld = em.merge(certificationIdOld);
            }
            if (certificationIdNew != null && !certificationIdNew.equals(certificationIdOld)) {
                certificationIdNew.getUserCertificationCollection().add(userCertification);
                certificationIdNew = em.merge(certificationIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getUserCertificationCollection().remove(userCertification);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getUserCertificationCollection().add(userCertification);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userCertification.getIdUserCertification();
                if (findUserCertification(id) == null) {
                    throw new NonexistentEntityException("The userCertification with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserCertification userCertification;
            try {
                userCertification = em.getReference(UserCertification.class, id);
                userCertification.getIdUserCertification();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userCertification with id " + id + " no longer exists.", enfe);
            }
            Certification certificationId = userCertification.getCertificationId();
            if (certificationId != null) {
                certificationId.getUserCertificationCollection().remove(userCertification);
                certificationId = em.merge(certificationId);
            }
            User userId = userCertification.getUserId();
            if (userId != null) {
                userId.getUserCertificationCollection().remove(userCertification);
                userId = em.merge(userId);
            }
            em.remove(userCertification);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserCertification> findUserCertificationEntities() {
        return findUserCertificationEntities(true, -1, -1);
    }

    public List<UserCertification> findUserCertificationEntities(int maxResults, int firstResult) {
        return findUserCertificationEntities(false, maxResults, firstResult);
    }

    private List<UserCertification> findUserCertificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserCertification.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UserCertification findUserCertification(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserCertification.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCertificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserCertification> rt = cq.from(UserCertification.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /*
    ***************************************************************************
    * Consultas agregadas manualmente
    ***************************************************************************
    */
    
    //Devuelve el listado de certificaciones del usuario indicado
    public List<UserCertification> findUserCertificationsList(int idUsuario) {
        EntityManager em = getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserCertification> cq = cb.createQuery(UserCertification.class);
            Root<UserCertification> rt = cq.from(UserCertification.class);

            Predicate id = cb.equal(rt.get("idUser"),idUsuario);
            cq.select(rt).where(id);
         
            List<UserCertification> list = em.createQuery(cq).getResultList();
            
            return list;
        } finally {
            em.close();
        }
    }

}
