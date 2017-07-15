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
import tn.ensi.rh.entities.Equipe;
import tn.ensi.rh.entities.Formation;
import tn.ensi.rh.entities.Projet;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
public class FormationDaoImp implements Serializable, IFormationDao {

        private EntityManagerFactory emf;

    public FormationDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Formation formation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idEquipe = formation.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                formation.setIdEquipe(idEquipe);
            }
            Projet idProjet = formation.getIdProjet();
            if (idProjet != null) {
                idProjet = em.getReference(idProjet.getClass(), idProjet.getIdProjet());
                formation.setIdProjet(idProjet);
            }
            Tache idTache = formation.getIdTache();
            if (idTache != null) {
                idTache = em.getReference(idTache.getClass(), idTache.getIdTache());
                formation.setIdTache(idTache);
            }
            em.persist(formation);
            if (idEquipe != null) {
                idEquipe.getFormationCollection().add(formation);
                idEquipe = em.merge(idEquipe);
            }
            if (idProjet != null) {
                idProjet.getFormationCollection().add(formation);
                idProjet = em.merge(idProjet);
            }
            if (idTache != null) {
                idTache.getFormationCollection().add(formation);
                idTache = em.merge(idTache);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Formation formation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formation persistentFormation = em.find(Formation.class, formation.getIdFormation());
            Equipe idEquipeOld = persistentFormation.getIdEquipe();
            Equipe idEquipeNew = formation.getIdEquipe();
            Projet idProjetOld = persistentFormation.getIdProjet();
            Projet idProjetNew = formation.getIdProjet();
            Tache idTacheOld = persistentFormation.getIdTache();
            Tache idTacheNew = formation.getIdTache();
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                formation.setIdEquipe(idEquipeNew);
            }
            if (idProjetNew != null) {
                idProjetNew = em.getReference(idProjetNew.getClass(), idProjetNew.getIdProjet());
                formation.setIdProjet(idProjetNew);
            }
            if (idTacheNew != null) {
                idTacheNew = em.getReference(idTacheNew.getClass(), idTacheNew.getIdTache());
                formation.setIdTache(idTacheNew);
            }
            formation = em.merge(formation);
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getFormationCollection().remove(formation);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getFormationCollection().add(formation);
                idEquipeNew = em.merge(idEquipeNew);
            }
            if (idProjetOld != null && !idProjetOld.equals(idProjetNew)) {
                idProjetOld.getFormationCollection().remove(formation);
                idProjetOld = em.merge(idProjetOld);
            }
            if (idProjetNew != null && !idProjetNew.equals(idProjetOld)) {
                idProjetNew.getFormationCollection().add(formation);
                idProjetNew = em.merge(idProjetNew);
            }
            if (idTacheOld != null && !idTacheOld.equals(idTacheNew)) {
                idTacheOld.getFormationCollection().remove(formation);
                idTacheOld = em.merge(idTacheOld);
            }
            if (idTacheNew != null && !idTacheNew.equals(idTacheOld)) {
                idTacheNew.getFormationCollection().add(formation);
                idTacheNew = em.merge(idTacheNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formation.getIdFormation();
                if (findFormation(id) == null) {
                    throw new NonexistentEntityException("The formation with id " + id + " no longer exists.");
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
            Formation formation;
            try {
                formation = em.getReference(Formation.class, id);
                formation.getIdFormation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formation with id " + id + " no longer exists.", enfe);
            }
            Equipe idEquipe = formation.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getFormationCollection().remove(formation);
                idEquipe = em.merge(idEquipe);
            }
            Projet idProjet = formation.getIdProjet();
            if (idProjet != null) {
                idProjet.getFormationCollection().remove(formation);
                idProjet = em.merge(idProjet);
            }
            Tache idTache = formation.getIdTache();
            if (idTache != null) {
                idTache.getFormationCollection().remove(formation);
                idTache = em.merge(idTache);
            }
            em.remove(formation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Formation> findFormationEntities() {
        return findFormationEntities(true, -1, -1);
    }

    @Override
    public List<Formation> findFormationEntities(int maxResults, int firstResult) {
        return findFormationEntities(false, maxResults, firstResult);
    }

    private List<Formation> findFormationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formation.class));
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
    public Formation findFormation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formation.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getFormationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formation> rt = cq.from(Formation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
