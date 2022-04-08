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
import repository.Agency;
import repository.UserCertification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import repository.Certification;

public class CertificationJpaController implements Serializable {

    public CertificationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Certification certification) {
        if (certification.getUserCertificationCollection() == null) {
            certification.setUserCertificationCollection(new ArrayList<UserCertification>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agency agencyId = certification.getAgencyId();
            if (agencyId != null) {
                agencyId = em.getReference(agencyId.getClass(), agencyId.getIdAgency());
                certification.setAgencyId(agencyId);
            }
            Collection<UserCertification> attachedUserCertificationCollection = new ArrayList<UserCertification>();
            for (UserCertification userCertificationCollectionUserCertificationToAttach : certification.getUserCertificationCollection()) {
                userCertificationCollectionUserCertificationToAttach = em.getReference(userCertificationCollectionUserCertificationToAttach.getClass(), userCertificationCollectionUserCertificationToAttach.getIdUserCertification());
                attachedUserCertificationCollection.add(userCertificationCollectionUserCertificationToAttach);
            }
            certification.setUserCertificationCollection(attachedUserCertificationCollection);
            em.persist(certification);
            if (agencyId != null) {
                agencyId.getCertificationCollection().add(certification);
                agencyId = em.merge(agencyId);
            }
            for (UserCertification userCertificationCollectionUserCertification : certification.getUserCertificationCollection()) {
                Certification oldCertificationIdOfUserCertificationCollectionUserCertification = userCertificationCollectionUserCertification.getCertificationId();
                userCertificationCollectionUserCertification.setCertificationId(certification);
                userCertificationCollectionUserCertification = em.merge(userCertificationCollectionUserCertification);
                if (oldCertificationIdOfUserCertificationCollectionUserCertification != null) {
                    oldCertificationIdOfUserCertificationCollectionUserCertification.getUserCertificationCollection().remove(userCertificationCollectionUserCertification);
                    oldCertificationIdOfUserCertificationCollectionUserCertification = em.merge(oldCertificationIdOfUserCertificationCollectionUserCertification);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Certification certification) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Certification persistentCertification = em.find(Certification.class, certification.getIdCertification());
            Agency agencyIdOld = persistentCertification.getAgencyId();
            Agency agencyIdNew = certification.getAgencyId();
            Collection<UserCertification> userCertificationCollectionOld = persistentCertification.getUserCertificationCollection();
            Collection<UserCertification> userCertificationCollectionNew = certification.getUserCertificationCollection();
            List<String> illegalOrphanMessages = null;
            for (UserCertification userCertificationCollectionOldUserCertification : userCertificationCollectionOld) {
                if (!userCertificationCollectionNew.contains(userCertificationCollectionOldUserCertification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserCertification " + userCertificationCollectionOldUserCertification + " since its certificationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (agencyIdNew != null) {
                agencyIdNew = em.getReference(agencyIdNew.getClass(), agencyIdNew.getIdAgency());
                certification.setAgencyId(agencyIdNew);
            }
            Collection<UserCertification> attachedUserCertificationCollectionNew = new ArrayList<UserCertification>();
            for (UserCertification userCertificationCollectionNewUserCertificationToAttach : userCertificationCollectionNew) {
                userCertificationCollectionNewUserCertificationToAttach = em.getReference(userCertificationCollectionNewUserCertificationToAttach.getClass(), userCertificationCollectionNewUserCertificationToAttach.getIdUserCertification());
                attachedUserCertificationCollectionNew.add(userCertificationCollectionNewUserCertificationToAttach);
            }
            userCertificationCollectionNew = attachedUserCertificationCollectionNew;
            certification.setUserCertificationCollection(userCertificationCollectionNew);
            certification = em.merge(certification);
            if (agencyIdOld != null && !agencyIdOld.equals(agencyIdNew)) {
                agencyIdOld.getCertificationCollection().remove(certification);
                agencyIdOld = em.merge(agencyIdOld);
            }
            if (agencyIdNew != null && !agencyIdNew.equals(agencyIdOld)) {
                agencyIdNew.getCertificationCollection().add(certification);
                agencyIdNew = em.merge(agencyIdNew);
            }
            for (UserCertification userCertificationCollectionNewUserCertification : userCertificationCollectionNew) {
                if (!userCertificationCollectionOld.contains(userCertificationCollectionNewUserCertification)) {
                    Certification oldCertificationIdOfUserCertificationCollectionNewUserCertification = userCertificationCollectionNewUserCertification.getCertificationId();
                    userCertificationCollectionNewUserCertification.setCertificationId(certification);
                    userCertificationCollectionNewUserCertification = em.merge(userCertificationCollectionNewUserCertification);
                    if (oldCertificationIdOfUserCertificationCollectionNewUserCertification != null && !oldCertificationIdOfUserCertificationCollectionNewUserCertification.equals(certification)) {
                        oldCertificationIdOfUserCertificationCollectionNewUserCertification.getUserCertificationCollection().remove(userCertificationCollectionNewUserCertification);
                        oldCertificationIdOfUserCertificationCollectionNewUserCertification = em.merge(oldCertificationIdOfUserCertificationCollectionNewUserCertification);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = certification.getIdCertification();
                if (findCertification(id) == null) {
                    throw new NonexistentEntityException("The certification with id " + id + " no longer exists.");
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
            Certification certification;
            try {
                certification = em.getReference(Certification.class, id);
                certification.getIdCertification();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The certification with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UserCertification> userCertificationCollectionOrphanCheck = certification.getUserCertificationCollection();
            for (UserCertification userCertificationCollectionOrphanCheckUserCertification : userCertificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Certification (" + certification + ") cannot be destroyed since the UserCertification " + userCertificationCollectionOrphanCheckUserCertification + " in its userCertificationCollection field has a non-nullable certificationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Agency agencyId = certification.getAgencyId();
            if (agencyId != null) {
                agencyId.getCertificationCollection().remove(certification);
                agencyId = em.merge(agencyId);
            }
            em.remove(certification);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Certification> findCertificationEntities() {
        return findCertificationEntities(true, -1, -1);
    }

    public List<Certification> findCertificationEntities(int maxResults, int firstResult) {
        return findCertificationEntities(false, maxResults, firstResult);
    }

    private List<Certification> findCertificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Certification.class));
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

    public Certification findCertification(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Certification.class, id);
        } finally {
            em.close();
        }
    }

    public int getCertificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Certification> rt = cq.from(Certification.class);
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
    
    //Devuelve el listado de certificaciones de la agencia indicada (ID)
    public List<Certification> findCertificationsFromAgencyID(int idAgency) {
        EntityManager em = getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Certification> cq = cb.createQuery(Certification.class);
            Root<Certification> rt = cq.from(Certification.class);

            Predicate id = cb.equal(rt.get("idAgency"),idAgency);
            cq.select(rt).where(id);
         
            List<Certification> list = em.createQuery(cq).getResultList();
            
            return list;
        } finally {
            em.close();
        }
    }

}
