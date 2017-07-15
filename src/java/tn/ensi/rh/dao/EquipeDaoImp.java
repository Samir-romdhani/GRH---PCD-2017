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
import tn.ensi.rh.entities.ChefDeProjet;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Equipe;

/**
 *
 * @author user
 */
public class EquipeDaoImp implements Serializable, IEquipeDao {

         private EntityManagerFactory emf;

    public EquipeDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Equipe equipe) {
        if (equipe.getFormationCollection() == null) {
            equipe.setFormationCollection(new ArrayList<Formation>());
        }
        if (equipe.getMissionCollection() == null) {
            equipe.setMissionCollection(new ArrayList<Mission>());
        }
        if (equipe.getProjetCollection() == null) {
            equipe.setProjetCollection(new ArrayList<Projet>());
        }
        if (equipe.getTacheCollection() == null) {
            equipe.setTacheCollection(new ArrayList<Tache>());
        }
        if (equipe.getChefDeProjetCollection() == null) {
            equipe.setChefDeProjetCollection(new ArrayList<ChefDeProjet>());
        }
        if (equipe.getEmployeCollection() == null) {
            equipe.setEmployeCollection(new ArrayList<Employe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Formation> attachedFormationCollection = new ArrayList<Formation>();
            for (Formation formationCollectionFormationToAttach : equipe.getFormationCollection()) {
                formationCollectionFormationToAttach = em.getReference(formationCollectionFormationToAttach.getClass(), formationCollectionFormationToAttach.getIdFormation());
                attachedFormationCollection.add(formationCollectionFormationToAttach);
            }
            equipe.setFormationCollection(attachedFormationCollection);
            Collection<Mission> attachedMissionCollection = new ArrayList<Mission>();
            for (Mission missionCollectionMissionToAttach : equipe.getMissionCollection()) {
                missionCollectionMissionToAttach = em.getReference(missionCollectionMissionToAttach.getClass(), missionCollectionMissionToAttach.getIdMission());
                attachedMissionCollection.add(missionCollectionMissionToAttach);
            }
            equipe.setMissionCollection(attachedMissionCollection);
            Collection<Projet> attachedProjetCollection = new ArrayList<Projet>();
            for (Projet projetCollectionProjetToAttach : equipe.getProjetCollection()) {
                projetCollectionProjetToAttach = em.getReference(projetCollectionProjetToAttach.getClass(), projetCollectionProjetToAttach.getIdProjet());
                attachedProjetCollection.add(projetCollectionProjetToAttach);
            }
            equipe.setProjetCollection(attachedProjetCollection);
            Collection<Tache> attachedTacheCollection = new ArrayList<Tache>();
            for (Tache tacheCollectionTacheToAttach : equipe.getTacheCollection()) {
                tacheCollectionTacheToAttach = em.getReference(tacheCollectionTacheToAttach.getClass(), tacheCollectionTacheToAttach.getIdTache());
                attachedTacheCollection.add(tacheCollectionTacheToAttach);
            }
            equipe.setTacheCollection(attachedTacheCollection);
            Collection<ChefDeProjet> attachedChefDeProjetCollection = new ArrayList<ChefDeProjet>();
            for (ChefDeProjet chefDeProjetCollectionChefDeProjetToAttach : equipe.getChefDeProjetCollection()) {
                chefDeProjetCollectionChefDeProjetToAttach = em.getReference(chefDeProjetCollectionChefDeProjetToAttach.getClass(), chefDeProjetCollectionChefDeProjetToAttach.getIdChefDeProjet());
                attachedChefDeProjetCollection.add(chefDeProjetCollectionChefDeProjetToAttach);
            }
            equipe.setChefDeProjetCollection(attachedChefDeProjetCollection);
            Collection<Employe> attachedEmployeCollection = new ArrayList<Employe>();
            for (Employe employeCollectionEmployeToAttach : equipe.getEmployeCollection()) {
                employeCollectionEmployeToAttach = em.getReference(employeCollectionEmployeToAttach.getClass(), employeCollectionEmployeToAttach.getIdEmploye());
                attachedEmployeCollection.add(employeCollectionEmployeToAttach);
            }
            equipe.setEmployeCollection(attachedEmployeCollection);
            em.persist(equipe);
            for (Formation formationCollectionFormation : equipe.getFormationCollection()) {
                Equipe oldIdEquipeOfFormationCollectionFormation = formationCollectionFormation.getIdEquipe();
                formationCollectionFormation.setIdEquipe(equipe);
                formationCollectionFormation = em.merge(formationCollectionFormation);
                if (oldIdEquipeOfFormationCollectionFormation != null) {
                    oldIdEquipeOfFormationCollectionFormation.getFormationCollection().remove(formationCollectionFormation);
                    oldIdEquipeOfFormationCollectionFormation = em.merge(oldIdEquipeOfFormationCollectionFormation);
                }
            }
            for (Mission missionCollectionMission : equipe.getMissionCollection()) {
                Equipe oldIdEquipeOfMissionCollectionMission = missionCollectionMission.getIdEquipe();
                missionCollectionMission.setIdEquipe(equipe);
                missionCollectionMission = em.merge(missionCollectionMission);
                if (oldIdEquipeOfMissionCollectionMission != null) {
                    oldIdEquipeOfMissionCollectionMission.getMissionCollection().remove(missionCollectionMission);
                    oldIdEquipeOfMissionCollectionMission = em.merge(oldIdEquipeOfMissionCollectionMission);
                }
            }
            for (Projet projetCollectionProjet : equipe.getProjetCollection()) {
                Equipe oldIdEquipeOfProjetCollectionProjet = projetCollectionProjet.getIdEquipe();
                projetCollectionProjet.setIdEquipe(equipe);
                projetCollectionProjet = em.merge(projetCollectionProjet);
                if (oldIdEquipeOfProjetCollectionProjet != null) {
                    oldIdEquipeOfProjetCollectionProjet.getProjetCollection().remove(projetCollectionProjet);
                    oldIdEquipeOfProjetCollectionProjet = em.merge(oldIdEquipeOfProjetCollectionProjet);
                }
            }
            for (Tache tacheCollectionTache : equipe.getTacheCollection()) {
                Equipe oldIdEquipeOfTacheCollectionTache = tacheCollectionTache.getIdEquipe();
                tacheCollectionTache.setIdEquipe(equipe);
                tacheCollectionTache = em.merge(tacheCollectionTache);
                if (oldIdEquipeOfTacheCollectionTache != null) {
                    oldIdEquipeOfTacheCollectionTache.getTacheCollection().remove(tacheCollectionTache);
                    oldIdEquipeOfTacheCollectionTache = em.merge(oldIdEquipeOfTacheCollectionTache);
                }
            }
            for (ChefDeProjet chefDeProjetCollectionChefDeProjet : equipe.getChefDeProjetCollection()) {
                Equipe oldIdEquipeOfChefDeProjetCollectionChefDeProjet = chefDeProjetCollectionChefDeProjet.getIdEquipe();
                chefDeProjetCollectionChefDeProjet.setIdEquipe(equipe);
                chefDeProjetCollectionChefDeProjet = em.merge(chefDeProjetCollectionChefDeProjet);
                if (oldIdEquipeOfChefDeProjetCollectionChefDeProjet != null) {
                    oldIdEquipeOfChefDeProjetCollectionChefDeProjet.getChefDeProjetCollection().remove(chefDeProjetCollectionChefDeProjet);
                    oldIdEquipeOfChefDeProjetCollectionChefDeProjet = em.merge(oldIdEquipeOfChefDeProjetCollectionChefDeProjet);
                }
            }
            for (Employe employeCollectionEmploye : equipe.getEmployeCollection()) {
                Equipe oldIdEquipeOfEmployeCollectionEmploye = employeCollectionEmploye.getIdEquipe();
                employeCollectionEmploye.setIdEquipe(equipe);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
                if (oldIdEquipeOfEmployeCollectionEmploye != null) {
                    oldIdEquipeOfEmployeCollectionEmploye.getEmployeCollection().remove(employeCollectionEmploye);
                    oldIdEquipeOfEmployeCollectionEmploye = em.merge(oldIdEquipeOfEmployeCollectionEmploye);
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
    public void edit(Equipe equipe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe persistentEquipe = em.find(Equipe.class, equipe.getIdEquipe());
            Collection<Formation> formationCollectionOld = persistentEquipe.getFormationCollection();
            Collection<Formation> formationCollectionNew = equipe.getFormationCollection();
            Collection<Mission> missionCollectionOld = persistentEquipe.getMissionCollection();
            Collection<Mission> missionCollectionNew = equipe.getMissionCollection();
            Collection<Projet> projetCollectionOld = persistentEquipe.getProjetCollection();
            Collection<Projet> projetCollectionNew = equipe.getProjetCollection();
            Collection<Tache> tacheCollectionOld = persistentEquipe.getTacheCollection();
            Collection<Tache> tacheCollectionNew = equipe.getTacheCollection();
            Collection<ChefDeProjet> chefDeProjetCollectionOld = persistentEquipe.getChefDeProjetCollection();
            Collection<ChefDeProjet> chefDeProjetCollectionNew = equipe.getChefDeProjetCollection();
            Collection<Employe> employeCollectionOld = persistentEquipe.getEmployeCollection();
            Collection<Employe> employeCollectionNew = equipe.getEmployeCollection();
            List<String> illegalOrphanMessages = null;
            for (Projet projetCollectionOldProjet : projetCollectionOld) {
                if (!projetCollectionNew.contains(projetCollectionOldProjet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projet " + projetCollectionOldProjet + " since its idEquipe field is not nullable.");
                }
            }
            for (Tache tacheCollectionOldTache : tacheCollectionOld) {
                if (!tacheCollectionNew.contains(tacheCollectionOldTache)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tache " + tacheCollectionOldTache + " since its idEquipe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Formation> attachedFormationCollectionNew = new ArrayList<Formation>();
            for (Formation formationCollectionNewFormationToAttach : formationCollectionNew) {
                formationCollectionNewFormationToAttach = em.getReference(formationCollectionNewFormationToAttach.getClass(), formationCollectionNewFormationToAttach.getIdFormation());
                attachedFormationCollectionNew.add(formationCollectionNewFormationToAttach);
            }
            formationCollectionNew = attachedFormationCollectionNew;
            equipe.setFormationCollection(formationCollectionNew);
            Collection<Mission> attachedMissionCollectionNew = new ArrayList<Mission>();
            for (Mission missionCollectionNewMissionToAttach : missionCollectionNew) {
                missionCollectionNewMissionToAttach = em.getReference(missionCollectionNewMissionToAttach.getClass(), missionCollectionNewMissionToAttach.getIdMission());
                attachedMissionCollectionNew.add(missionCollectionNewMissionToAttach);
            }
            missionCollectionNew = attachedMissionCollectionNew;
            equipe.setMissionCollection(missionCollectionNew);
            Collection<Projet> attachedProjetCollectionNew = new ArrayList<Projet>();
            for (Projet projetCollectionNewProjetToAttach : projetCollectionNew) {
                projetCollectionNewProjetToAttach = em.getReference(projetCollectionNewProjetToAttach.getClass(), projetCollectionNewProjetToAttach.getIdProjet());
                attachedProjetCollectionNew.add(projetCollectionNewProjetToAttach);
            }
            projetCollectionNew = attachedProjetCollectionNew;
            equipe.setProjetCollection(projetCollectionNew);
            Collection<Tache> attachedTacheCollectionNew = new ArrayList<Tache>();
            for (Tache tacheCollectionNewTacheToAttach : tacheCollectionNew) {
                tacheCollectionNewTacheToAttach = em.getReference(tacheCollectionNewTacheToAttach.getClass(), tacheCollectionNewTacheToAttach.getIdTache());
                attachedTacheCollectionNew.add(tacheCollectionNewTacheToAttach);
            }
            tacheCollectionNew = attachedTacheCollectionNew;
            equipe.setTacheCollection(tacheCollectionNew);
            Collection<ChefDeProjet> attachedChefDeProjetCollectionNew = new ArrayList<ChefDeProjet>();
            for (ChefDeProjet chefDeProjetCollectionNewChefDeProjetToAttach : chefDeProjetCollectionNew) {
                chefDeProjetCollectionNewChefDeProjetToAttach = em.getReference(chefDeProjetCollectionNewChefDeProjetToAttach.getClass(), chefDeProjetCollectionNewChefDeProjetToAttach.getIdChefDeProjet());
                attachedChefDeProjetCollectionNew.add(chefDeProjetCollectionNewChefDeProjetToAttach);
            }
            chefDeProjetCollectionNew = attachedChefDeProjetCollectionNew;
            equipe.setChefDeProjetCollection(chefDeProjetCollectionNew);
            Collection<Employe> attachedEmployeCollectionNew = new ArrayList<Employe>();
            for (Employe employeCollectionNewEmployeToAttach : employeCollectionNew) {
                employeCollectionNewEmployeToAttach = em.getReference(employeCollectionNewEmployeToAttach.getClass(), employeCollectionNewEmployeToAttach.getIdEmploye());
                attachedEmployeCollectionNew.add(employeCollectionNewEmployeToAttach);
            }
            employeCollectionNew = attachedEmployeCollectionNew;
            equipe.setEmployeCollection(employeCollectionNew);
            equipe = em.merge(equipe);
            for (Formation formationCollectionOldFormation : formationCollectionOld) {
                if (!formationCollectionNew.contains(formationCollectionOldFormation)) {
                    formationCollectionOldFormation.setIdEquipe(null);
                    formationCollectionOldFormation = em.merge(formationCollectionOldFormation);
                }
            }
            for (Formation formationCollectionNewFormation : formationCollectionNew) {
                if (!formationCollectionOld.contains(formationCollectionNewFormation)) {
                    Equipe oldIdEquipeOfFormationCollectionNewFormation = formationCollectionNewFormation.getIdEquipe();
                    formationCollectionNewFormation.setIdEquipe(equipe);
                    formationCollectionNewFormation = em.merge(formationCollectionNewFormation);
                    if (oldIdEquipeOfFormationCollectionNewFormation != null && !oldIdEquipeOfFormationCollectionNewFormation.equals(equipe)) {
                        oldIdEquipeOfFormationCollectionNewFormation.getFormationCollection().remove(formationCollectionNewFormation);
                        oldIdEquipeOfFormationCollectionNewFormation = em.merge(oldIdEquipeOfFormationCollectionNewFormation);
                    }
                }
            }
            for (Mission missionCollectionOldMission : missionCollectionOld) {
                if (!missionCollectionNew.contains(missionCollectionOldMission)) {
                    missionCollectionOldMission.setIdEquipe(null);
                    missionCollectionOldMission = em.merge(missionCollectionOldMission);
                }
            }
            for (Mission missionCollectionNewMission : missionCollectionNew) {
                if (!missionCollectionOld.contains(missionCollectionNewMission)) {
                    Equipe oldIdEquipeOfMissionCollectionNewMission = missionCollectionNewMission.getIdEquipe();
                    missionCollectionNewMission.setIdEquipe(equipe);
                    missionCollectionNewMission = em.merge(missionCollectionNewMission);
                    if (oldIdEquipeOfMissionCollectionNewMission != null && !oldIdEquipeOfMissionCollectionNewMission.equals(equipe)) {
                        oldIdEquipeOfMissionCollectionNewMission.getMissionCollection().remove(missionCollectionNewMission);
                        oldIdEquipeOfMissionCollectionNewMission = em.merge(oldIdEquipeOfMissionCollectionNewMission);
                    }
                }
            }
            for (Projet projetCollectionNewProjet : projetCollectionNew) {
                if (!projetCollectionOld.contains(projetCollectionNewProjet)) {
                    Equipe oldIdEquipeOfProjetCollectionNewProjet = projetCollectionNewProjet.getIdEquipe();
                    projetCollectionNewProjet.setIdEquipe(equipe);
                    projetCollectionNewProjet = em.merge(projetCollectionNewProjet);
                    if (oldIdEquipeOfProjetCollectionNewProjet != null && !oldIdEquipeOfProjetCollectionNewProjet.equals(equipe)) {
                        oldIdEquipeOfProjetCollectionNewProjet.getProjetCollection().remove(projetCollectionNewProjet);
                        oldIdEquipeOfProjetCollectionNewProjet = em.merge(oldIdEquipeOfProjetCollectionNewProjet);
                    }
                }
            }
            for (Tache tacheCollectionNewTache : tacheCollectionNew) {
                if (!tacheCollectionOld.contains(tacheCollectionNewTache)) {
                    Equipe oldIdEquipeOfTacheCollectionNewTache = tacheCollectionNewTache.getIdEquipe();
                    tacheCollectionNewTache.setIdEquipe(equipe);
                    tacheCollectionNewTache = em.merge(tacheCollectionNewTache);
                    if (oldIdEquipeOfTacheCollectionNewTache != null && !oldIdEquipeOfTacheCollectionNewTache.equals(equipe)) {
                        oldIdEquipeOfTacheCollectionNewTache.getTacheCollection().remove(tacheCollectionNewTache);
                        oldIdEquipeOfTacheCollectionNewTache = em.merge(oldIdEquipeOfTacheCollectionNewTache);
                    }
                }
            }
            for (ChefDeProjet chefDeProjetCollectionOldChefDeProjet : chefDeProjetCollectionOld) {
                if (!chefDeProjetCollectionNew.contains(chefDeProjetCollectionOldChefDeProjet)) {
                    chefDeProjetCollectionOldChefDeProjet.setIdEquipe(null);
                    chefDeProjetCollectionOldChefDeProjet = em.merge(chefDeProjetCollectionOldChefDeProjet);
                }
            }
            for (ChefDeProjet chefDeProjetCollectionNewChefDeProjet : chefDeProjetCollectionNew) {
                if (!chefDeProjetCollectionOld.contains(chefDeProjetCollectionNewChefDeProjet)) {
                    Equipe oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet = chefDeProjetCollectionNewChefDeProjet.getIdEquipe();
                    chefDeProjetCollectionNewChefDeProjet.setIdEquipe(equipe);
                    chefDeProjetCollectionNewChefDeProjet = em.merge(chefDeProjetCollectionNewChefDeProjet);
                    if (oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet != null && !oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet.equals(equipe)) {
                        oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet.getChefDeProjetCollection().remove(chefDeProjetCollectionNewChefDeProjet);
                        oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet = em.merge(oldIdEquipeOfChefDeProjetCollectionNewChefDeProjet);
                    }
                }
            }
            for (Employe employeCollectionOldEmploye : employeCollectionOld) {
                if (!employeCollectionNew.contains(employeCollectionOldEmploye)) {
                    employeCollectionOldEmploye.setIdEquipe(null);
                    employeCollectionOldEmploye = em.merge(employeCollectionOldEmploye);
                }
            }
            for (Employe employeCollectionNewEmploye : employeCollectionNew) {
                if (!employeCollectionOld.contains(employeCollectionNewEmploye)) {
                    Equipe oldIdEquipeOfEmployeCollectionNewEmploye = employeCollectionNewEmploye.getIdEquipe();
                    employeCollectionNewEmploye.setIdEquipe(equipe);
                    employeCollectionNewEmploye = em.merge(employeCollectionNewEmploye);
                    if (oldIdEquipeOfEmployeCollectionNewEmploye != null && !oldIdEquipeOfEmployeCollectionNewEmploye.equals(equipe)) {
                        oldIdEquipeOfEmployeCollectionNewEmploye.getEmployeCollection().remove(employeCollectionNewEmploye);
                        oldIdEquipeOfEmployeCollectionNewEmploye = em.merge(oldIdEquipeOfEmployeCollectionNewEmploye);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getIdEquipe();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
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
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getIdEquipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Projet> projetCollectionOrphanCheck = equipe.getProjetCollection();
            for (Projet projetCollectionOrphanCheckProjet : projetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipe (" + equipe + ") cannot be destroyed since the Projet " + projetCollectionOrphanCheckProjet + " in its projetCollection field has a non-nullable idEquipe field.");
            }
            Collection<Tache> tacheCollectionOrphanCheck = equipe.getTacheCollection();
            for (Tache tacheCollectionOrphanCheckTache : tacheCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipe (" + equipe + ") cannot be destroyed since the Tache " + tacheCollectionOrphanCheckTache + " in its tacheCollection field has a non-nullable idEquipe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Formation> formationCollection = equipe.getFormationCollection();
            for (Formation formationCollectionFormation : formationCollection) {
                formationCollectionFormation.setIdEquipe(null);
                formationCollectionFormation = em.merge(formationCollectionFormation);
            }
            Collection<Mission> missionCollection = equipe.getMissionCollection();
            for (Mission missionCollectionMission : missionCollection) {
                missionCollectionMission.setIdEquipe(null);
                missionCollectionMission = em.merge(missionCollectionMission);
            }
            Collection<ChefDeProjet> chefDeProjetCollection = equipe.getChefDeProjetCollection();
            for (ChefDeProjet chefDeProjetCollectionChefDeProjet : chefDeProjetCollection) {
                chefDeProjetCollectionChefDeProjet.setIdEquipe(null);
                chefDeProjetCollectionChefDeProjet = em.merge(chefDeProjetCollectionChefDeProjet);
            }
            Collection<Employe> employeCollection = equipe.getEmployeCollection();
            for (Employe employeCollectionEmploye : employeCollection) {
                employeCollectionEmploye.setIdEquipe(null);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    @Override
    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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
    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
