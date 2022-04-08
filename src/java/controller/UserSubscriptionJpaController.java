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
import repository.User;
import repository.UserSubscription;

public class UserSubscriptionJpaController implements Serializable {

    public UserSubscriptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserSubscription userSubscription) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User followerUserId = userSubscription.getFollowerUserId();
            if (followerUserId != null) {
                followerUserId = em.getReference(followerUserId.getClass(), followerUserId.getIdUser());
                userSubscription.setFollowerUserId(followerUserId);
            }
            User liderUserId = userSubscription.getLiderUserId();
            if (liderUserId != null) {
                liderUserId = em.getReference(liderUserId.getClass(), liderUserId.getIdUser());
                userSubscription.setLiderUserId(liderUserId);
            }
            em.persist(userSubscription);
            if (followerUserId != null) {
                followerUserId.getUserSubscriptionCollection().add(userSubscription);
                followerUserId = em.merge(followerUserId);
            }
            if (liderUserId != null) {
                liderUserId.getUserSubscriptionCollection().add(userSubscription);
                liderUserId = em.merge(liderUserId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserSubscription userSubscription) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserSubscription persistentUserSubscription = em.find(UserSubscription.class, userSubscription.getIdSubscription());
            User followerUserIdOld = persistentUserSubscription.getFollowerUserId();
            User followerUserIdNew = userSubscription.getFollowerUserId();
            User liderUserIdOld = persistentUserSubscription.getLiderUserId();
            User liderUserIdNew = userSubscription.getLiderUserId();
            if (followerUserIdNew != null) {
                followerUserIdNew = em.getReference(followerUserIdNew.getClass(), followerUserIdNew.getIdUser());
                userSubscription.setFollowerUserId(followerUserIdNew);
            }
            if (liderUserIdNew != null) {
                liderUserIdNew = em.getReference(liderUserIdNew.getClass(), liderUserIdNew.getIdUser());
                userSubscription.setLiderUserId(liderUserIdNew);
            }
            userSubscription = em.merge(userSubscription);
            if (followerUserIdOld != null && !followerUserIdOld.equals(followerUserIdNew)) {
                followerUserIdOld.getUserSubscriptionCollection().remove(userSubscription);
                followerUserIdOld = em.merge(followerUserIdOld);
            }
            if (followerUserIdNew != null && !followerUserIdNew.equals(followerUserIdOld)) {
                followerUserIdNew.getUserSubscriptionCollection().add(userSubscription);
                followerUserIdNew = em.merge(followerUserIdNew);
            }
            if (liderUserIdOld != null && !liderUserIdOld.equals(liderUserIdNew)) {
                liderUserIdOld.getUserSubscriptionCollection().remove(userSubscription);
                liderUserIdOld = em.merge(liderUserIdOld);
            }
            if (liderUserIdNew != null && !liderUserIdNew.equals(liderUserIdOld)) {
                liderUserIdNew.getUserSubscriptionCollection().add(userSubscription);
                liderUserIdNew = em.merge(liderUserIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userSubscription.getIdSubscription();
                if (findUserSubscription(id) == null) {
                    throw new NonexistentEntityException("The userSubscription with id " + id + " no longer exists.");
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
            UserSubscription userSubscription;
            try {
                userSubscription = em.getReference(UserSubscription.class, id);
                userSubscription.getIdSubscription();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userSubscription with id " + id + " no longer exists.", enfe);
            }
            User followerUserId = userSubscription.getFollowerUserId();
            if (followerUserId != null) {
                followerUserId.getUserSubscriptionCollection().remove(userSubscription);
                followerUserId = em.merge(followerUserId);
            }
            User liderUserId = userSubscription.getLiderUserId();
            if (liderUserId != null) {
                liderUserId.getUserSubscriptionCollection().remove(userSubscription);
                liderUserId = em.merge(liderUserId);
            }
            em.remove(userSubscription);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserSubscription> findUserSubscriptionEntities() {
        return findUserSubscriptionEntities(true, -1, -1);
    }

    public List<UserSubscription> findUserSubscriptionEntities(int maxResults, int firstResult) {
        return findUserSubscriptionEntities(false, maxResults, firstResult);
    }

    private List<UserSubscription> findUserSubscriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserSubscription.class));
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

    public UserSubscription findUserSubscription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserSubscription.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserSubscriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserSubscription> rt = cq.from(UserSubscription.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
