/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import repository.User;
import repository.BuddiesDiving;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import repository.Divelog;

public class DivelogJpaController implements Serializable {

    public DivelogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Divelog divelog) {
        if (divelog.getBuddiesDivingCollection() == null) {
            divelog.setBuddiesDivingCollection(new ArrayList<BuddiesDiving>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = divelog.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getIdUser());
                divelog.setUserId(userId);
            }
            Collection<BuddiesDiving> attachedBuddiesDivingCollection = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollectionBuddiesDivingToAttach : divelog.getBuddiesDivingCollection()) {
                buddiesDivingCollectionBuddiesDivingToAttach = em.getReference(buddiesDivingCollectionBuddiesDivingToAttach.getClass(), buddiesDivingCollectionBuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollection.add(buddiesDivingCollectionBuddiesDivingToAttach);
            }
            divelog.setBuddiesDivingCollection(attachedBuddiesDivingCollection);
            em.persist(divelog);
            if (userId != null) {
                userId.getDivelogCollection().add(divelog);
                userId = em.merge(userId);
            }
            for (BuddiesDiving buddiesDivingCollectionBuddiesDiving : divelog.getBuddiesDivingCollection()) {
                Divelog oldDivingIdOfBuddiesDivingCollectionBuddiesDiving = buddiesDivingCollectionBuddiesDiving.getDivingId();
                buddiesDivingCollectionBuddiesDiving.setDivingId(divelog);
                buddiesDivingCollectionBuddiesDiving = em.merge(buddiesDivingCollectionBuddiesDiving);
                if (oldDivingIdOfBuddiesDivingCollectionBuddiesDiving != null) {
                    oldDivingIdOfBuddiesDivingCollectionBuddiesDiving.getBuddiesDivingCollection().remove(buddiesDivingCollectionBuddiesDiving);
                    oldDivingIdOfBuddiesDivingCollectionBuddiesDiving = em.merge(oldDivingIdOfBuddiesDivingCollectionBuddiesDiving);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Divelog divelog) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Divelog persistentDivelog = em.find(Divelog.class, divelog.getIdDivelog());
            User userIdOld = persistentDivelog.getUserId();
            User userIdNew = divelog.getUserId();
            Collection<BuddiesDiving> buddiesDivingCollectionOld = persistentDivelog.getBuddiesDivingCollection();
            Collection<BuddiesDiving> buddiesDivingCollectionNew = divelog.getBuddiesDivingCollection();
            List<String> illegalOrphanMessages = null;
            for (BuddiesDiving buddiesDivingCollectionOldBuddiesDiving : buddiesDivingCollectionOld) {
                if (!buddiesDivingCollectionNew.contains(buddiesDivingCollectionOldBuddiesDiving)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BuddiesDiving " + buddiesDivingCollectionOldBuddiesDiving + " since its divingId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getIdUser());
                divelog.setUserId(userIdNew);
            }
            Collection<BuddiesDiving> attachedBuddiesDivingCollectionNew = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollectionNewBuddiesDivingToAttach : buddiesDivingCollectionNew) {
                buddiesDivingCollectionNewBuddiesDivingToAttach = em.getReference(buddiesDivingCollectionNewBuddiesDivingToAttach.getClass(), buddiesDivingCollectionNewBuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollectionNew.add(buddiesDivingCollectionNewBuddiesDivingToAttach);
            }
            buddiesDivingCollectionNew = attachedBuddiesDivingCollectionNew;
            divelog.setBuddiesDivingCollection(buddiesDivingCollectionNew);
            divelog = em.merge(divelog);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getDivelogCollection().remove(divelog);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getDivelogCollection().add(divelog);
                userIdNew = em.merge(userIdNew);
            }
            for (BuddiesDiving buddiesDivingCollectionNewBuddiesDiving : buddiesDivingCollectionNew) {
                if (!buddiesDivingCollectionOld.contains(buddiesDivingCollectionNewBuddiesDiving)) {
                    Divelog oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving = buddiesDivingCollectionNewBuddiesDiving.getDivingId();
                    buddiesDivingCollectionNewBuddiesDiving.setDivingId(divelog);
                    buddiesDivingCollectionNewBuddiesDiving = em.merge(buddiesDivingCollectionNewBuddiesDiving);
                    if (oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving != null && !oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving.equals(divelog)) {
                        oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving.getBuddiesDivingCollection().remove(buddiesDivingCollectionNewBuddiesDiving);
                        oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving = em.merge(oldDivingIdOfBuddiesDivingCollectionNewBuddiesDiving);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = divelog.getIdDivelog();
                if (findDivelog(id) == null) {
                    throw new NonexistentEntityException("The divelog with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Divelog divelog;
            try {
                divelog = em.getReference(Divelog.class, id);
                divelog.getIdDivelog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The divelog with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BuddiesDiving> buddiesDivingCollectionOrphanCheck = divelog.getBuddiesDivingCollection();
            for (BuddiesDiving buddiesDivingCollectionOrphanCheckBuddiesDiving : buddiesDivingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Divelog (" + divelog + ") cannot be destroyed since the BuddiesDiving " + buddiesDivingCollectionOrphanCheckBuddiesDiving + " in its buddiesDivingCollection field has a non-nullable divingId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = divelog.getUserId();
            if (userId != null) {
                userId.getDivelogCollection().remove(divelog);
                userId = em.merge(userId);
            }
            em.remove(divelog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Divelog> findDivelogEntities() {
        return findDivelogEntities(true, -1, -1);
    }

    public List<Divelog> findDivelogEntities(int maxResults, int firstResult) {
        return findDivelogEntities(false, maxResults, firstResult);
    }

    private List<Divelog> findDivelogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Divelog.class));
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

    public Divelog findDivelog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Divelog.class, id);
        } finally {
            em.close();
        }
    }

    public int getDivelogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Divelog> rt = cq.from(Divelog.class);
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
    
    //Devuelve el listado de inmersiones del usuario indicado (ID)
    public List<Divelog> findInmersionEntities(int idUser) {
        EntityManager em = getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Divelog> cq = cb.createQuery(Divelog.class);
            Root<Divelog> rt = cq.from(Divelog.class);

            Predicate userNick = cb.equal(rt.get("idUser"),idUser);
            cq.select(rt).where(userNick);
         
            List<Divelog> lista = em.createQuery(cq).getResultList();
            
            return lista;
        } finally {
            em.close();
        }
    }

}
