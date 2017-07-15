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
import tn.ensi.rh.entities.Formation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Mission;
import tn.ensi.rh.entities.Projet;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
public class ProjetDaoImp implements Serializable, IProjetDao {

        private EntityManagerFactory emf;

    public ProjetDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Projet projet) {
        if (projet.getFormationCollection() == null) {
            projet.setFormationCollection(new ArrayList<Formation>());
        }
        if (projet.getMissionCollection() == null) {
            projet.setMissionCollection(new ArrayList<Mission>());
        }
        if (projet.getTacheCollection() == null) {
            projet.setTacheCollection(new ArrayList<Tache>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe idEquipe = projet.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                projet.setIdEquipe(idEquipe);
            }
            Collection<Formation> attachedFormationCollection = new ArrayList<Formation>();
            for (Formation formationCollectionFormationToAttach : projet.getFormationCollection()) {
                formationCollectionFormationToAttach = em.getReference(formationCollectionFormationToAttach.getClass(), formationCollectionFormationToAttach.getIdFormation());
                attachedFormationCollection.add(formationCollectionFormationToAttach);
            }
            projet.setFormationCollection(attachedFormationCollection);
            Collection<Mission> attachedMissionCollection = new ArrayList<Mission>();
            for (Mission missionCollectionMissionToAttach : projet.getMissionCollection()) {
                missionCollectionMissionToAttach = em.getReference(missionCollectionMissionToAttach.getClass(), missionCollectionMissionToAttach.getIdMission());
                attachedMissionCollection.add(missionCollectionMissionToAttach);
            }
            projet.setMissionCollection(attachedMissionCollection);
            Collection<Tache> attachedTacheCollection = new ArrayList<Tache>();
            for (Tache tacheCollectionTacheToAttach : projet.getTacheCollection()) {
                tacheCollectionTacheToAttach = em.getReference(tacheCollectionTacheToAttach.getClass(), tacheCollectionTacheToAttach.getIdTache());
                attachedTacheCollection.add(tacheCollectionTacheToAttach);
            }
            projet.setTacheCollection(attachedTacheCollection);
            em.persist(projet);
            if (idEquipe != null) {
                idEquipe.getProjetCollection().add(projet);
                idEquipe = em.merge(idEquipe);
            }
            for (Formation formationCollectionFormation : projet.getFormationCollection()) {
                Projet oldIdProjetOfFormationCollectionFormation = formationCollectionFormation.getIdProjet();
                formationCollectionFormation.setIdProjet(projet);
                formationCollectionFormation = em.merge(formationCollectionFormation);
                if (oldIdProjetOfFormationCollectionFormation != null) {
                    oldIdProjetOfFormationCollectionFormation.getFormationCollection().remove(formationCollectionFormation);
                    oldIdProjetOfFormationCollectionFormation = em.merge(oldIdProjetOfFormationCollectionFormation);
                }
            }
            for (Mission missionCollectionMission : projet.getMissionCollection()) {
                Projet oldIdProjetOfMissionCollectionMission = missionCollectionMission.getIdProjet();
                missionCollectionMission.setIdProjet(projet);
                missionCollectionMission = em.merge(missionCollectionMission);
                if (oldIdProjetOfMissionCollectionMission != null) {
                    oldIdProjetOfMissionCollectionMission.getMissionCollection().remove(missionCollectionMission);
                    oldIdProjetOfMissionCollectionMission = em.merge(oldIdProjetOfMissionCollectionMission);
                }
            }
            for (Tache tacheCollectionTache : projet.getTacheCollection()) {
                Projet oldIdProjetOfTacheCollectionTache = tacheCollectionTache.getIdProjet();
                tacheCollectionTache.setIdProjet(projet);
                tacheCollectionTache = em.merge(tacheCollectionTache);
                if (oldIdProjetOfTacheCollectionTache != null) {
                    oldIdProjetOfTacheCollectionTache.getTacheCollection().remove(tacheCollectionTache);
                    oldIdProjetOfTacheCollectionTache = em.merge(oldIdProjetOfTacheCollectionTache);
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
    public void edit(Projet projet) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projet persistentProjet = em.find(Projet.class, projet.getIdProjet());
            Equipe idEquipeOld = persistentProjet.getIdEquipe();
            Equipe idEquipeNew = projet.getIdEquipe();
            Collection<Formation> formationCollectionOld = persistentProjet.getFormationCollection();
            Collection<Formation> formationCollectionNew = projet.getFormationCollection();
            Collection<Mission> missionCollectionOld = persistentProjet.getMissionCollection();
            Collection<Mission> missionCollectionNew = projet.getMissionCollection();
            Collection<Tache> tacheCollectionOld = persistentProjet.getTacheCollection();
            Collection<Tache> tacheCollectionNew = projet.getTacheCollection();
            List<String> illegalOrphanMessages = null;
            for (Tache tacheCollectionOldTache : tacheCollectionOld) {
                if (!tacheCollectionNew.contains(tacheCollectionOldTache)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tache " + tacheCollectionOldTache + " since its idProjet field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                projet.setIdEquipe(idEquipeNew);
            }
            Collection<Formation> attachedFormationCollectionNew = new ArrayList<Formation>();
            for (Formation formationCollectionNewFormationToAttach : formationCollectionNew) {
                formationCollectionNewFormationToAttach = em.getReference(formationCollectionNewFormationToAttach.getClass(), formationCollectionNewFormationToAttach.getIdFormation());
                attachedFormationCollectionNew.add(formationCollectionNewFormationToAttach);
            }
            formationCollectionNew = attachedFormationCollectionNew;
            projet.setFormationCollection(formationCollectionNew);
            Collection<Mission> attachedMissionCollectionNew = new ArrayList<Mission>();
            for (Mission missionCollectionNewMissionToAttach : missionCollectionNew) {
                missionCollectionNewMissionToAttach = em.getReference(missionCollectionNewMissionToAttach.getClass(), missionCollectionNewMissionToAttach.getIdMission());
                attachedMissionCollectionNew.add(missionCollectionNewMissionToAttach);
            }
            missionCollectionNew = attachedMissionCollectionNew;
            projet.setMissionCollection(missionCollectionNew);
            Collection<Tache> attachedTacheCollectionNew = new ArrayList<Tache>();
            for (Tache tacheCollectionNewTacheToAttach : tacheCollectionNew) {
                tacheCollectionNewTacheToAttach = em.getReference(tacheCollectionNewTacheToAttach.getClass(), tacheCollectionNewTacheToAttach.getIdTache());
                attachedTacheCollectionNew.add(tacheCollectionNewTacheToAttach);
            }
            tacheCollectionNew = attachedTacheCollectionNew;
            projet.setTacheCollection(tacheCollectionNew);
            projet = em.merge(projet);
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getProjetCollection().remove(projet);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getProjetCollection().add(projet);
                idEquipeNew = em.merge(idEquipeNew);
            }
            for (Formation formationCollectionOldFormation : formationCollectionOld) {
                if (!formationCollectionNew.contains(formationCollectionOldFormation)) {
                    formationCollectionOldFormation.setIdProjet(null);
                    formationCollectionOldFormation = em.merge(formationCollectionOldFormation);
                }
            }
            for (Formation formationCollectionNewFormation : formationCollectionNew) {
                if (!formationCollectionOld.contains(formationCollectionNewFormation)) {
                    Projet oldIdProjetOfFormationCollectionNewFormation = formationCollectionNewFormation.getIdProjet();
                    formationCollectionNewFormation.setIdProjet(projet);
                    formationCollectionNewFormation = em.merge(formationCollectionNewFormation);
                    if (oldIdProjetOfFormationCollectionNewFormation != null && !oldIdProjetOfFormationCollectionNewFormation.equals(projet)) {
                        oldIdProjetOfFormationCollectionNewFormation.getFormationCollection().remove(formationCollectionNewFormation);
                        oldIdProjetOfFormationCollectionNewFormation = em.merge(oldIdProjetOfFormationCollectionNewFormation);
                    }
                }
            }
            for (Mission missionCollectionOldMission : missionCollectionOld) {
                if (!missionCollectionNew.contains(missionCollectionOldMission)) {
                    missionCollectionOldMission.setIdProjet(null);
                    missionCollectionOldMission = em.merge(missionCollectionOldMission);
                }
            }
            for (Mission missionCollectionNewMission : missionCollectionNew) {
                if (!missionCollectionOld.contains(missionCollectionNewMission)) {
                    Projet oldIdProjetOfMissionCollectionNewMission = missionCollectionNewMission.getIdProjet();
                    missionCollectionNewMission.setIdProjet(projet);
                    missionCollectionNewMission = em.merge(missionCollectionNewMission);
                    if (oldIdProjetOfMissionCollectionNewMission != null && !oldIdProjetOfMissionCollectionNewMission.equals(projet)) {
                        oldIdProjetOfMissionCollectionNewMission.getMissionCollection().remove(missionCollectionNewMission);
                        oldIdProjetOfMissionCollectionNewMission = em.merge(oldIdProjetOfMissionCollectionNewMission);
                    }
                }
            }
            for (Tache tacheCollectionNewTache : tacheCollectionNew) {
                if (!tacheCollectionOld.contains(tacheCollectionNewTache)) {
                    Projet oldIdProjetOfTacheCollectionNewTache = tacheCollectionNewTache.getIdProjet();
                    tacheCollectionNewTache.setIdProjet(projet);
                    tacheCollectionNewTache = em.merge(tacheCollectionNewTache);
                    if (oldIdProjetOfTacheCollectionNewTache != null && !oldIdProjetOfTacheCollectionNewTache.equals(projet)) {
                        oldIdProjetOfTacheCollectionNewTache.getTacheCollection().remove(tacheCollectionNewTache);
                        oldIdProjetOfTacheCollectionNewTache = em.merge(oldIdProjetOfTacheCollectionNewTache);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projet.getIdProjet();
                if (findProjet(id) == null) {
                    throw new NonexistentEntityException("The projet with id " + id + " no longer exists.");
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
            Projet projet;
            try {
                projet = em.getReference(Projet.class, id);
                projet.getIdProjet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projet with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Tache> tacheCollectionOrphanCheck = projet.getTacheCollection();
            for (Tache tacheCollectionOrphanCheckTache : tacheCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Projet (" + projet + ") cannot be destroyed since the Tache " + tacheCollectionOrphanCheckTache + " in its tacheCollection field has a non-nullable idProjet field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Equipe idEquipe = projet.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getProjetCollection().remove(projet);
                idEquipe = em.merge(idEquipe);
            }
            Collection<Formation> formationCollection = projet.getFormationCollection();
            for (Formation formationCollectionFormation : formationCollection) {
                formationCollectionFormation.setIdProjet(null);
                formationCollectionFormation = em.merge(formationCollectionFormation);
            }
            Collection<Mission> missionCollection = projet.getMissionCollection();
            for (Mission missionCollectionMission : missionCollection) {
                missionCollectionMission.setIdProjet(null);
                missionCollectionMission = em.merge(missionCollectionMission);
            }
            em.remove(projet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Projet> findProjetEntities() {
        return findProjetEntities(true, -1, -1);
    }

    @Override
    public List<Projet> findProjetEntities(int maxResults, int firstResult) {
        return findProjetEntities(false, maxResults, firstResult);
    }

    private List<Projet> findProjetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projet.class));
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
    public Projet findProjet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projet.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getProjetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projet> rt = cq.from(Projet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
