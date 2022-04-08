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
import repository.BuddiesDiving;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import repository.Planning;
import repository.UserSubscription;
import repository.UserCertification;
import repository.Equipment;
import repository.Divelog;
import repository.User;

public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getBuddiesDivingCollection() == null) {
            user.setBuddiesDivingCollection(new ArrayList<BuddiesDiving>());
        }
        if (user.getBuddiesDivingCollection1() == null) {
            user.setBuddiesDivingCollection1(new ArrayList<BuddiesDiving>());
        }
        if (user.getPlanningCollection() == null) {
            user.setPlanningCollection(new ArrayList<Planning>());
        }
        if (user.getUserSubscriptionCollection() == null) {
            user.setUserSubscriptionCollection(new ArrayList<UserSubscription>());
        }
        if (user.getUserSubscriptionCollection1() == null) {
            user.setUserSubscriptionCollection1(new ArrayList<UserSubscription>());
        }
        if (user.getUserCertificationCollection() == null) {
            user.setUserCertificationCollection(new ArrayList<UserCertification>());
        }
        if (user.getEquipmentCollection() == null) {
            user.setEquipmentCollection(new ArrayList<Equipment>());
        }
        if (user.getDivelogCollection() == null) {
            user.setDivelogCollection(new ArrayList<Divelog>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BuddiesDiving> attachedBuddiesDivingCollection = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollectionBuddiesDivingToAttach : user.getBuddiesDivingCollection()) {
                buddiesDivingCollectionBuddiesDivingToAttach = em.getReference(buddiesDivingCollectionBuddiesDivingToAttach.getClass(), buddiesDivingCollectionBuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollection.add(buddiesDivingCollectionBuddiesDivingToAttach);
            }
            user.setBuddiesDivingCollection(attachedBuddiesDivingCollection);
            Collection<BuddiesDiving> attachedBuddiesDivingCollection1 = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollection1BuddiesDivingToAttach : user.getBuddiesDivingCollection1()) {
                buddiesDivingCollection1BuddiesDivingToAttach = em.getReference(buddiesDivingCollection1BuddiesDivingToAttach.getClass(), buddiesDivingCollection1BuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollection1.add(buddiesDivingCollection1BuddiesDivingToAttach);
            }
            user.setBuddiesDivingCollection1(attachedBuddiesDivingCollection1);
            Collection<Planning> attachedPlanningCollection = new ArrayList<Planning>();
            for (Planning planningCollectionPlanningToAttach : user.getPlanningCollection()) {
                planningCollectionPlanningToAttach = em.getReference(planningCollectionPlanningToAttach.getClass(), planningCollectionPlanningToAttach.getIdPlanning());
                attachedPlanningCollection.add(planningCollectionPlanningToAttach);
            }
            user.setPlanningCollection(attachedPlanningCollection);
            Collection<UserSubscription> attachedUserSubscriptionCollection = new ArrayList<UserSubscription>();
            for (UserSubscription userSubscriptionCollectionUserSubscriptionToAttach : user.getUserSubscriptionCollection()) {
                userSubscriptionCollectionUserSubscriptionToAttach = em.getReference(userSubscriptionCollectionUserSubscriptionToAttach.getClass(), userSubscriptionCollectionUserSubscriptionToAttach.getIdSubscription());
                attachedUserSubscriptionCollection.add(userSubscriptionCollectionUserSubscriptionToAttach);
            }
            user.setUserSubscriptionCollection(attachedUserSubscriptionCollection);
            Collection<UserSubscription> attachedUserSubscriptionCollection1 = new ArrayList<UserSubscription>();
            for (UserSubscription userSubscriptionCollection1UserSubscriptionToAttach : user.getUserSubscriptionCollection1()) {
                userSubscriptionCollection1UserSubscriptionToAttach = em.getReference(userSubscriptionCollection1UserSubscriptionToAttach.getClass(), userSubscriptionCollection1UserSubscriptionToAttach.getIdSubscription());
                attachedUserSubscriptionCollection1.add(userSubscriptionCollection1UserSubscriptionToAttach);
            }
            user.setUserSubscriptionCollection1(attachedUserSubscriptionCollection1);
            Collection<UserCertification> attachedUserCertificationCollection = new ArrayList<UserCertification>();
            for (UserCertification userCertificationCollectionUserCertificationToAttach : user.getUserCertificationCollection()) {
                userCertificationCollectionUserCertificationToAttach = em.getReference(userCertificationCollectionUserCertificationToAttach.getClass(), userCertificationCollectionUserCertificationToAttach.getIdUserCertification());
                attachedUserCertificationCollection.add(userCertificationCollectionUserCertificationToAttach);
            }
            user.setUserCertificationCollection(attachedUserCertificationCollection);
            Collection<Equipment> attachedEquipmentCollection = new ArrayList<Equipment>();
            for (Equipment equipmentCollectionEquipmentToAttach : user.getEquipmentCollection()) {
                equipmentCollectionEquipmentToAttach = em.getReference(equipmentCollectionEquipmentToAttach.getClass(), equipmentCollectionEquipmentToAttach.getIdEquipment());
                attachedEquipmentCollection.add(equipmentCollectionEquipmentToAttach);
            }
            user.setEquipmentCollection(attachedEquipmentCollection);
            Collection<Divelog> attachedDivelogCollection = new ArrayList<Divelog>();
            for (Divelog divelogCollectionDivelogToAttach : user.getDivelogCollection()) {
                divelogCollectionDivelogToAttach = em.getReference(divelogCollectionDivelogToAttach.getClass(), divelogCollectionDivelogToAttach.getIdDivelog());
                attachedDivelogCollection.add(divelogCollectionDivelogToAttach);
            }
            user.setDivelogCollection(attachedDivelogCollection);
            em.persist(user);
            for (BuddiesDiving buddiesDivingCollectionBuddiesDiving : user.getBuddiesDivingCollection()) {
                User oldBuddyIdOfBuddiesDivingCollectionBuddiesDiving = buddiesDivingCollectionBuddiesDiving.getBuddyId();
                buddiesDivingCollectionBuddiesDiving.setBuddyId(user);
                buddiesDivingCollectionBuddiesDiving = em.merge(buddiesDivingCollectionBuddiesDiving);
                if (oldBuddyIdOfBuddiesDivingCollectionBuddiesDiving != null) {
                    oldBuddyIdOfBuddiesDivingCollectionBuddiesDiving.getBuddiesDivingCollection().remove(buddiesDivingCollectionBuddiesDiving);
                    oldBuddyIdOfBuddiesDivingCollectionBuddiesDiving = em.merge(oldBuddyIdOfBuddiesDivingCollectionBuddiesDiving);
                }
            }
            for (BuddiesDiving buddiesDivingCollection1BuddiesDiving : user.getBuddiesDivingCollection1()) {
                User oldUserIdOfBuddiesDivingCollection1BuddiesDiving = buddiesDivingCollection1BuddiesDiving.getUserId();
                buddiesDivingCollection1BuddiesDiving.setUserId(user);
                buddiesDivingCollection1BuddiesDiving = em.merge(buddiesDivingCollection1BuddiesDiving);
                if (oldUserIdOfBuddiesDivingCollection1BuddiesDiving != null) {
                    oldUserIdOfBuddiesDivingCollection1BuddiesDiving.getBuddiesDivingCollection1().remove(buddiesDivingCollection1BuddiesDiving);
                    oldUserIdOfBuddiesDivingCollection1BuddiesDiving = em.merge(oldUserIdOfBuddiesDivingCollection1BuddiesDiving);
                }
            }
            for (Planning planningCollectionPlanning : user.getPlanningCollection()) {
                User oldUserIdOfPlanningCollectionPlanning = planningCollectionPlanning.getUserId();
                planningCollectionPlanning.setUserId(user);
                planningCollectionPlanning = em.merge(planningCollectionPlanning);
                if (oldUserIdOfPlanningCollectionPlanning != null) {
                    oldUserIdOfPlanningCollectionPlanning.getPlanningCollection().remove(planningCollectionPlanning);
                    oldUserIdOfPlanningCollectionPlanning = em.merge(oldUserIdOfPlanningCollectionPlanning);
                }
            }
            for (UserSubscription userSubscriptionCollectionUserSubscription : user.getUserSubscriptionCollection()) {
                User oldFollowerUserIdOfUserSubscriptionCollectionUserSubscription = userSubscriptionCollectionUserSubscription.getFollowerUserId();
                userSubscriptionCollectionUserSubscription.setFollowerUserId(user);
                userSubscriptionCollectionUserSubscription = em.merge(userSubscriptionCollectionUserSubscription);
                if (oldFollowerUserIdOfUserSubscriptionCollectionUserSubscription != null) {
                    oldFollowerUserIdOfUserSubscriptionCollectionUserSubscription.getUserSubscriptionCollection().remove(userSubscriptionCollectionUserSubscription);
                    oldFollowerUserIdOfUserSubscriptionCollectionUserSubscription = em.merge(oldFollowerUserIdOfUserSubscriptionCollectionUserSubscription);
                }
            }
            for (UserSubscription userSubscriptionCollection1UserSubscription : user.getUserSubscriptionCollection1()) {
                User oldLiderUserIdOfUserSubscriptionCollection1UserSubscription = userSubscriptionCollection1UserSubscription.getLiderUserId();
                userSubscriptionCollection1UserSubscription.setLiderUserId(user);
                userSubscriptionCollection1UserSubscription = em.merge(userSubscriptionCollection1UserSubscription);
                if (oldLiderUserIdOfUserSubscriptionCollection1UserSubscription != null) {
                    oldLiderUserIdOfUserSubscriptionCollection1UserSubscription.getUserSubscriptionCollection1().remove(userSubscriptionCollection1UserSubscription);
                    oldLiderUserIdOfUserSubscriptionCollection1UserSubscription = em.merge(oldLiderUserIdOfUserSubscriptionCollection1UserSubscription);
                }
            }
            for (UserCertification userCertificationCollectionUserCertification : user.getUserCertificationCollection()) {
                User oldUserIdOfUserCertificationCollectionUserCertification = userCertificationCollectionUserCertification.getUserId();
                userCertificationCollectionUserCertification.setUserId(user);
                userCertificationCollectionUserCertification = em.merge(userCertificationCollectionUserCertification);
                if (oldUserIdOfUserCertificationCollectionUserCertification != null) {
                    oldUserIdOfUserCertificationCollectionUserCertification.getUserCertificationCollection().remove(userCertificationCollectionUserCertification);
                    oldUserIdOfUserCertificationCollectionUserCertification = em.merge(oldUserIdOfUserCertificationCollectionUserCertification);
                }
            }
            for (Equipment equipmentCollectionEquipment : user.getEquipmentCollection()) {
                User oldUserIdOfEquipmentCollectionEquipment = equipmentCollectionEquipment.getUserId();
                equipmentCollectionEquipment.setUserId(user);
                equipmentCollectionEquipment = em.merge(equipmentCollectionEquipment);
                if (oldUserIdOfEquipmentCollectionEquipment != null) {
                    oldUserIdOfEquipmentCollectionEquipment.getEquipmentCollection().remove(equipmentCollectionEquipment);
                    oldUserIdOfEquipmentCollectionEquipment = em.merge(oldUserIdOfEquipmentCollectionEquipment);
                }
            }
            for (Divelog divelogCollectionDivelog : user.getDivelogCollection()) {
                User oldUserIdOfDivelogCollectionDivelog = divelogCollectionDivelog.getUserId();
                divelogCollectionDivelog.setUserId(user);
                divelogCollectionDivelog = em.merge(divelogCollectionDivelog);
                if (oldUserIdOfDivelogCollectionDivelog != null) {
                    oldUserIdOfDivelogCollectionDivelog.getDivelogCollection().remove(divelogCollectionDivelog);
                    oldUserIdOfDivelogCollectionDivelog = em.merge(oldUserIdOfDivelogCollectionDivelog);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getIdUser());
            Collection<BuddiesDiving> buddiesDivingCollectionOld = persistentUser.getBuddiesDivingCollection();
            Collection<BuddiesDiving> buddiesDivingCollectionNew = user.getBuddiesDivingCollection();
            Collection<BuddiesDiving> buddiesDivingCollection1Old = persistentUser.getBuddiesDivingCollection1();
            Collection<BuddiesDiving> buddiesDivingCollection1New = user.getBuddiesDivingCollection1();
            Collection<Planning> planningCollectionOld = persistentUser.getPlanningCollection();
            Collection<Planning> planningCollectionNew = user.getPlanningCollection();
            Collection<UserSubscription> userSubscriptionCollectionOld = persistentUser.getUserSubscriptionCollection();
            Collection<UserSubscription> userSubscriptionCollectionNew = user.getUserSubscriptionCollection();
            Collection<UserSubscription> userSubscriptionCollection1Old = persistentUser.getUserSubscriptionCollection1();
            Collection<UserSubscription> userSubscriptionCollection1New = user.getUserSubscriptionCollection1();
            Collection<UserCertification> userCertificationCollectionOld = persistentUser.getUserCertificationCollection();
            Collection<UserCertification> userCertificationCollectionNew = user.getUserCertificationCollection();
            Collection<Equipment> equipmentCollectionOld = persistentUser.getEquipmentCollection();
            Collection<Equipment> equipmentCollectionNew = user.getEquipmentCollection();
            Collection<Divelog> divelogCollectionOld = persistentUser.getDivelogCollection();
            Collection<Divelog> divelogCollectionNew = user.getDivelogCollection();
            List<String> illegalOrphanMessages = null;
            for (BuddiesDiving buddiesDivingCollectionOldBuddiesDiving : buddiesDivingCollectionOld) {
                if (!buddiesDivingCollectionNew.contains(buddiesDivingCollectionOldBuddiesDiving)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BuddiesDiving " + buddiesDivingCollectionOldBuddiesDiving + " since its buddyId field is not nullable.");
                }
            }
            for (BuddiesDiving buddiesDivingCollection1OldBuddiesDiving : buddiesDivingCollection1Old) {
                if (!buddiesDivingCollection1New.contains(buddiesDivingCollection1OldBuddiesDiving)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BuddiesDiving " + buddiesDivingCollection1OldBuddiesDiving + " since its userId field is not nullable.");
                }
            }
            for (Planning planningCollectionOldPlanning : planningCollectionOld) {
                if (!planningCollectionNew.contains(planningCollectionOldPlanning)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Planning " + planningCollectionOldPlanning + " since its userId field is not nullable.");
                }
            }
            for (UserSubscription userSubscriptionCollectionOldUserSubscription : userSubscriptionCollectionOld) {
                if (!userSubscriptionCollectionNew.contains(userSubscriptionCollectionOldUserSubscription)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserSubscription " + userSubscriptionCollectionOldUserSubscription + " since its followerUserId field is not nullable.");
                }
            }
            for (UserSubscription userSubscriptionCollection1OldUserSubscription : userSubscriptionCollection1Old) {
                if (!userSubscriptionCollection1New.contains(userSubscriptionCollection1OldUserSubscription)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserSubscription " + userSubscriptionCollection1OldUserSubscription + " since its liderUserId field is not nullable.");
                }
            }
            for (UserCertification userCertificationCollectionOldUserCertification : userCertificationCollectionOld) {
                if (!userCertificationCollectionNew.contains(userCertificationCollectionOldUserCertification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserCertification " + userCertificationCollectionOldUserCertification + " since its userId field is not nullable.");
                }
            }
            for (Equipment equipmentCollectionOldEquipment : equipmentCollectionOld) {
                if (!equipmentCollectionNew.contains(equipmentCollectionOldEquipment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipment " + equipmentCollectionOldEquipment + " since its userId field is not nullable.");
                }
            }
            for (Divelog divelogCollectionOldDivelog : divelogCollectionOld) {
                if (!divelogCollectionNew.contains(divelogCollectionOldDivelog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Divelog " + divelogCollectionOldDivelog + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<BuddiesDiving> attachedBuddiesDivingCollectionNew = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollectionNewBuddiesDivingToAttach : buddiesDivingCollectionNew) {
                buddiesDivingCollectionNewBuddiesDivingToAttach = em.getReference(buddiesDivingCollectionNewBuddiesDivingToAttach.getClass(), buddiesDivingCollectionNewBuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollectionNew.add(buddiesDivingCollectionNewBuddiesDivingToAttach);
            }
            buddiesDivingCollectionNew = attachedBuddiesDivingCollectionNew;
            user.setBuddiesDivingCollection(buddiesDivingCollectionNew);
            Collection<BuddiesDiving> attachedBuddiesDivingCollection1New = new ArrayList<BuddiesDiving>();
            for (BuddiesDiving buddiesDivingCollection1NewBuddiesDivingToAttach : buddiesDivingCollection1New) {
                buddiesDivingCollection1NewBuddiesDivingToAttach = em.getReference(buddiesDivingCollection1NewBuddiesDivingToAttach.getClass(), buddiesDivingCollection1NewBuddiesDivingToAttach.getIdRelationBuddy());
                attachedBuddiesDivingCollection1New.add(buddiesDivingCollection1NewBuddiesDivingToAttach);
            }
            buddiesDivingCollection1New = attachedBuddiesDivingCollection1New;
            user.setBuddiesDivingCollection1(buddiesDivingCollection1New);
            Collection<Planning> attachedPlanningCollectionNew = new ArrayList<Planning>();
            for (Planning planningCollectionNewPlanningToAttach : planningCollectionNew) {
                planningCollectionNewPlanningToAttach = em.getReference(planningCollectionNewPlanningToAttach.getClass(), planningCollectionNewPlanningToAttach.getIdPlanning());
                attachedPlanningCollectionNew.add(planningCollectionNewPlanningToAttach);
            }
            planningCollectionNew = attachedPlanningCollectionNew;
            user.setPlanningCollection(planningCollectionNew);
            Collection<UserSubscription> attachedUserSubscriptionCollectionNew = new ArrayList<UserSubscription>();
            for (UserSubscription userSubscriptionCollectionNewUserSubscriptionToAttach : userSubscriptionCollectionNew) {
                userSubscriptionCollectionNewUserSubscriptionToAttach = em.getReference(userSubscriptionCollectionNewUserSubscriptionToAttach.getClass(), userSubscriptionCollectionNewUserSubscriptionToAttach.getIdSubscription());
                attachedUserSubscriptionCollectionNew.add(userSubscriptionCollectionNewUserSubscriptionToAttach);
            }
            userSubscriptionCollectionNew = attachedUserSubscriptionCollectionNew;
            user.setUserSubscriptionCollection(userSubscriptionCollectionNew);
            Collection<UserSubscription> attachedUserSubscriptionCollection1New = new ArrayList<UserSubscription>();
            for (UserSubscription userSubscriptionCollection1NewUserSubscriptionToAttach : userSubscriptionCollection1New) {
                userSubscriptionCollection1NewUserSubscriptionToAttach = em.getReference(userSubscriptionCollection1NewUserSubscriptionToAttach.getClass(), userSubscriptionCollection1NewUserSubscriptionToAttach.getIdSubscription());
                attachedUserSubscriptionCollection1New.add(userSubscriptionCollection1NewUserSubscriptionToAttach);
            }
            userSubscriptionCollection1New = attachedUserSubscriptionCollection1New;
            user.setUserSubscriptionCollection1(userSubscriptionCollection1New);
            Collection<UserCertification> attachedUserCertificationCollectionNew = new ArrayList<UserCertification>();
            for (UserCertification userCertificationCollectionNewUserCertificationToAttach : userCertificationCollectionNew) {
                userCertificationCollectionNewUserCertificationToAttach = em.getReference(userCertificationCollectionNewUserCertificationToAttach.getClass(), userCertificationCollectionNewUserCertificationToAttach.getIdUserCertification());
                attachedUserCertificationCollectionNew.add(userCertificationCollectionNewUserCertificationToAttach);
            }
            userCertificationCollectionNew = attachedUserCertificationCollectionNew;
            user.setUserCertificationCollection(userCertificationCollectionNew);
            Collection<Equipment> attachedEquipmentCollectionNew = new ArrayList<Equipment>();
            for (Equipment equipmentCollectionNewEquipmentToAttach : equipmentCollectionNew) {
                equipmentCollectionNewEquipmentToAttach = em.getReference(equipmentCollectionNewEquipmentToAttach.getClass(), equipmentCollectionNewEquipmentToAttach.getIdEquipment());
                attachedEquipmentCollectionNew.add(equipmentCollectionNewEquipmentToAttach);
            }
            equipmentCollectionNew = attachedEquipmentCollectionNew;
            user.setEquipmentCollection(equipmentCollectionNew);
            Collection<Divelog> attachedDivelogCollectionNew = new ArrayList<Divelog>();
            for (Divelog divelogCollectionNewDivelogToAttach : divelogCollectionNew) {
                divelogCollectionNewDivelogToAttach = em.getReference(divelogCollectionNewDivelogToAttach.getClass(), divelogCollectionNewDivelogToAttach.getIdDivelog());
                attachedDivelogCollectionNew.add(divelogCollectionNewDivelogToAttach);
            }
            divelogCollectionNew = attachedDivelogCollectionNew;
            user.setDivelogCollection(divelogCollectionNew);
            user = em.merge(user);
            for (BuddiesDiving buddiesDivingCollectionNewBuddiesDiving : buddiesDivingCollectionNew) {
                if (!buddiesDivingCollectionOld.contains(buddiesDivingCollectionNewBuddiesDiving)) {
                    User oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving = buddiesDivingCollectionNewBuddiesDiving.getBuddyId();
                    buddiesDivingCollectionNewBuddiesDiving.setBuddyId(user);
                    buddiesDivingCollectionNewBuddiesDiving = em.merge(buddiesDivingCollectionNewBuddiesDiving);
                    if (oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving != null && !oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving.equals(user)) {
                        oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving.getBuddiesDivingCollection().remove(buddiesDivingCollectionNewBuddiesDiving);
                        oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving = em.merge(oldBuddyIdOfBuddiesDivingCollectionNewBuddiesDiving);
                    }
                }
            }
            for (BuddiesDiving buddiesDivingCollection1NewBuddiesDiving : buddiesDivingCollection1New) {
                if (!buddiesDivingCollection1Old.contains(buddiesDivingCollection1NewBuddiesDiving)) {
                    User oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving = buddiesDivingCollection1NewBuddiesDiving.getUserId();
                    buddiesDivingCollection1NewBuddiesDiving.setUserId(user);
                    buddiesDivingCollection1NewBuddiesDiving = em.merge(buddiesDivingCollection1NewBuddiesDiving);
                    if (oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving != null && !oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving.equals(user)) {
                        oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving.getBuddiesDivingCollection1().remove(buddiesDivingCollection1NewBuddiesDiving);
                        oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving = em.merge(oldUserIdOfBuddiesDivingCollection1NewBuddiesDiving);
                    }
                }
            }
            for (Planning planningCollectionNewPlanning : planningCollectionNew) {
                if (!planningCollectionOld.contains(planningCollectionNewPlanning)) {
                    User oldUserIdOfPlanningCollectionNewPlanning = planningCollectionNewPlanning.getUserId();
                    planningCollectionNewPlanning.setUserId(user);
                    planningCollectionNewPlanning = em.merge(planningCollectionNewPlanning);
                    if (oldUserIdOfPlanningCollectionNewPlanning != null && !oldUserIdOfPlanningCollectionNewPlanning.equals(user)) {
                        oldUserIdOfPlanningCollectionNewPlanning.getPlanningCollection().remove(planningCollectionNewPlanning);
                        oldUserIdOfPlanningCollectionNewPlanning = em.merge(oldUserIdOfPlanningCollectionNewPlanning);
                    }
                }
            }
            for (UserSubscription userSubscriptionCollectionNewUserSubscription : userSubscriptionCollectionNew) {
                if (!userSubscriptionCollectionOld.contains(userSubscriptionCollectionNewUserSubscription)) {
                    User oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription = userSubscriptionCollectionNewUserSubscription.getFollowerUserId();
                    userSubscriptionCollectionNewUserSubscription.setFollowerUserId(user);
                    userSubscriptionCollectionNewUserSubscription = em.merge(userSubscriptionCollectionNewUserSubscription);
                    if (oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription != null && !oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription.equals(user)) {
                        oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription.getUserSubscriptionCollection().remove(userSubscriptionCollectionNewUserSubscription);
                        oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription = em.merge(oldFollowerUserIdOfUserSubscriptionCollectionNewUserSubscription);
                    }
                }
            }
            for (UserSubscription userSubscriptionCollection1NewUserSubscription : userSubscriptionCollection1New) {
                if (!userSubscriptionCollection1Old.contains(userSubscriptionCollection1NewUserSubscription)) {
                    User oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription = userSubscriptionCollection1NewUserSubscription.getLiderUserId();
                    userSubscriptionCollection1NewUserSubscription.setLiderUserId(user);
                    userSubscriptionCollection1NewUserSubscription = em.merge(userSubscriptionCollection1NewUserSubscription);
                    if (oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription != null && !oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription.equals(user)) {
                        oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription.getUserSubscriptionCollection1().remove(userSubscriptionCollection1NewUserSubscription);
                        oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription = em.merge(oldLiderUserIdOfUserSubscriptionCollection1NewUserSubscription);
                    }
                }
            }
            for (UserCertification userCertificationCollectionNewUserCertification : userCertificationCollectionNew) {
                if (!userCertificationCollectionOld.contains(userCertificationCollectionNewUserCertification)) {
                    User oldUserIdOfUserCertificationCollectionNewUserCertification = userCertificationCollectionNewUserCertification.getUserId();
                    userCertificationCollectionNewUserCertification.setUserId(user);
                    userCertificationCollectionNewUserCertification = em.merge(userCertificationCollectionNewUserCertification);
                    if (oldUserIdOfUserCertificationCollectionNewUserCertification != null && !oldUserIdOfUserCertificationCollectionNewUserCertification.equals(user)) {
                        oldUserIdOfUserCertificationCollectionNewUserCertification.getUserCertificationCollection().remove(userCertificationCollectionNewUserCertification);
                        oldUserIdOfUserCertificationCollectionNewUserCertification = em.merge(oldUserIdOfUserCertificationCollectionNewUserCertification);
                    }
                }
            }
            for (Equipment equipmentCollectionNewEquipment : equipmentCollectionNew) {
                if (!equipmentCollectionOld.contains(equipmentCollectionNewEquipment)) {
                    User oldUserIdOfEquipmentCollectionNewEquipment = equipmentCollectionNewEquipment.getUserId();
                    equipmentCollectionNewEquipment.setUserId(user);
                    equipmentCollectionNewEquipment = em.merge(equipmentCollectionNewEquipment);
                    if (oldUserIdOfEquipmentCollectionNewEquipment != null && !oldUserIdOfEquipmentCollectionNewEquipment.equals(user)) {
                        oldUserIdOfEquipmentCollectionNewEquipment.getEquipmentCollection().remove(equipmentCollectionNewEquipment);
                        oldUserIdOfEquipmentCollectionNewEquipment = em.merge(oldUserIdOfEquipmentCollectionNewEquipment);
                    }
                }
            }
            for (Divelog divelogCollectionNewDivelog : divelogCollectionNew) {
                if (!divelogCollectionOld.contains(divelogCollectionNewDivelog)) {
                    User oldUserIdOfDivelogCollectionNewDivelog = divelogCollectionNewDivelog.getUserId();
                    divelogCollectionNewDivelog.setUserId(user);
                    divelogCollectionNewDivelog = em.merge(divelogCollectionNewDivelog);
                    if (oldUserIdOfDivelogCollectionNewDivelog != null && !oldUserIdOfDivelogCollectionNewDivelog.equals(user)) {
                        oldUserIdOfDivelogCollectionNewDivelog.getDivelogCollection().remove(divelogCollectionNewDivelog);
                        oldUserIdOfDivelogCollectionNewDivelog = em.merge(oldUserIdOfDivelogCollectionNewDivelog);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIdUser();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BuddiesDiving> buddiesDivingCollectionOrphanCheck = user.getBuddiesDivingCollection();
            for (BuddiesDiving buddiesDivingCollectionOrphanCheckBuddiesDiving : buddiesDivingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the BuddiesDiving " + buddiesDivingCollectionOrphanCheckBuddiesDiving + " in its buddiesDivingCollection field has a non-nullable buddyId field.");
            }
            Collection<BuddiesDiving> buddiesDivingCollection1OrphanCheck = user.getBuddiesDivingCollection1();
            for (BuddiesDiving buddiesDivingCollection1OrphanCheckBuddiesDiving : buddiesDivingCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the BuddiesDiving " + buddiesDivingCollection1OrphanCheckBuddiesDiving + " in its buddiesDivingCollection1 field has a non-nullable userId field.");
            }
            Collection<Planning> planningCollectionOrphanCheck = user.getPlanningCollection();
            for (Planning planningCollectionOrphanCheckPlanning : planningCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Planning " + planningCollectionOrphanCheckPlanning + " in its planningCollection field has a non-nullable userId field.");
            }
            Collection<UserSubscription> userSubscriptionCollectionOrphanCheck = user.getUserSubscriptionCollection();
            for (UserSubscription userSubscriptionCollectionOrphanCheckUserSubscription : userSubscriptionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserSubscription " + userSubscriptionCollectionOrphanCheckUserSubscription + " in its userSubscriptionCollection field has a non-nullable followerUserId field.");
            }
            Collection<UserSubscription> userSubscriptionCollection1OrphanCheck = user.getUserSubscriptionCollection1();
            for (UserSubscription userSubscriptionCollection1OrphanCheckUserSubscription : userSubscriptionCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserSubscription " + userSubscriptionCollection1OrphanCheckUserSubscription + " in its userSubscriptionCollection1 field has a non-nullable liderUserId field.");
            }
            Collection<UserCertification> userCertificationCollectionOrphanCheck = user.getUserCertificationCollection();
            for (UserCertification userCertificationCollectionOrphanCheckUserCertification : userCertificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserCertification " + userCertificationCollectionOrphanCheckUserCertification + " in its userCertificationCollection field has a non-nullable userId field.");
            }
            Collection<Equipment> equipmentCollectionOrphanCheck = user.getEquipmentCollection();
            for (Equipment equipmentCollectionOrphanCheckEquipment : equipmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Equipment " + equipmentCollectionOrphanCheckEquipment + " in its equipmentCollection field has a non-nullable userId field.");
            }
            Collection<Divelog> divelogCollectionOrphanCheck = user.getDivelogCollection();
            for (Divelog divelogCollectionOrphanCheckDivelog : divelogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Divelog " + divelogCollectionOrphanCheckDivelog + " in its divelogCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
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
    
    // Devuelve la ID del usuario segun el nickName que se le pase
    public int getUserId(String nickName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootUser = cq.from(User.class);

            // SQL : SELECT * FROM calypso.usuario WHERE nickname = 'nickName';
            Predicate userNick = cb.equal(rootUser.get("nickname"),nickName);
            cq.select(rootUser).where(userNick);
         
            User user = em.createQuery(cq).getSingleResult();
            
            return user.getIdUser();
        } finally {
            em.close();
        }
    }
    
    // Devuelve el Usuari segun el nickName que se le pase
    public User getUserFromNickName(String nickName) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rt = cq.from(User.class);

            // SQL : SELECT * FROM calypso.usuario WHERE nickname = 'nickName';
            Predicate userNick = cb.equal(rt.get("nickname"),nickName);
            cq.select(rt).where(userNick);
         
            User user = em.createQuery(cq).getSingleResult();
            
            return user;
        } finally {
            em.close();
        }
    }
    
    // Devuelve el listado de Usuarios con Perfil Publico
    public List<User> getPublicUsers() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rt = cq.from(User.class);

            Predicate userPublic = cb.equal(rt.get("publicProfile"),1);
            cq.select(rt).where(userPublic);
         
            List<User> list = em.createQuery(cq).getResultList();
            
            return list;
        } finally {
            em.close();
        }
    }
    

}
