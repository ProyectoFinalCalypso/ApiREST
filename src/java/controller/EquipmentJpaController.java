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
import repository.Equipment;
import repository.User;

public class EquipmentJpaController implements Serializable {

    public EquipmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipment equipment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = equipment.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getIdUser());
                equipment.setUserId(userId);
            }
            em.persist(equipment);
            if (userId != null) {
                userId.getEquipmentCollection().add(equipment);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipment equipment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipment persistentEquipment = em.find(Equipment.class, equipment.getIdEquipment());
            User userIdOld = persistentEquipment.getUserId();
            User userIdNew = equipment.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getIdUser());
                equipment.setUserId(userIdNew);
            }
            equipment = em.merge(equipment);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getEquipmentCollection().remove(equipment);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getEquipmentCollection().add(equipment);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipment.getIdEquipment();
                if (findEquipment(id) == null) {
                    throw new NonexistentEntityException("The equipment with id " + id + " no longer exists.");
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
            Equipment equipment;
            try {
                equipment = em.getReference(Equipment.class, id);
                equipment.getIdEquipment();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipment with id " + id + " no longer exists.", enfe);
            }
            User userId = equipment.getUserId();
            if (userId != null) {
                userId.getEquipmentCollection().remove(equipment);
                userId = em.merge(userId);
            }
            em.remove(equipment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipment> findEquipmentEntities() {
        return findEquipmentEntities(true, -1, -1);
    }

    public List<Equipment> findEquipmentEntities(int maxResults, int firstResult) {
        return findEquipmentEntities(false, maxResults, firstResult);
    }

    private List<Equipment> findEquipmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipment.class));
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

    public Equipment findEquipment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipment.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipment> rt = cq.from(Equipment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
