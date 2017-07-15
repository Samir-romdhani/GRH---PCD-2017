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
import tn.ensi.rh.entities.Mission;
import tn.ensi.rh.entities.Projet;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
public class MissionDaoImp implements Serializable, IMissionDao {

        private EntityManagerFactory emf;

    public MissionDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Mission mission) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idEquipe = mission.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                mission.setIdEquipe(idEquipe);
            }
            Projet idProjet = mission.getIdProjet();
            if (idProjet != null) {
                idProjet = em.getReference(idProjet.getClass(), idProjet.getIdProjet());
                mission.setIdProjet(idProjet);
            }
            Tache idTache = mission.getIdTache();
            if (idTache != null) {
                idTache = em.getReference(idTache.getClass(), idTache.getIdTache());
                mission.setIdTache(idTache);
            }
            em.persist(mission);
            if (idEquipe != null) {
                idEquipe.getMissionCollection().add(mission);
                idEquipe = em.merge(idEquipe);
            }
            if (idProjet != null) {
                idProjet.getMissionCollection().add(mission);
                idProjet = em.merge(idProjet);
            }
            if (idTache != null) {
                idTache.getMissionCollection().add(mission);
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
    public void edit(Mission mission) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mission persistentMission = em.find(Mission.class, mission.getIdMission());
            Equipe idEquipeOld = persistentMission.getIdEquipe();
            Equipe idEquipeNew = mission.getIdEquipe();
            Projet idProjetOld = persistentMission.getIdProjet();
            Projet idProjetNew = mission.getIdProjet();
            Tache idTacheOld = persistentMission.getIdTache();
            Tache idTacheNew = mission.getIdTache();
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                mission.setIdEquipe(idEquipeNew);
            }
            if (idProjetNew != null) {
                idProjetNew = em.getReference(idProjetNew.getClass(), idProjetNew.getIdProjet());
                mission.setIdProjet(idProjetNew);
            }
            if (idTacheNew != null) {
                idTacheNew = em.getReference(idTacheNew.getClass(), idTacheNew.getIdTache());
                mission.setIdTache(idTacheNew);
            }
            mission = em.merge(mission);
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getMissionCollection().remove(mission);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getMissionCollection().add(mission);
                idEquipeNew = em.merge(idEquipeNew);
            }
            if (idProjetOld != null && !idProjetOld.equals(idProjetNew)) {
                idProjetOld.getMissionCollection().remove(mission);
                idProjetOld = em.merge(idProjetOld);
            }
            if (idProjetNew != null && !idProjetNew.equals(idProjetOld)) {
                idProjetNew.getMissionCollection().add(mission);
                idProjetNew = em.merge(idProjetNew);
            }
            if (idTacheOld != null && !idTacheOld.equals(idTacheNew)) {
                idTacheOld.getMissionCollection().remove(mission);
                idTacheOld = em.merge(idTacheOld);
            }
            if (idTacheNew != null && !idTacheNew.equals(idTacheOld)) {
                idTacheNew.getMissionCollection().add(mission);
                idTacheNew = em.merge(idTacheNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mission.getIdMission();
                if (findMission(id) == null) {
                    throw new NonexistentEntityException("The mission with id " + id + " no longer exists.");
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
            Mission mission;
            try {
                mission = em.getReference(Mission.class, id);
                mission.getIdMission();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mission with id " + id + " no longer exists.", enfe);
            }
            Equipe idEquipe = mission.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getMissionCollection().remove(mission);
                idEquipe = em.merge(idEquipe);
            }
            Projet idProjet = mission.getIdProjet();
            if (idProjet != null) {
                idProjet.getMissionCollection().remove(mission);
                idProjet = em.merge(idProjet);
            }
            Tache idTache = mission.getIdTache();
            if (idTache != null) {
                idTache.getMissionCollection().remove(mission);
                idTache = em.merge(idTache);
            }
            em.remove(mission);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Mission> findMissionEntities() {
        return findMissionEntities(true, -1, -1);
    }

    @Override
    public List<Mission> findMissionEntities(int maxResults, int firstResult) {
        return findMissionEntities(false, maxResults, firstResult);
    }

    private List<Mission> findMissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mission.class));
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
    public Mission findMission(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mission.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getMissionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mission> rt = cq.from(Mission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
