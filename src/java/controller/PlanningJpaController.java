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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import repository.Planning;
import repository.User;

public class PlanningJpaController implements Serializable {

    public PlanningJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Planning planning) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = planning.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getIdUser());
                planning.setUserId(userId);
            }
            em.persist(planning);
            if (userId != null) {
                userId.getPlanningCollection().add(planning);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Planning planning) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planning persistentPlanning = em.find(Planning.class, planning.getIdPlanning());
            User userIdOld = persistentPlanning.getUserId();
            User userIdNew = planning.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getIdUser());
                planning.setUserId(userIdNew);
            }
            planning = em.merge(planning);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getPlanningCollection().remove(planning);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getPlanningCollection().add(planning);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planning.getIdPlanning();
                if (findPlanning(id) == null) {
                    throw new NonexistentEntityException("The planning with id " + id + " no longer exists.");
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
            Planning planning;
            try {
                planning = em.getReference(Planning.class, id);
                planning.getIdPlanning();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planning with id " + id + " no longer exists.", enfe);
            }
            User userId = planning.getUserId();
            if (userId != null) {
                userId.getPlanningCollection().remove(planning);
                userId = em.merge(userId);
            }
            em.remove(planning);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Planning> findPlanningEntities() {
        return findPlanningEntities(true, -1, -1);
    }

    public List<Planning> findPlanningEntities(int maxResults, int firstResult) {
        return findPlanningEntities(false, maxResults, firstResult);
    }

    private List<Planning> findPlanningEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Planning.class));
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

    public Planning findPlanning(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Planning.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanningCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Planning> rt = cq.from(Planning.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
