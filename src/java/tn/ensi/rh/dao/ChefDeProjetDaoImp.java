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
import tn.ensi.rh.entities.ChefDeProjet;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Equipe;

/**
 *
 * @author user
 */
public class ChefDeProjetDaoImp implements Serializable, IChefDeProjetDao {

    private EntityManagerFactory emf;

    public ChefDeProjetDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(ChefDeProjet chefDeProjet) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employe idEmploye = chefDeProjet.getIdEmploye();
            if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getIdEmploye());
                chefDeProjet.setIdEmploye(idEmploye);
            }
            Equipe idEquipe = chefDeProjet.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                chefDeProjet.setIdEquipe(idEquipe);
            }
            em.persist(chefDeProjet);
            if (idEmploye != null) {
                idEmploye.getChefDeProjetCollection().add(chefDeProjet);
                idEmploye = em.merge(idEmploye);
            }
            if (idEquipe != null) {
                idEquipe.getChefDeProjetCollection().add(chefDeProjet);
                idEquipe = em.merge(idEquipe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(ChefDeProjet chefDeProjet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChefDeProjet persistentChefDeProjet = em.find(ChefDeProjet.class, chefDeProjet.getIdChefDeProjet());
            Employe idEmployeOld = persistentChefDeProjet.getIdEmploye();
            Employe idEmployeNew = chefDeProjet.getIdEmploye();
            Equipe idEquipeOld = persistentChefDeProjet.getIdEquipe();
            Equipe idEquipeNew = chefDeProjet.getIdEquipe();
            if (idEmployeNew != null) {
                idEmployeNew = em.getReference(idEmployeNew.getClass(), idEmployeNew.getIdEmploye());
                chefDeProjet.setIdEmploye(idEmployeNew);
            }
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                chefDeProjet.setIdEquipe(idEquipeNew);
            }
            chefDeProjet = em.merge(chefDeProjet);
            if (idEmployeOld != null && !idEmployeOld.equals(idEmployeNew)) {
                idEmployeOld.getChefDeProjetCollection().remove(chefDeProjet);
                idEmployeOld = em.merge(idEmployeOld);
            }
            if (idEmployeNew != null && !idEmployeNew.equals(idEmployeOld)) {
                idEmployeNew.getChefDeProjetCollection().add(chefDeProjet);
                idEmployeNew = em.merge(idEmployeNew);
            }
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getChefDeProjetCollection().remove(chefDeProjet);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getChefDeProjetCollection().add(chefDeProjet);
                idEquipeNew = em.merge(idEquipeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = chefDeProjet.getIdChefDeProjet();
                if (findChefDeProjet(id) == null) {
                    throw new NonexistentEntityException("The chefDeProjet with id " + id + " no longer exists.");
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
            ChefDeProjet chefDeProjet;
            try {
                chefDeProjet = em.getReference(ChefDeProjet.class, id);
                chefDeProjet.getIdChefDeProjet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chefDeProjet with id " + id + " no longer exists.", enfe);
            }
            Employe idEmploye = chefDeProjet.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getChefDeProjetCollection().remove(chefDeProjet);
                idEmploye = em.merge(idEmploye);
            }
            Equipe idEquipe = chefDeProjet.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getChefDeProjetCollection().remove(chefDeProjet);
                idEquipe = em.merge(idEquipe);
            }
            em.remove(chefDeProjet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<ChefDeProjet> findChefDeProjetEntities() {
        return findChefDeProjetEntities(true, -1, -1);
    }

    @Override
    public List<ChefDeProjet> findChefDeProjetEntities(int maxResults, int firstResult) {
        return findChefDeProjetEntities(false, maxResults, firstResult);
    }

    private List<ChefDeProjet> findChefDeProjetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChefDeProjet.class));
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
    public ChefDeProjet findChefDeProjet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChefDeProjet.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getChefDeProjetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChefDeProjet> rt = cq.from(ChefDeProjet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
