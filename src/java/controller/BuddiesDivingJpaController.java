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
import repository.BuddiesDiving;
import repository.Divelog;
import repository.User;

public class BuddiesDivingJpaController implements Serializable {

    public BuddiesDivingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BuddiesDiving buddiesDiving) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Divelog divingId = buddiesDiving.getDivingId();
            if (divingId != null) {
                divingId = em.getReference(divingId.getClass(), divingId.getIdDivelog());
                buddiesDiving.setDivingId(divingId);
            }
            User buddyId = buddiesDiving.getBuddyId();
            if (buddyId != null) {
                buddyId = em.getReference(buddyId.getClass(), buddyId.getIdUser());
                buddiesDiving.setBuddyId(buddyId);
            }
            User userId = buddiesDiving.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getIdUser());
                buddiesDiving.setUserId(userId);
            }
            em.persist(buddiesDiving);
            if (divingId != null) {
                divingId.getBuddiesDivingCollection().add(buddiesDiving);
                divingId = em.merge(divingId);
            }
            if (buddyId != null) {
                buddyId.getBuddiesDivingCollection().add(buddiesDiving);
                buddyId = em.merge(buddyId);
            }
            if (userId != null) {
                userId.getBuddiesDivingCollection().add(buddiesDiving);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BuddiesDiving buddiesDiving) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BuddiesDiving persistentBuddiesDiving = em.find(BuddiesDiving.class, buddiesDiving.getIdRelationBuddy());
            Divelog divingIdOld = persistentBuddiesDiving.getDivingId();
            Divelog divingIdNew = buddiesDiving.getDivingId();
            User buddyIdOld = persistentBuddiesDiving.getBuddyId();
            User buddyIdNew = buddiesDiving.getBuddyId();
            User userIdOld = persistentBuddiesDiving.getUserId();
            User userIdNew = buddiesDiving.getUserId();
            if (divingIdNew != null) {
                divingIdNew = em.getReference(divingIdNew.getClass(), divingIdNew.getIdDivelog());
                buddiesDiving.setDivingId(divingIdNew);
            }
            if (buddyIdNew != null) {
                buddyIdNew = em.getReference(buddyIdNew.getClass(), buddyIdNew.getIdUser());
                buddiesDiving.setBuddyId(buddyIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getIdUser());
                buddiesDiving.setUserId(userIdNew);
            }
            buddiesDiving = em.merge(buddiesDiving);
            if (divingIdOld != null && !divingIdOld.equals(divingIdNew)) {
                divingIdOld.getBuddiesDivingCollection().remove(buddiesDiving);
                divingIdOld = em.merge(divingIdOld);
            }
            if (divingIdNew != null && !divingIdNew.equals(divingIdOld)) {
                divingIdNew.getBuddiesDivingCollection().add(buddiesDiving);
                divingIdNew = em.merge(divingIdNew);
            }
            if (buddyIdOld != null && !buddyIdOld.equals(buddyIdNew)) {
                buddyIdOld.getBuddiesDivingCollection().remove(buddiesDiving);
                buddyIdOld = em.merge(buddyIdOld);
            }
            if (buddyIdNew != null && !buddyIdNew.equals(buddyIdOld)) {
                buddyIdNew.getBuddiesDivingCollection().add(buddiesDiving);
                buddyIdNew = em.merge(buddyIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getBuddiesDivingCollection().remove(buddiesDiving);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getBuddiesDivingCollection().add(buddiesDiving);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = buddiesDiving.getIdRelationBuddy();
                if (findBuddiesDiving(id) == null) {
                    throw new NonexistentEntityException("The buddiesDiving with id " + id + " no longer exists.");
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
            BuddiesDiving buddiesDiving;
            try {
                buddiesDiving = em.getReference(BuddiesDiving.class, id);
                buddiesDiving.getIdRelationBuddy();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buddiesDiving with id " + id + " no longer exists.", enfe);
            }
            Divelog divingId = buddiesDiving.getDivingId();
            if (divingId != null) {
                divingId.getBuddiesDivingCollection().remove(buddiesDiving);
                divingId = em.merge(divingId);
            }
            User buddyId = buddiesDiving.getBuddyId();
            if (buddyId != null) {
                buddyId.getBuddiesDivingCollection().remove(buddiesDiving);
                buddyId = em.merge(buddyId);
            }
            User userId = buddiesDiving.getUserId();
            if (userId != null) {
                userId.getBuddiesDivingCollection().remove(buddiesDiving);
                userId = em.merge(userId);
            }
            em.remove(buddiesDiving);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BuddiesDiving> findBuddiesDivingEntities() {
        return findBuddiesDivingEntities(true, -1, -1);
    }

    public List<BuddiesDiving> findBuddiesDivingEntities(int maxResults, int firstResult) {
        return findBuddiesDivingEntities(false, maxResults, firstResult);
    }

    private List<BuddiesDiving> findBuddiesDivingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BuddiesDiving.class));
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

    public BuddiesDiving findBuddiesDiving(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BuddiesDiving.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuddiesDivingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BuddiesDiving> rt = cq.from(BuddiesDiving.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
