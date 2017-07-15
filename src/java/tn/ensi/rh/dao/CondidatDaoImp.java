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
import tn.ensi.rh.entities.Demandeemploi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Condidat;

/**
 *
 * @author user
 */
public class CondidatDaoImp implements Serializable, ICondidatDao {

           private EntityManagerFactory emf;

    public CondidatDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Condidat condidat) {
        if (condidat.getDemandeemploiCollection() == null) {
            condidat.setDemandeemploiCollection(new ArrayList<Demandeemploi>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Demandeemploi> attachedDemandeemploiCollection = new ArrayList<Demandeemploi>();
            for (Demandeemploi demandeemploiCollectionDemandeemploiToAttach : condidat.getDemandeemploiCollection()) {
                demandeemploiCollectionDemandeemploiToAttach = em.getReference(demandeemploiCollectionDemandeemploiToAttach.getClass(), demandeemploiCollectionDemandeemploiToAttach.getIdDemandeemplois());
                attachedDemandeemploiCollection.add(demandeemploiCollectionDemandeemploiToAttach);
            }
            condidat.setDemandeemploiCollection(attachedDemandeemploiCollection);
            em.persist(condidat);
            for (Demandeemploi demandeemploiCollectionDemandeemploi : condidat.getDemandeemploiCollection()) {
                Condidat oldIdOfDemandeemploiCollectionDemandeemploi = demandeemploiCollectionDemandeemploi.getId();
                demandeemploiCollectionDemandeemploi.setId(condidat);
                demandeemploiCollectionDemandeemploi = em.merge(demandeemploiCollectionDemandeemploi);
                if (oldIdOfDemandeemploiCollectionDemandeemploi != null) {
                    oldIdOfDemandeemploiCollectionDemandeemploi.getDemandeemploiCollection().remove(demandeemploiCollectionDemandeemploi);
                    oldIdOfDemandeemploiCollectionDemandeemploi = em.merge(oldIdOfDemandeemploiCollectionDemandeemploi);
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
    public void edit(Condidat condidat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Condidat persistentCondidat = em.find(Condidat.class, condidat.getId());
            Collection<Demandeemploi> demandeemploiCollectionOld = persistentCondidat.getDemandeemploiCollection();
            Collection<Demandeemploi> demandeemploiCollectionNew = condidat.getDemandeemploiCollection();
            List<String> illegalOrphanMessages = null;
            for (Demandeemploi demandeemploiCollectionOldDemandeemploi : demandeemploiCollectionOld) {
                if (!demandeemploiCollectionNew.contains(demandeemploiCollectionOldDemandeemploi)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Demandeemploi " + demandeemploiCollectionOldDemandeemploi + " since its id field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Demandeemploi> attachedDemandeemploiCollectionNew = new ArrayList<Demandeemploi>();
            for (Demandeemploi demandeemploiCollectionNewDemandeemploiToAttach : demandeemploiCollectionNew) {
                demandeemploiCollectionNewDemandeemploiToAttach = em.getReference(demandeemploiCollectionNewDemandeemploiToAttach.getClass(), demandeemploiCollectionNewDemandeemploiToAttach.getIdDemandeemplois());
                attachedDemandeemploiCollectionNew.add(demandeemploiCollectionNewDemandeemploiToAttach);
            }
            demandeemploiCollectionNew = attachedDemandeemploiCollectionNew;
            condidat.setDemandeemploiCollection(demandeemploiCollectionNew);
            condidat = em.merge(condidat);
            for (Demandeemploi demandeemploiCollectionNewDemandeemploi : demandeemploiCollectionNew) {
                if (!demandeemploiCollectionOld.contains(demandeemploiCollectionNewDemandeemploi)) {
                    Condidat oldIdOfDemandeemploiCollectionNewDemandeemploi = demandeemploiCollectionNewDemandeemploi.getId();
                    demandeemploiCollectionNewDemandeemploi.setId(condidat);
                    demandeemploiCollectionNewDemandeemploi = em.merge(demandeemploiCollectionNewDemandeemploi);
                    if (oldIdOfDemandeemploiCollectionNewDemandeemploi != null && !oldIdOfDemandeemploiCollectionNewDemandeemploi.equals(condidat)) {
                        oldIdOfDemandeemploiCollectionNewDemandeemploi.getDemandeemploiCollection().remove(demandeemploiCollectionNewDemandeemploi);
                        oldIdOfDemandeemploiCollectionNewDemandeemploi = em.merge(oldIdOfDemandeemploiCollectionNewDemandeemploi);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = condidat.getId();
                if (findCondidat(id) == null) {
                    throw new NonexistentEntityException("The condidat with id " + id + " no longer exists.");
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
            Condidat condidat;
            try {
                condidat = em.getReference(Condidat.class, id);
                condidat.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The condidat with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Demandeemploi> demandeemploiCollectionOrphanCheck = condidat.getDemandeemploiCollection();
            for (Demandeemploi demandeemploiCollectionOrphanCheckDemandeemploi : demandeemploiCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Condidat (" + condidat + ") cannot be destroyed since the Demandeemploi " + demandeemploiCollectionOrphanCheckDemandeemploi + " in its demandeemploiCollection field has a non-nullable id field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(condidat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Condidat> findCondidatEntities() {
        return findCondidatEntities(true, -1, -1);
    }

    @Override
    public List<Condidat> findCondidatEntities(int maxResults, int firstResult) {
        return findCondidatEntities(false, maxResults, firstResult);
    }

    private List<Condidat> findCondidatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Condidat.class));
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
    public Condidat findCondidat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Condidat.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getCondidatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Condidat> rt = cq.from(Condidat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
