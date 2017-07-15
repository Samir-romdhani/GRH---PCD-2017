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
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Departement;

/**
 *
 * @author user
 */
public class DepartementDaoImp implements Serializable, IDepartementDao {

        private EntityManagerFactory emf;

    public DepartementDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Departement departement) {
        if (departement.getEmployeCollection() == null) {
            departement.setEmployeCollection(new ArrayList<Employe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employe> attachedEmployeCollection = new ArrayList<Employe>();
            for (Employe employeCollectionEmployeToAttach : departement.getEmployeCollection()) {
                employeCollectionEmployeToAttach = em.getReference(employeCollectionEmployeToAttach.getClass(), employeCollectionEmployeToAttach.getIdEmploye());
                attachedEmployeCollection.add(employeCollectionEmployeToAttach);
            }
            departement.setEmployeCollection(attachedEmployeCollection);
            em.persist(departement);
            for (Employe employeCollectionEmploye : departement.getEmployeCollection()) {
                Departement oldIdDepartementOfEmployeCollectionEmploye = employeCollectionEmploye.getIdDepartement();
                employeCollectionEmploye.setIdDepartement(departement);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
                if (oldIdDepartementOfEmployeCollectionEmploye != null) {
                    oldIdDepartementOfEmployeCollectionEmploye.getEmployeCollection().remove(employeCollectionEmploye);
                    oldIdDepartementOfEmployeCollectionEmploye = em.merge(oldIdDepartementOfEmployeCollectionEmploye);
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
    public void edit(Departement departement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departement persistentDepartement = em.find(Departement.class, departement.getIdDepartement());
            Collection<Employe> employeCollectionOld = persistentDepartement.getEmployeCollection();
            Collection<Employe> employeCollectionNew = departement.getEmployeCollection();
            Collection<Employe> attachedEmployeCollectionNew = new ArrayList<Employe>();
            for (Employe employeCollectionNewEmployeToAttach : employeCollectionNew) {
                employeCollectionNewEmployeToAttach = em.getReference(employeCollectionNewEmployeToAttach.getClass(), employeCollectionNewEmployeToAttach.getIdEmploye());
                attachedEmployeCollectionNew.add(employeCollectionNewEmployeToAttach);
            }
            employeCollectionNew = attachedEmployeCollectionNew;
            departement.setEmployeCollection(employeCollectionNew);
            departement = em.merge(departement);
            for (Employe employeCollectionOldEmploye : employeCollectionOld) {
                if (!employeCollectionNew.contains(employeCollectionOldEmploye)) {
                    employeCollectionOldEmploye.setIdDepartement(null);
                    employeCollectionOldEmploye = em.merge(employeCollectionOldEmploye);
                }
            }
            for (Employe employeCollectionNewEmploye : employeCollectionNew) {
                if (!employeCollectionOld.contains(employeCollectionNewEmploye)) {
                    Departement oldIdDepartementOfEmployeCollectionNewEmploye = employeCollectionNewEmploye.getIdDepartement();
                    employeCollectionNewEmploye.setIdDepartement(departement);
                    employeCollectionNewEmploye = em.merge(employeCollectionNewEmploye);
                    if (oldIdDepartementOfEmployeCollectionNewEmploye != null && !oldIdDepartementOfEmployeCollectionNewEmploye.equals(departement)) {
                        oldIdDepartementOfEmployeCollectionNewEmploye.getEmployeCollection().remove(employeCollectionNewEmploye);
                        oldIdDepartementOfEmployeCollectionNewEmploye = em.merge(oldIdDepartementOfEmployeCollectionNewEmploye);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departement.getIdDepartement();
                if (findDepartement(id) == null) {
                    throw new NonexistentEntityException("The departement with id " + id + " no longer exists.");
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
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departement departement;
            try {
                departement = em.getReference(Departement.class, id);
                departement.getIdDepartement();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departement with id " + id + " no longer exists.", enfe);
            }
            Collection<Employe> employeCollection = departement.getEmployeCollection();
            for (Employe employeCollectionEmploye : employeCollection) {
                employeCollectionEmploye.setIdDepartement(null);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
            }
            em.remove(departement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Departement> findDepartementEntities() {
        return findDepartementEntities(true, -1, -1);
    }

    @Override
    public List<Departement> findDepartementEntities(int maxResults, int firstResult) {
        return findDepartementEntities(false, maxResults, firstResult);
    }

    private List<Departement> findDepartementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departement.class));
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
    public Departement findDepartement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departement.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getDepartementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departement> rt = cq.from(Departement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
