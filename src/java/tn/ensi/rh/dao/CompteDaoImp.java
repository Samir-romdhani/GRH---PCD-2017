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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.dao.exceptions.PreexistingEntityException;
import tn.ensi.rh.entities.Compte;

/**
 *
 * @author user
 */
public class CompteDaoImp implements Serializable, ICompteDao {

        private EntityManagerFactory emf;

    public CompteDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Compte compte) throws IllegalOrphanException, PreexistingEntityException, Exception {
        
        /*
        List<String> illegalOrphanMessages = null;
        Employe employeOrphanCheck = compte.getEmploye();
        if (employeOrphanCheck != null) {
            Compte oldCompteOfEmploye = employeOrphanCheck.getCompte();
            if (oldCompteOfEmploye != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Employe " + employeOrphanCheck + " already has an item of type Compte whose employe column cannot be null. Please make another selection for the employe field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }*/
        EntityManager em = null;
        try {
            em = getEntityManager();
            
            Employe employe  = new Employe(compte.getIdEmploye());
            
            compte.setEmploye(employe);
            em.getTransaction().begin();
            
             Employe emp= em.find(Employe.class, compte.getIdEmploye()) ;
            compte.setEmploye(emp);
            /*
            Employe employe = compte.getEmploye();
            if (employe != null) {
                employe = em.getReference(employe.getClass(), employe.getIdEmploye());
                compte.setEmploye(employe);
            }
            
            */
            em.persist(compte);
            /*
            if (employe != null) {
                employe.setCompte(compte);
                employe = em.merge(employe);
            }
            */
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompte(compte.getIdEmploye()) != null) {
                throw new PreexistingEntityException("Compte " + compte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Compte compte) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte persistentCompte = em.find(Compte.class, compte.getIdEmploye());
            Employe employeOld = persistentCompte.getEmploye();
            Employe employeNew = compte.getEmploye();
            List<String> illegalOrphanMessages = null;
            if (employeNew != null && !employeNew.equals(employeOld)) {
                Compte oldCompteOfEmploye = employeNew.getCompte();
                if (oldCompteOfEmploye != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Employe " + employeNew + " already has an item of type Compte whose employe column cannot be null. Please make another selection for the employe field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeNew != null) {
                employeNew = em.getReference(employeNew.getClass(), employeNew.getIdEmploye());
                compte.setEmploye(employeNew);
            }
            compte = em.merge(compte);
            if (employeOld != null && !employeOld.equals(employeNew)) {
                employeOld.setCompte(null);
                employeOld = em.merge(employeOld);
            }
            if (employeNew != null && !employeNew.equals(employeOld)) {
                employeNew.setCompte(compte);
                employeNew = em.merge(employeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = compte.getIdEmploye();
                if (findCompte(id) == null) {
                    throw new NonexistentEntityException("The compte with id " + id + " no longer exists.");
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
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte compte;
            try {
                compte = em.getReference(Compte.class, id);
                compte.getIdEmploye();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compte with id " + id + " no longer exists.", enfe);
            }
            Employe employe = compte.getEmploye();
            if (employe != null) {
                employe.setCompte(null);
                employe = em.merge(employe);
            }
            em.remove(compte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Compte> findCompteEntities() {
        return findCompteEntities(true, -1, -1);
    }

    @Override
    public List<Compte> findCompteEntities(int maxResults, int firstResult) {
        return findCompteEntities(false, maxResults, firstResult);
    }

    private List<Compte> findCompteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compte.class));
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
    public Compte findCompte(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compte.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getCompteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compte> rt = cq.from(Compte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
