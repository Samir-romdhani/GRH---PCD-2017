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
import tn.ensi.rh.entities.Equipe;
import tn.ensi.rh.entities.Projet;
import tn.ensi.rh.entities.Formation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Mission;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
public class TacheDaoImp implements Serializable, ITacheDao {

        private EntityManagerFactory emf;

    public TacheDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }
    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Tache tache) {
        if (tache.getFormationCollection() == null) {
            tache.setFormationCollection(new ArrayList<Formation>());
        }
        if (tache.getMissionCollection() == null) {
            tache.setMissionCollection(new ArrayList<Mission>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idEquipe = tache.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                tache.setIdEquipe(idEquipe);
            }
            Projet idProjet = tache.getIdProjet();
            if (idProjet != null) {
                idProjet = em.getReference(idProjet.getClass(), idProjet.getIdProjet());
                tache.setIdProjet(idProjet);
            }
            Collection<Formation> attachedFormationCollection = new ArrayList<Formation>();
            for (Formation formationCollectionFormationToAttach : tache.getFormationCollection()) {
                formationCollectionFormationToAttach = em.getReference(formationCollectionFormationToAttach.getClass(), formationCollectionFormationToAttach.getIdFormation());
                attachedFormationCollection.add(formationCollectionFormationToAttach);
            }
            tache.setFormationCollection(attachedFormationCollection);
            Collection<Mission> attachedMissionCollection = new ArrayList<Mission>();
            for (Mission missionCollectionMissionToAttach : tache.getMissionCollection()) {
                missionCollectionMissionToAttach = em.getReference(missionCollectionMissionToAttach.getClass(), missionCollectionMissionToAttach.getIdMission());
                attachedMissionCollection.add(missionCollectionMissionToAttach);
            }
            tache.setMissionCollection(attachedMissionCollection);
            em.persist(tache);
            if (idEquipe != null) {
                idEquipe.getTacheCollection().add(tache);
                idEquipe = em.merge(idEquipe);
            }
            if (idProjet != null) {
                idProjet.getTacheCollection().add(tache);
                idProjet = em.merge(idProjet);
            }
            for (Formation formationCollectionFormation : tache.getFormationCollection()) {
                Tache oldIdTacheOfFormationCollectionFormation = formationCollectionFormation.getIdTache();
                formationCollectionFormation.setIdTache(tache);
                formationCollectionFormation = em.merge(formationCollectionFormation);
                if (oldIdTacheOfFormationCollectionFormation != null) {
                    oldIdTacheOfFormationCollectionFormation.getFormationCollection().remove(formationCollectionFormation);
                    oldIdTacheOfFormationCollectionFormation = em.merge(oldIdTacheOfFormationCollectionFormation);
                }
            }
            for (Mission missionCollectionMission : tache.getMissionCollection()) {
                Tache oldIdTacheOfMissionCollectionMission = missionCollectionMission.getIdTache();
                missionCollectionMission.setIdTache(tache);
                missionCollectionMission = em.merge(missionCollectionMission);
                if (oldIdTacheOfMissionCollectionMission != null) {
                    oldIdTacheOfMissionCollectionMission.getMissionCollection().remove(missionCollectionMission);
                    oldIdTacheOfMissionCollectionMission = em.merge(oldIdTacheOfMissionCollectionMission);
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
    public void edit(Tache tache) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tache persistentTache = em.find(Tache.class, tache.getIdTache());
            Equipe idEquipeOld = persistentTache.getIdEquipe();
            Equipe idEquipeNew = tache.getIdEquipe();
            Projet idProjetOld = persistentTache.getIdProjet();
            Projet idProjetNew = tache.getIdProjet();
            Collection<Formation> formationCollectionOld = persistentTache.getFormationCollection();
            Collection<Formation> formationCollectionNew = tache.getFormationCollection();
            Collection<Mission> missionCollectionOld = persistentTache.getMissionCollection();
            Collection<Mission> missionCollectionNew = tache.getMissionCollection();
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                tache.setIdEquipe(idEquipeNew);
            }
            if (idProjetNew != null) {
                idProjetNew = em.getReference(idProjetNew.getClass(), idProjetNew.getIdProjet());
                tache.setIdProjet(idProjetNew);
            }
            Collection<Formation> attachedFormationCollectionNew = new ArrayList<Formation>();
            for (Formation formationCollectionNewFormationToAttach : formationCollectionNew) {
                formationCollectionNewFormationToAttach = em.getReference(formationCollectionNewFormationToAttach.getClass(), formationCollectionNewFormationToAttach.getIdFormation());
                attachedFormationCollectionNew.add(formationCollectionNewFormationToAttach);
            }
            formationCollectionNew = attachedFormationCollectionNew;
            tache.setFormationCollection(formationCollectionNew);
            Collection<Mission> attachedMissionCollectionNew = new ArrayList<Mission>();
            for (Mission missionCollectionNewMissionToAttach : missionCollectionNew) {
                missionCollectionNewMissionToAttach = em.getReference(missionCollectionNewMissionToAttach.getClass(), missionCollectionNewMissionToAttach.getIdMission());
                attachedMissionCollectionNew.add(missionCollectionNewMissionToAttach);
            }
            missionCollectionNew = attachedMissionCollectionNew;
            tache.setMissionCollection(missionCollectionNew);
            tache = em.merge(tache);
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getTacheCollection().remove(tache);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getTacheCollection().add(tache);
                idEquipeNew = em.merge(idEquipeNew);
            }
            if (idProjetOld != null && !idProjetOld.equals(idProjetNew)) {
                idProjetOld.getTacheCollection().remove(tache);
                idProjetOld = em.merge(idProjetOld);
            }
            if (idProjetNew != null && !idProjetNew.equals(idProjetOld)) {
                idProjetNew.getTacheCollection().add(tache);
                idProjetNew = em.merge(idProjetNew);
            }
            for (Formation formationCollectionOldFormation : formationCollectionOld) {
                if (!formationCollectionNew.contains(formationCollectionOldFormation)) {
                    formationCollectionOldFormation.setIdTache(null);
                    formationCollectionOldFormation = em.merge(formationCollectionOldFormation);
                }
            }
            for (Formation formationCollectionNewFormation : formationCollectionNew) {
                if (!formationCollectionOld.contains(formationCollectionNewFormation)) {
                    Tache oldIdTacheOfFormationCollectionNewFormation = formationCollectionNewFormation.getIdTache();
                    formationCollectionNewFormation.setIdTache(tache);
                    formationCollectionNewFormation = em.merge(formationCollectionNewFormation);
                    if (oldIdTacheOfFormationCollectionNewFormation != null && !oldIdTacheOfFormationCollectionNewFormation.equals(tache)) {
                        oldIdTacheOfFormationCollectionNewFormation.getFormationCollection().remove(formationCollectionNewFormation);
                        oldIdTacheOfFormationCollectionNewFormation = em.merge(oldIdTacheOfFormationCollectionNewFormation);
                    }
                }
            }
            for (Mission missionCollectionOldMission : missionCollectionOld) {
                if (!missionCollectionNew.contains(missionCollectionOldMission)) {
                    missionCollectionOldMission.setIdTache(null);
                    missionCollectionOldMission = em.merge(missionCollectionOldMission);
                }
            }
            for (Mission missionCollectionNewMission : missionCollectionNew) {
                if (!missionCollectionOld.contains(missionCollectionNewMission)) {
                    Tache oldIdTacheOfMissionCollectionNewMission = missionCollectionNewMission.getIdTache();
                    missionCollectionNewMission.setIdTache(tache);
                    missionCollectionNewMission = em.merge(missionCollectionNewMission);
                    if (oldIdTacheOfMissionCollectionNewMission != null && !oldIdTacheOfMissionCollectionNewMission.equals(tache)) {
                        oldIdTacheOfMissionCollectionNewMission.getMissionCollection().remove(missionCollectionNewMission);
                        oldIdTacheOfMissionCollectionNewMission = em.merge(oldIdTacheOfMissionCollectionNewMission);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tache.getIdTache();
                if (findTache(id) == null) {
                    throw new NonexistentEntityException("The tache with id " + id + " no longer exists.");
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
            Tache tache;
            try {
                tache = em.getReference(Tache.class, id);
                tache.getIdTache();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tache with id " + id + " no longer exists.", enfe);
            }
            Equipe idEquipe = tache.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getTacheCollection().remove(tache);
                idEquipe = em.merge(idEquipe);
            }
            Projet idProjet = tache.getIdProjet();
            if (idProjet != null) {
                idProjet.getTacheCollection().remove(tache);
                idProjet = em.merge(idProjet);
            }
            Collection<Formation> formationCollection = tache.getFormationCollection();
            for (Formation formationCollectionFormation : formationCollection) {
                formationCollectionFormation.setIdTache(null);
                formationCollectionFormation = em.merge(formationCollectionFormation);
            }
            Collection<Mission> missionCollection = tache.getMissionCollection();
            for (Mission missionCollectionMission : missionCollection) {
                missionCollectionMission.setIdTache(null);
                missionCollectionMission = em.merge(missionCollectionMission);
            }
            em.remove(tache);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Tache> findTacheEntities() {
        return findTacheEntities(true, -1, -1);
    }

    @Override
    public List<Tache> findTacheEntities(int maxResults, int firstResult) {
        return findTacheEntities(false, maxResults, firstResult);
    }

    private List<Tache> findTacheEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tache.class));
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
    public Tache findTache(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tache.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getTacheCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tache> rt = cq.from(Tache.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
