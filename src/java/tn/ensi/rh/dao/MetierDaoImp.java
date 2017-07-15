/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.ensi.rh.entities.Employe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Metier;

/**
 *
 * @author user
 */
public class MetierDaoImp implements Serializable, IMetierDao {

         private EntityManagerFactory emf;

    public MetierDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Metier metier) {
        if (metier.getEmployeCollection() == null) {
            metier.setEmployeCollection(new ArrayList<Employe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employe> attachedEmployeCollection = new ArrayList<Employe>();
            for (Employe employeCollectionEmployeToAttach : metier.getEmployeCollection()) {
                employeCollectionEmployeToAttach = em.getReference(employeCollectionEmployeToAttach.getClass(), employeCollectionEmployeToAttach.getIdEmploye());
                attachedEmployeCollection.add(employeCollectionEmployeToAttach);
            }
            metier.setEmployeCollection(attachedEmployeCollection);
            em.persist(metier);
            for (Employe employeCollectionEmploye : metier.getEmployeCollection()) {
                Metier oldIdentificationDuPosteOfEmployeCollectionEmploye = employeCollectionEmploye.getIdentificationDuPoste();
                employeCollectionEmploye.setIdentificationDuPoste(metier);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
                if (oldIdentificationDuPosteOfEmployeCollectionEmploye != null) {
                    oldIdentificationDuPosteOfEmployeCollectionEmploye.getEmployeCollection().remove(employeCollectionEmploye);
                    oldIdentificationDuPosteOfEmployeCollectionEmploye = em.merge(oldIdentificationDuPosteOfEmployeCollectionEmploye);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Metier metier) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Metier persistentMetier = em.find(Metier.class, metier.getIdentificationDuPoste());
            Collection<Employe> employeCollectionOld = persistentMetier.getEmployeCollection();
            Collection<Employe> employeCollectionNew = metier.getEmployeCollection();
            List<String> illegalOrphanMessages = null;
            for (Employe employeCollectionOldEmploye : employeCollectionOld) {
                if (!employeCollectionNew.contains(employeCollectionOldEmploye)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employe " + employeCollectionOldEmploye + " since its identificationDuPoste field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Employe> attachedEmployeCollectionNew = new ArrayList<Employe>();
            for (Employe employeCollectionNewEmployeToAttach : employeCollectionNew) {
                employeCollectionNewEmployeToAttach = em.getReference(employeCollectionNewEmployeToAttach.getClass(), employeCollectionNewEmployeToAttach.getIdEmploye());
                attachedEmployeCollectionNew.add(employeCollectionNewEmployeToAttach);
            }
            employeCollectionNew = attachedEmployeCollectionNew;
            metier.setEmployeCollection(employeCollectionNew);
            metier = em.merge(metier);
            for (Employe employeCollectionNewEmploye : employeCollectionNew) {
                if (!employeCollectionOld.contains(employeCollectionNewEmploye)) {
                    Metier oldIdentificationDuPosteOfEmployeCollectionNewEmploye = employeCollectionNewEmploye.getIdentificationDuPoste();
                    employeCollectionNewEmploye.setIdentificationDuPoste(metier);
                    employeCollectionNewEmploye = em.merge(employeCollectionNewEmploye);
                    if (oldIdentificationDuPosteOfEmployeCollectionNewEmploye != null && !oldIdentificationDuPosteOfEmployeCollectionNewEmploye.equals(metier)) {
                        oldIdentificationDuPosteOfEmployeCollectionNewEmploye.getEmployeCollection().remove(employeCollectionNewEmploye);
                        oldIdentificationDuPosteOfEmployeCollectionNewEmploye = em.merge(oldIdentificationDuPosteOfEmployeCollectionNewEmploye);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = metier.getIdentificationDuPoste();
                if (findMetier(id) == null) {
                    throw new NonexistentEntityException("The metier with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Metier metier;
            try {
                metier = em.getReference(Metier.class, id);
                metier.getIdentificationDuPoste();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The metier with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Employe> employeCollectionOrphanCheck = metier.getEmployeCollection();
            for (Employe employeCollectionOrphanCheckEmploye : employeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Metier (" + metier + ") cannot be destroyed since the Employe " + employeCollectionOrphanCheckEmploye + " in its employeCollection field has a non-nullable identificationDuPoste field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(metier);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Metier> findMetierEntities() {
        return findMetierEntities(true, -1, -1);
    }

    @Override
    public List<Metier> findMetierEntities(int maxResults, int firstResult) {
        return findMetierEntities(false, maxResults, firstResult);
    }

    private List<Metier> findMetierEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Metier.class));
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

    @Override
    public Metier findMetier(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Metier.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getMetierCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Metier> rt = cq.from(Metier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
