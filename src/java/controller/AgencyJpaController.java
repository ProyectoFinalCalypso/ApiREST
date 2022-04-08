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
import repository.Certification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import repository.Agency;

public class AgencyJpaController implements Serializable {

    public AgencyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agency agency) {
        if (agency.getCertificationCollection() == null) {
            agency.setCertificationCollection(new ArrayList<Certification>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Certification> attachedCertificationCollection = new ArrayList<Certification>();
            for (Certification certificationCollectionCertificationToAttach : agency.getCertificationCollection()) {
                certificationCollectionCertificationToAttach = em.getReference(certificationCollectionCertificationToAttach.getClass(), certificationCollectionCertificationToAttach.getIdCertification());
                attachedCertificationCollection.add(certificationCollectionCertificationToAttach);
            }
            agency.setCertificationCollection(attachedCertificationCollection);
            em.persist(agency);
            for (Certification certificationCollectionCertification : agency.getCertificationCollection()) {
                Agency oldAgencyIdOfCertificationCollectionCertification = certificationCollectionCertification.getAgencyId();
                certificationCollectionCertification.setAgencyId(agency);
                certificationCollectionCertification = em.merge(certificationCollectionCertification);
                if (oldAgencyIdOfCertificationCollectionCertification != null) {
                    oldAgencyIdOfCertificationCollectionCertification.getCertificationCollection().remove(certificationCollectionCertification);
                    oldAgencyIdOfCertificationCollectionCertification = em.merge(oldAgencyIdOfCertificationCollectionCertification);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agency agency) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agency persistentAgency = em.find(Agency.class, agency.getIdAgency());
            Collection<Certification> certificationCollectionOld = persistentAgency.getCertificationCollection();
            Collection<Certification> certificationCollectionNew = agency.getCertificationCollection();
            List<String> illegalOrphanMessages = null;
            for (Certification certificationCollectionOldCertification : certificationCollectionOld) {
                if (!certificationCollectionNew.contains(certificationCollectionOldCertification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Certification " + certificationCollectionOldCertification + " since its agencyId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Certification> attachedCertificationCollectionNew = new ArrayList<Certification>();
            for (Certification certificationCollectionNewCertificationToAttach : certificationCollectionNew) {
                certificationCollectionNewCertificationToAttach = em.getReference(certificationCollectionNewCertificationToAttach.getClass(), certificationCollectionNewCertificationToAttach.getIdCertification());
                attachedCertificationCollectionNew.add(certificationCollectionNewCertificationToAttach);
            }
            certificationCollectionNew = attachedCertificationCollectionNew;
            agency.setCertificationCollection(certificationCollectionNew);
            agency = em.merge(agency);
            for (Certification certificationCollectionNewCertification : certificationCollectionNew) {
                if (!certificationCollectionOld.contains(certificationCollectionNewCertification)) {
                    Agency oldAgencyIdOfCertificationCollectionNewCertification = certificationCollectionNewCertification.getAgencyId();
                    certificationCollectionNewCertification.setAgencyId(agency);
                    certificationCollectionNewCertification = em.merge(certificationCollectionNewCertification);
                    if (oldAgencyIdOfCertificationCollectionNewCertification != null && !oldAgencyIdOfCertificationCollectionNewCertification.equals(agency)) {
                        oldAgencyIdOfCertificationCollectionNewCertification.getCertificationCollection().remove(certificationCollectionNewCertification);
                        oldAgencyIdOfCertificationCollectionNewCertification = em.merge(oldAgencyIdOfCertificationCollectionNewCertification);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = agency.getIdAgency();
                if (findAgency(id) == null) {
                    throw new NonexistentEntityException("The agency with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agency agency;
            try {
                agency = em.getReference(Agency.class, id);
                agency.getIdAgency();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agency with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Certification> certificationCollectionOrphanCheck = agency.getCertificationCollection();
            for (Certification certificationCollectionOrphanCheckCertification : certificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agency (" + agency + ") cannot be destroyed since the Certification " + certificationCollectionOrphanCheckCertification + " in its certificationCollection field has a non-nullable agencyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(agency);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agency> findAgencyEntities() {
        return findAgencyEntities(true, -1, -1);
    }

    public List<Agency> findAgencyEntities(int maxResults, int firstResult) {
        return findAgencyEntities(false, maxResults, firstResult);
    }

    private List<Agency> findAgencyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agency.class));
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

    public Agency findAgency(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agency.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgencyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agency> rt = cq.from(Agency.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
