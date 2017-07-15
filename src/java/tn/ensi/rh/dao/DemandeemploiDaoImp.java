/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Condidat;
import tn.ensi.rh.entities.Demandeemploi;

/**
 *
 * @author user
 */
public class DemandeemploiDaoImp implements Serializable, IDemandeemploiDao {

         private EntityManagerFactory emf;

    public DemandeemploiDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Demandeemploi demandeemploi) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Condidat id = demandeemploi.getId();
            if (id != null) {
                id = em.getReference(id.getClass(), id.getId());
                demandeemploi.setId(id);
            }
            em.persist(demandeemploi);
            if (id != null) {
                id.getDemandeemploiCollection().add(demandeemploi);
                id = em.merge(id);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Demandeemploi demandeemploi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Demandeemploi persistentDemandeemploi = em.find(Demandeemploi.class, demandeemploi.getIdDemandeemplois());
            Condidat idOld = persistentDemandeemploi.getId();
            Condidat idNew = demandeemploi.getId();
            if (idNew != null) {
                idNew = em.getReference(idNew.getClass(), idNew.getId());
                demandeemploi.setId(idNew);
            }
            demandeemploi = em.merge(demandeemploi);
            if (idOld != null && !idOld.equals(idNew)) {
                idOld.getDemandeemploiCollection().remove(demandeemploi);
                idOld = em.merge(idOld);
            }
            if (idNew != null && !idNew.equals(idOld)) {
                idNew.getDemandeemploiCollection().add(demandeemploi);
                idNew = em.merge(idNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = demandeemploi.getIdDemandeemplois();
                if (findDemandeemploi(id) == null) {
                    throw new NonexistentEntityException("The demandeemploi with id " + id + " no longer exists.");
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
            Demandeemploi demandeemploi;
            try {
                demandeemploi = em.getReference(Demandeemploi.class, id);
                demandeemploi.getIdDemandeemplois();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The demandeemploi with id " + id + " no longer exists.", enfe);
            }
            Condidat id2 = demandeemploi.getId();
            if (id2 != null) {
                id2.getDemandeemploiCollection().remove(demandeemploi);
                id2 = em.merge(id2);
            }
            em.remove(demandeemploi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Demandeemploi> findDemandeemploiEntities() {
        return findDemandeemploiEntities(true, -1, -1);
    }

    @Override
    public List<Demandeemploi> findDemandeemploiEntities(int maxResults, int firstResult) {
        return findDemandeemploiEntities(false, maxResults, firstResult);
    }

    private List<Demandeemploi> findDemandeemploiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Demandeemploi.class));
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
    public Demandeemploi findDemandeemploi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Demandeemploi.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getDemandeemploiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Demandeemploi> rt = cq.from(Demandeemploi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
