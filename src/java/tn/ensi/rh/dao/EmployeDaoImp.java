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
import tn.ensi.rh.entities.Compte;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Equipe;
import tn.ensi.rh.entities.Departement;
import tn.ensi.rh.entities.Metier;
import tn.ensi.rh.entities.Demandeformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.beans.CompteController;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.dao.exceptions.PreexistingEntityException;
import tn.ensi.rh.entities.Evaluation;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Competence;
import tn.ensi.rh.entities.ChefDeProjet;

/**
 * -
 *
 * @author user
 */
public class EmployeDaoImp implements Serializable, IEmployeDao {

    private EntityManagerFactory emf;

    public EmployeDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU");
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Employe employe) throws PreexistingEntityException, Exception {
        /*
        if (employe.getDemandeformationCollection() == null) {
            employe.setDemandeformationCollection(new ArrayList<Demandeformation>());
        }
        if (employe.getEvaluationCollection() == null) {
            employe.setEvaluationCollection(new ArrayList<Evaluation>());
        }
        if (employe.getDemandeabsenceCollection() == null) {
            employe.setDemandeabsenceCollection(new ArrayList<Demandeabsence>());
        }
        if (employe.getCompetenceCollection() == null) {
            employe.setCompetenceCollection(new ArrayList<Competence>());
        }
        if (employe.getChefDeProjetCollection() == null) {
            employe.setChefDeProjetCollection(new ArrayList<ChefDeProjet>());
        }
        if (employe.getEmployeCollection() == null) {
            employe.setEmployeCollection(new ArrayList<Employe>());
        }
         */
        EntityManager em = null;
        try {
            em = getEntityManager();
            employe.setChef(new Employe("E100"));
            employe.setIdDepartement(null);
            employe.setIdEquipe(null);
            employe.setIdentificationDuPoste(null);

            em.getTransaction().begin();
            /*
            Compte compte = employe.getCompte();
            if (compte != null) {
                compte = em.getReference(compte.getClass(), compte.getIdEmploye());
                employe.setCompte(compte);
            }
            Employe chef = employe.getChef();
            if (chef != null) {
                chef = em.getReference(chef.getClass(), chef.getIdEmploye());
                employe.setChef(chef);
            }
            Equipe idEquipe = employe.getIdEquipe();
            if (idEquipe != null) {
                idEquipe = em.getReference(idEquipe.getClass(), idEquipe.getIdEquipe());
                employe.setIdEquipe(idEquipe);
            }
            Departement idDepartement = employe.getIdDepartement();
            if (idDepartement != null) {
                idDepartement = em.getReference(idDepartement.getClass(), idDepartement.getIdDepartement());
                employe.setIdDepartement(idDepartement);
            }
            Metier identificationDuPoste = employe.getIdentificationDuPoste();
            if (identificationDuPoste != null) {
                identificationDuPoste = em.getReference(identificationDuPoste.getClass(), identificationDuPoste.getIdentificationDuPoste());
                employe.setIdentificationDuPoste(identificationDuPoste);
            }
            Collection<Demandeformation> attachedDemandeformationCollection = new ArrayList<Demandeformation>();
            for (Demandeformation demandeformationCollectionDemandeformationToAttach : employe.getDemandeformationCollection()) {
                demandeformationCollectionDemandeformationToAttach = em.getReference(demandeformationCollectionDemandeformationToAttach.getClass(), demandeformationCollectionDemandeformationToAttach.getIdF());
                attachedDemandeformationCollection.add(demandeformationCollectionDemandeformationToAttach);
            }
            employe.setDemandeformationCollection(attachedDemandeformationCollection);
            Collection<Evaluation> attachedEvaluationCollection = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionEvaluationToAttach : employe.getEvaluationCollection()) {
                evaluationCollectionEvaluationToAttach = em.getReference(evaluationCollectionEvaluationToAttach.getClass(), evaluationCollectionEvaluationToAttach.getIdEval());
                attachedEvaluationCollection.add(evaluationCollectionEvaluationToAttach);
            }
            employe.setEvaluationCollection(attachedEvaluationCollection);
            Collection<Demandeabsence> attachedDemandeabsenceCollection = new ArrayList<Demandeabsence>();
            for (Demandeabsence demandeabsenceCollectionDemandeabsenceToAttach : employe.getDemandeabsenceCollection()) {
                demandeabsenceCollectionDemandeabsenceToAttach = em.getReference(demandeabsenceCollectionDemandeabsenceToAttach.getClass(), demandeabsenceCollectionDemandeabsenceToAttach.getIdDemande());
                attachedDemandeabsenceCollection.add(demandeabsenceCollectionDemandeabsenceToAttach);
            }
            employe.setDemandeabsenceCollection(attachedDemandeabsenceCollection);
            Collection<Competence> attachedCompetenceCollection = new ArrayList<Competence>();
            for (Competence competenceCollectionCompetenceToAttach : employe.getCompetenceCollection()) {
                competenceCollectionCompetenceToAttach = em.getReference(competenceCollectionCompetenceToAttach.getClass(), competenceCollectionCompetenceToAttach.getIdC());
                attachedCompetenceCollection.add(competenceCollectionCompetenceToAttach);
            }
            employe.setCompetenceCollection(attachedCompetenceCollection);
            Collection<ChefDeProjet> attachedChefDeProjetCollection = new ArrayList<ChefDeProjet>();
            for (ChefDeProjet chefDeProjetCollectionChefDeProjetToAttach : employe.getChefDeProjetCollection()) {
                chefDeProjetCollectionChefDeProjetToAttach = em.getReference(chefDeProjetCollectionChefDeProjetToAttach.getClass(), chefDeProjetCollectionChefDeProjetToAttach.getIdChefDeProjet());
                attachedChefDeProjetCollection.add(chefDeProjetCollectionChefDeProjetToAttach);
            }
            employe.setChefDeProjetCollection(attachedChefDeProjetCollection);
            Collection<Employe> attachedEmployeCollection = new ArrayList<Employe>();
            for (Employe employeCollectionEmployeToAttach : employe.getEmployeCollection()) {
                employeCollectionEmployeToAttach = em.getReference(employeCollectionEmployeToAttach.getClass(), employeCollectionEmployeToAttach.getIdEmploye());
                attachedEmployeCollection.add(employeCollectionEmployeToAttach);
            }
            employe.setEmployeCollection(attachedEmployeCollection);
             */
            em.persist(employe);
            /*
            if (compte != null) {
                Employe oldEmployeOfCompte = compte.getEmploye();
                if (oldEmployeOfCompte != null) {
                    oldEmployeOfCompte.setCompte(null);
                    oldEmployeOfCompte = em.merge(oldEmployeOfCompte);
                }
                compte.setEmploye(employe);
                compte = em.merge(compte);
            }
            if (chef != null) {
                chef.getEmployeCollection().add(employe);
                chef = em.merge(chef);
            }
            if (idEquipe != null) {
                idEquipe.getEmployeCollection().add(employe);
                idEquipe = em.merge(idEquipe);
            }
            if (idDepartement != null) {
                idDepartement.getEmployeCollection().add(employe);
                idDepartement = em.merge(idDepartement);
            }
            if (identificationDuPoste != null) {
                identificationDuPoste.getEmployeCollection().add(employe);
                identificationDuPoste = em.merge(identificationDuPoste);
            }
            for (Demandeformation demandeformationCollectionDemandeformation : employe.getDemandeformationCollection()) {
                Employe oldIdEmployeOfDemandeformationCollectionDemandeformation = demandeformationCollectionDemandeformation.getIdEmploye();
                demandeformationCollectionDemandeformation.setIdEmploye(employe);
                demandeformationCollectionDemandeformation = em.merge(demandeformationCollectionDemandeformation);
                if (oldIdEmployeOfDemandeformationCollectionDemandeformation != null) {
                    oldIdEmployeOfDemandeformationCollectionDemandeformation.getDemandeformationCollection().remove(demandeformationCollectionDemandeformation);
                    oldIdEmployeOfDemandeformationCollectionDemandeformation = em.merge(oldIdEmployeOfDemandeformationCollectionDemandeformation);
                }
            }
            for (Evaluation evaluationCollectionEvaluation : employe.getEvaluationCollection()) {
                Employe oldIdEmployeOfEvaluationCollectionEvaluation = evaluationCollectionEvaluation.getIdEmploye();
                evaluationCollectionEvaluation.setIdEmploye(employe);
                evaluationCollectionEvaluation = em.merge(evaluationCollectionEvaluation);
                if (oldIdEmployeOfEvaluationCollectionEvaluation != null) {
                    oldIdEmployeOfEvaluationCollectionEvaluation.getEvaluationCollection().remove(evaluationCollectionEvaluation);
                    oldIdEmployeOfEvaluationCollectionEvaluation = em.merge(oldIdEmployeOfEvaluationCollectionEvaluation);
                }
            }
            for (Demandeabsence demandeabsenceCollectionDemandeabsence : employe.getDemandeabsenceCollection()) {
                Employe oldIdEmployeOfDemandeabsenceCollectionDemandeabsence = demandeabsenceCollectionDemandeabsence.getIdEmploye();
                demandeabsenceCollectionDemandeabsence.setIdEmploye(employe);
                demandeabsenceCollectionDemandeabsence = em.merge(demandeabsenceCollectionDemandeabsence);
                if (oldIdEmployeOfDemandeabsenceCollectionDemandeabsence != null) {
                    oldIdEmployeOfDemandeabsenceCollectionDemandeabsence.getDemandeabsenceCollection().remove(demandeabsenceCollectionDemandeabsence);
                    oldIdEmployeOfDemandeabsenceCollectionDemandeabsence = em.merge(oldIdEmployeOfDemandeabsenceCollectionDemandeabsence);
                }
            }
            for (Competence competenceCollectionCompetence : employe.getCompetenceCollection()) {
                Employe oldIdEmployeOfCompetenceCollectionCompetence = competenceCollectionCompetence.getIdEmploye();
                competenceCollectionCompetence.setIdEmploye(employe);
                competenceCollectionCompetence = em.merge(competenceCollectionCompetence);
                if (oldIdEmployeOfCompetenceCollectionCompetence != null) {
                    oldIdEmployeOfCompetenceCollectionCompetence.getCompetenceCollection().remove(competenceCollectionCompetence);
                    oldIdEmployeOfCompetenceCollectionCompetence = em.merge(oldIdEmployeOfCompetenceCollectionCompetence);
                }
            }
            for (ChefDeProjet chefDeProjetCollectionChefDeProjet : employe.getChefDeProjetCollection()) {
                Employe oldIdEmployeOfChefDeProjetCollectionChefDeProjet = chefDeProjetCollectionChefDeProjet.getIdEmploye();
                chefDeProjetCollectionChefDeProjet.setIdEmploye(employe);
                chefDeProjetCollectionChefDeProjet = em.merge(chefDeProjetCollectionChefDeProjet);
                if (oldIdEmployeOfChefDeProjetCollectionChefDeProjet != null) {
                    oldIdEmployeOfChefDeProjetCollectionChefDeProjet.getChefDeProjetCollection().remove(chefDeProjetCollectionChefDeProjet);
                    oldIdEmployeOfChefDeProjetCollectionChefDeProjet = em.merge(oldIdEmployeOfChefDeProjetCollectionChefDeProjet);
                }
            }
            for (Employe employeCollectionEmploye : employe.getEmployeCollection()) {
                Employe oldChefOfEmployeCollectionEmploye = employeCollectionEmploye.getChef();
                employeCollectionEmploye.setChef(employe);
                employeCollectionEmploye = em.merge(employeCollectionEmploye);
                if (oldChefOfEmployeCollectionEmploye != null) {
                    oldChefOfEmployeCollectionEmploye.getEmployeCollection().remove(employeCollectionEmploye);
                    oldChefOfEmployeCollectionEmploye = em.merge(oldChefOfEmployeCollectionEmploye);
                }
            }
             */
            em.getTransaction().commit();

        } catch (Exception ex) {
            if (findEmploye(employe.getIdEmploye()) != null) {
                throw new PreexistingEntityException("Employe " + employe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit1(Employe employe) {
        getEntityManager().merge(employe);
    }

    @Override
    public void edit(Employe employe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employe persistentEmploye = em.find(Employe.class, employe.getIdEmploye());
            // Query e = em.createNamedQuery("") ;
            //Employe chef = em.find(Employe.class, employe.getChef().getIdEmploye());

            /*
            Employe chefOld = persistentEmploye.getChef();
            String idchefNew = employe.getChef().getIdEmploye();
            if (!"".equals(idchefNew)) {
                Employe chef = em.find(Employe.class, idchefNew);
                //chefNew = em.getReference(chefNew.getClass(), chefNew.getIdEmploye());
                employe.setChef(chef);
            }else{
            employe.setChef(persistentEmploye.getChef());
            }
             */
            employe.setChef(persistentEmploye.getChef());
            employe.setIdDepartement(persistentEmploye.getIdDepartement());
            employe.setIdEquipe(persistentEmploye.getIdEquipe());
            employe.setIdentificationDuPoste(persistentEmploye.getIdentificationDuPoste());

            employe = em.merge(employe);

            /*
            Compte compteOld = persistentEmploye.getCompte();
            Compte compteNew = employe.getCompte();
            Employe chefOld = persistentEmploye.getChef();
            Employe chefNew = employe.getChef();
            Equipe idEquipeOld = persistentEmploye.getIdEquipe();
            Equipe idEquipeNew = employe.getIdEquipe();
            Departement idDepartementOld = persistentEmploye.getIdDepartement();
            Departement idDepartementNew = employe.getIdDepartement();
            Metier identificationDuPosteOld = persistentEmploye.getIdentificationDuPoste();
            Metier identificationDuPosteNew = employe.getIdentificationDuPoste();
            Collection<Demandeformation> demandeformationCollectionOld = persistentEmploye.getDemandeformationCollection();
            Collection<Demandeformation> demandeformationCollectionNew = employe.getDemandeformationCollection();
            Collection<Evaluation> evaluationCollectionOld = persistentEmploye.getEvaluationCollection();
            Collection<Evaluation> evaluationCollectionNew = employe.getEvaluationCollection();
            Collection<Demandeabsence> demandeabsenceCollectionOld = persistentEmploye.getDemandeabsenceCollection();
            Collection<Demandeabsence> demandeabsenceCollectionNew = employe.getDemandeabsenceCollection();
            Collection<Competence> competenceCollectionOld = persistentEmploye.getCompetenceCollection();
            Collection<Competence> competenceCollectionNew = employe.getCompetenceCollection();
            Collection<ChefDeProjet> chefDeProjetCollectionOld = persistentEmploye.getChefDeProjetCollection();
            Collection<ChefDeProjet> chefDeProjetCollectionNew = employe.getChefDeProjetCollection();
            Collection<Employe> employeCollectionOld = persistentEmploye.getEmployeCollection();
            Collection<Employe> employeCollectionNew = employe.getEmployeCollection();
            List<String> illegalOrphanMessages = null;
            if (compteOld != null && !compteOld.equals(compteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Compte " + compteOld + " since its employe field is not nullable.");
            }
            for (Evaluation evaluationCollectionOldEvaluation : evaluationCollectionOld) {
                if (!evaluationCollectionNew.contains(evaluationCollectionOldEvaluation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluation " + evaluationCollectionOldEvaluation + " since its idEmploye field is not nullable.");
                }
            }
            for (ChefDeProjet chefDeProjetCollectionOldChefDeProjet : chefDeProjetCollectionOld) {
                if (!chefDeProjetCollectionNew.contains(chefDeProjetCollectionOldChefDeProjet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ChefDeProjet " + chefDeProjetCollectionOldChefDeProjet + " since its idEmploye field is not nullable.");
                }
            }
            for (Employe employeCollectionOldEmploye : employeCollectionOld) {
                if (!employeCollectionNew.contains(employeCollectionOldEmploye)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employe " + employeCollectionOldEmploye + " since its chef field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (compteNew != null) {
                compteNew = em.getReference(compteNew.getClass(), compteNew.getIdEmploye());
                employe.setCompte(compteNew);
            }
            if (chefNew != null) {
                chefNew = em.getReference(chefNew.getClass(), chefNew.getIdEmploye());
                employe.setChef(chefNew);
            }
            if (idEquipeNew != null) {
                idEquipeNew = em.getReference(idEquipeNew.getClass(), idEquipeNew.getIdEquipe());
                employe.setIdEquipe(idEquipeNew);
            }
            if (idDepartementNew != null) {
                idDepartementNew = em.getReference(idDepartementNew.getClass(), idDepartementNew.getIdDepartement());
                employe.setIdDepartement(idDepartementNew);
            }
            if (identificationDuPosteNew != null) {
                identificationDuPosteNew = em.getReference(identificationDuPosteNew.getClass(), identificationDuPosteNew.getIdentificationDuPoste());
                employe.setIdentificationDuPoste(identificationDuPosteNew);
            }
            Collection<Demandeformation> attachedDemandeformationCollectionNew = new ArrayList<Demandeformation>();
            for (Demandeformation demandeformationCollectionNewDemandeformationToAttach : demandeformationCollectionNew) {
                demandeformationCollectionNewDemandeformationToAttach = em.getReference(demandeformationCollectionNewDemandeformationToAttach.getClass(), demandeformationCollectionNewDemandeformationToAttach.getIdF());
                attachedDemandeformationCollectionNew.add(demandeformationCollectionNewDemandeformationToAttach);
            }
            demandeformationCollectionNew = attachedDemandeformationCollectionNew;
            employe.setDemandeformationCollection(demandeformationCollectionNew);
            Collection<Evaluation> attachedEvaluationCollectionNew = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionNewEvaluationToAttach : evaluationCollectionNew) {
                evaluationCollectionNewEvaluationToAttach = em.getReference(evaluationCollectionNewEvaluationToAttach.getClass(), evaluationCollectionNewEvaluationToAttach.getIdEval());
                attachedEvaluationCollectionNew.add(evaluationCollectionNewEvaluationToAttach);
            }
            evaluationCollectionNew = attachedEvaluationCollectionNew;
            employe.setEvaluationCollection(evaluationCollectionNew);
            Collection<Demandeabsence> attachedDemandeabsenceCollectionNew = new ArrayList<Demandeabsence>();
            for (Demandeabsence demandeabsenceCollectionNewDemandeabsenceToAttach : demandeabsenceCollectionNew) {
                demandeabsenceCollectionNewDemandeabsenceToAttach = em.getReference(demandeabsenceCollectionNewDemandeabsenceToAttach.getClass(), demandeabsenceCollectionNewDemandeabsenceToAttach.getIdDemande());
                attachedDemandeabsenceCollectionNew.add(demandeabsenceCollectionNewDemandeabsenceToAttach);
            }
            demandeabsenceCollectionNew = attachedDemandeabsenceCollectionNew;
            employe.setDemandeabsenceCollection(demandeabsenceCollectionNew);
            Collection<Competence> attachedCompetenceCollectionNew = new ArrayList<Competence>();
            for (Competence competenceCollectionNewCompetenceToAttach : competenceCollectionNew) {
                competenceCollectionNewCompetenceToAttach = em.getReference(competenceCollectionNewCompetenceToAttach.getClass(), competenceCollectionNewCompetenceToAttach.getIdC());
                attachedCompetenceCollectionNew.add(competenceCollectionNewCompetenceToAttach);
            }
            competenceCollectionNew = attachedCompetenceCollectionNew;
            employe.setCompetenceCollection(competenceCollectionNew);
            Collection<ChefDeProjet> attachedChefDeProjetCollectionNew = new ArrayList<ChefDeProjet>();
            for (ChefDeProjet chefDeProjetCollectionNewChefDeProjetToAttach : chefDeProjetCollectionNew) {
                chefDeProjetCollectionNewChefDeProjetToAttach = em.getReference(chefDeProjetCollectionNewChefDeProjetToAttach.getClass(), chefDeProjetCollectionNewChefDeProjetToAttach.getIdChefDeProjet());
                attachedChefDeProjetCollectionNew.add(chefDeProjetCollectionNewChefDeProjetToAttach);
            }
            chefDeProjetCollectionNew = attachedChefDeProjetCollectionNew;
            employe.setChefDeProjetCollection(chefDeProjetCollectionNew);
            Collection<Employe> attachedEmployeCollectionNew = new ArrayList<Employe>();
            for (Employe employeCollectionNewEmployeToAttach : employeCollectionNew) {
                employeCollectionNewEmployeToAttach = em.getReference(employeCollectionNewEmployeToAttach.getClass(), employeCollectionNewEmployeToAttach.getIdEmploye());
                attachedEmployeCollectionNew.add(employeCollectionNewEmployeToAttach);
            }
            employeCollectionNew = attachedEmployeCollectionNew;
            employe.setEmployeCollection(employeCollectionNew);
            employe = em.merge(employe);
            if (compteNew != null && !compteNew.equals(compteOld)) {
                Employe oldEmployeOfCompte = compteNew.getEmploye();
                if (oldEmployeOfCompte != null) {
                    oldEmployeOfCompte.setCompte(null);
                    oldEmployeOfCompte = em.merge(oldEmployeOfCompte);
                }
                compteNew.setEmploye(employe);
                compteNew = em.merge(compteNew);
            }
            if (chefOld != null && !chefOld.equals(chefNew)) {
                chefOld.getEmployeCollection().remove(employe);
                chefOld = em.merge(chefOld);
            }
            if (chefNew != null && !chefNew.equals(chefOld)) {
                chefNew.getEmployeCollection().add(employe);
                chefNew = em.merge(chefNew);
            }
            if (idEquipeOld != null && !idEquipeOld.equals(idEquipeNew)) {
                idEquipeOld.getEmployeCollection().remove(employe);
                idEquipeOld = em.merge(idEquipeOld);
            }
            if (idEquipeNew != null && !idEquipeNew.equals(idEquipeOld)) {
                idEquipeNew.getEmployeCollection().add(employe);
                idEquipeNew = em.merge(idEquipeNew);
            }
            if (idDepartementOld != null && !idDepartementOld.equals(idDepartementNew)) {
                idDepartementOld.getEmployeCollection().remove(employe);
                idDepartementOld = em.merge(idDepartementOld);
            }
            if (idDepartementNew != null && !idDepartementNew.equals(idDepartementOld)) {
                idDepartementNew.getEmployeCollection().add(employe);
                idDepartementNew = em.merge(idDepartementNew);
            }
            if (identificationDuPosteOld != null && !identificationDuPosteOld.equals(identificationDuPosteNew)) {
                identificationDuPosteOld.getEmployeCollection().remove(employe);
                identificationDuPosteOld = em.merge(identificationDuPosteOld);
            }
            if (identificationDuPosteNew != null && !identificationDuPosteNew.equals(identificationDuPosteOld)) {
                identificationDuPosteNew.getEmployeCollection().add(employe);
                identificationDuPosteNew = em.merge(identificationDuPosteNew);
            }
            for (Demandeformation demandeformationCollectionOldDemandeformation : demandeformationCollectionOld) {
                if (!demandeformationCollectionNew.contains(demandeformationCollectionOldDemandeformation)) {
                    demandeformationCollectionOldDemandeformation.setIdEmploye(null);
                    demandeformationCollectionOldDemandeformation = em.merge(demandeformationCollectionOldDemandeformation);
                }
            }
            for (Demandeformation demandeformationCollectionNewDemandeformation : demandeformationCollectionNew) {
                if (!demandeformationCollectionOld.contains(demandeformationCollectionNewDemandeformation)) {
                    Employe oldIdEmployeOfDemandeformationCollectionNewDemandeformation = demandeformationCollectionNewDemandeformation.getIdEmploye();
                    demandeformationCollectionNewDemandeformation.setIdEmploye(employe);
                    demandeformationCollectionNewDemandeformation = em.merge(demandeformationCollectionNewDemandeformation);
                    if (oldIdEmployeOfDemandeformationCollectionNewDemandeformation != null && !oldIdEmployeOfDemandeformationCollectionNewDemandeformation.equals(employe)) {
                        oldIdEmployeOfDemandeformationCollectionNewDemandeformation.getDemandeformationCollection().remove(demandeformationCollectionNewDemandeformation);
                        oldIdEmployeOfDemandeformationCollectionNewDemandeformation = em.merge(oldIdEmployeOfDemandeformationCollectionNewDemandeformation);
                    }
                }
            }
            for (Evaluation evaluationCollectionNewEvaluation : evaluationCollectionNew) {
                if (!evaluationCollectionOld.contains(evaluationCollectionNewEvaluation)) {
                    Employe oldIdEmployeOfEvaluationCollectionNewEvaluation = evaluationCollectionNewEvaluation.getIdEmploye();
                    evaluationCollectionNewEvaluation.setIdEmploye(employe);
                    evaluationCollectionNewEvaluation = em.merge(evaluationCollectionNewEvaluation);
                    if (oldIdEmployeOfEvaluationCollectionNewEvaluation != null && !oldIdEmployeOfEvaluationCollectionNewEvaluation.equals(employe)) {
                        oldIdEmployeOfEvaluationCollectionNewEvaluation.getEvaluationCollection().remove(evaluationCollectionNewEvaluation);
                        oldIdEmployeOfEvaluationCollectionNewEvaluation = em.merge(oldIdEmployeOfEvaluationCollectionNewEvaluation);
                    }
                }
            }
            for (Demandeabsence demandeabsenceCollectionOldDemandeabsence : demandeabsenceCollectionOld) {
                if (!demandeabsenceCollectionNew.contains(demandeabsenceCollectionOldDemandeabsence)) {
                    demandeabsenceCollectionOldDemandeabsence.setIdEmploye(null);
                    demandeabsenceCollectionOldDemandeabsence = em.merge(demandeabsenceCollectionOldDemandeabsence);
                }
            }
            for (Demandeabsence demandeabsenceCollectionNewDemandeabsence : demandeabsenceCollectionNew) {
                if (!demandeabsenceCollectionOld.contains(demandeabsenceCollectionNewDemandeabsence)) {
                    Employe oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence = demandeabsenceCollectionNewDemandeabsence.getIdEmploye();
                    demandeabsenceCollectionNewDemandeabsence.setIdEmploye(employe);
                    demandeabsenceCollectionNewDemandeabsence = em.merge(demandeabsenceCollectionNewDemandeabsence);
                    if (oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence != null && !oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence.equals(employe)) {
                        oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence.getDemandeabsenceCollection().remove(demandeabsenceCollectionNewDemandeabsence);
                        oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence = em.merge(oldIdEmployeOfDemandeabsenceCollectionNewDemandeabsence);
                    }
                }
            }
            for (Competence competenceCollectionOldCompetence : competenceCollectionOld) {
                if (!competenceCollectionNew.contains(competenceCollectionOldCompetence)) {
                    competenceCollectionOldCompetence.setIdEmploye(null);
                    competenceCollectionOldCompetence = em.merge(competenceCollectionOldCompetence);
                }
            }
            for (Competence competenceCollectionNewCompetence : competenceCollectionNew) {
                if (!competenceCollectionOld.contains(competenceCollectionNewCompetence)) {
                    Employe oldIdEmployeOfCompetenceCollectionNewCompetence = competenceCollectionNewCompetence.getIdEmploye();
                    competenceCollectionNewCompetence.setIdEmploye(employe);
                    competenceCollectionNewCompetence = em.merge(competenceCollectionNewCompetence);
                    if (oldIdEmployeOfCompetenceCollectionNewCompetence != null && !oldIdEmployeOfCompetenceCollectionNewCompetence.equals(employe)) {
                        oldIdEmployeOfCompetenceCollectionNewCompetence.getCompetenceCollection().remove(competenceCollectionNewCompetence);
                        oldIdEmployeOfCompetenceCollectionNewCompetence = em.merge(oldIdEmployeOfCompetenceCollectionNewCompetence);
                    }
                }
            }
            for (ChefDeProjet chefDeProjetCollectionNewChefDeProjet : chefDeProjetCollectionNew) {
                if (!chefDeProjetCollectionOld.contains(chefDeProjetCollectionNewChefDeProjet)) {
                    Employe oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet = chefDeProjetCollectionNewChefDeProjet.getIdEmploye();
                    chefDeProjetCollectionNewChefDeProjet.setIdEmploye(employe);
                    chefDeProjetCollectionNewChefDeProjet = em.merge(chefDeProjetCollectionNewChefDeProjet);
                    if (oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet != null && !oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet.equals(employe)) {
                        oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet.getChefDeProjetCollection().remove(chefDeProjetCollectionNewChefDeProjet);
                        oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet = em.merge(oldIdEmployeOfChefDeProjetCollectionNewChefDeProjet);
                    }
                }
            }
            for (Employe employeCollectionNewEmploye : employeCollectionNew) {
                if (!employeCollectionOld.contains(employeCollectionNewEmploye)) {
                    Employe oldChefOfEmployeCollectionNewEmploye = employeCollectionNewEmploye.getChef();
                    employeCollectionNewEmploye.setChef(employe);
                    employeCollectionNewEmploye = em.merge(employeCollectionNewEmploye);
                    if (oldChefOfEmployeCollectionNewEmploye != null && !oldChefOfEmployeCollectionNewEmploye.equals(employe)) {
                        oldChefOfEmployeCollectionNewEmploye.getEmployeCollection().remove(employeCollectionNewEmploye);
                        oldChefOfEmployeCollectionNewEmploye = em.merge(oldChefOfEmployeCollectionNewEmploye);
                    }
                }
            }
             */
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = employe.getIdEmploye();
                if (findEmploye(id) == null) {
                    throw new NonexistentEntityException("The employe with id " + id + " no longer exists.");
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
    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            /*
            Employe ee = em.find(Employe.class, id);
            ee.setChef(null);
            ee.setIdEquipe(null);
            ee.setCompte(null);
            ee.setIdentificationDuPoste(null);
            ee.setIdDepartement(null);
            */

            Employe employe;
            try {
                employe = em.getReference(Employe.class, id);
                employe.getIdEmploye();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employe with id " + id + " no longer exists.", enfe);
            }
            
            List<String> illegalOrphanMessages = null;
            
            Compte compteOrphanCheck = employe.getCompte();
            if (compteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employe (" + employe + ") cannot be destroyed since the Compte " + compteOrphanCheck + " in its compte field has a non-nullable employe field.");
            }
            Collection<Evaluation> evaluationCollectionOrphanCheck = employe.getEvaluationCollection();
            for (Evaluation evaluationCollectionOrphanCheckEvaluation : evaluationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employe (" + employe + ") cannot be destroyed since the Evaluation " + evaluationCollectionOrphanCheckEvaluation + " in its evaluationCollection field has a non-nullable idEmploye field.");
            }
            Collection<ChefDeProjet> chefDeProjetCollectionOrphanCheck = employe.getChefDeProjetCollection();
            for (ChefDeProjet chefDeProjetCollectionOrphanCheckChefDeProjet : chefDeProjetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employe (" + employe + ") cannot be destroyed since the ChefDeProjet " + chefDeProjetCollectionOrphanCheckChefDeProjet + " in its chefDeProjetCollection field has a non-nullable idEmploye field.");
            }
            Collection<Employe> employeCollectionOrphanCheck = employe.getEmployeCollection();
            for (Employe employeCollectionOrphanCheckEmploye : employeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employe (" + employe + ") cannot be destroyed since the Employe " + employeCollectionOrphanCheckEmploye + " in its employeCollection field has a non-nullable chef field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employe chef = employe.getChef();
            if (chef != null) {
                chef.getEmployeCollection().remove(employe);
                chef = em.merge(chef);
            }
            Equipe idEquipe = employe.getIdEquipe();
            if (idEquipe != null) {
                idEquipe.getEmployeCollection().remove(employe);
                idEquipe = em.merge(idEquipe);
            }
            Departement idDepartement = employe.getIdDepartement();
            if (idDepartement != null) {
                idDepartement.getEmployeCollection().remove(employe);
                idDepartement = em.merge(idDepartement);
            }
            Metier identificationDuPoste = employe.getIdentificationDuPoste();
            if (identificationDuPoste != null) {
                identificationDuPoste.getEmployeCollection().remove(employe);
                identificationDuPoste = em.merge(identificationDuPoste);
            }
            Collection<Demandeformation> demandeformationCollection = employe.getDemandeformationCollection();
            for (Demandeformation demandeformationCollectionDemandeformation : demandeformationCollection) {
                demandeformationCollectionDemandeformation.setIdEmploye(null);
                demandeformationCollectionDemandeformation = em.merge(demandeformationCollectionDemandeformation);
            }
            Collection<Demandeabsence> demandeabsenceCollection = employe.getDemandeabsenceCollection();
            for (Demandeabsence demandeabsenceCollectionDemandeabsence : demandeabsenceCollection) {
                demandeabsenceCollectionDemandeabsence.setIdEmploye(null);
                demandeabsenceCollectionDemandeabsence = em.merge(demandeabsenceCollectionDemandeabsence);
            }
            Collection<Competence> competenceCollection = employe.getCompetenceCollection();
            for (Competence competenceCollectionCompetence : competenceCollection) {
                competenceCollectionCompetence.setIdEmploye(null);
                competenceCollectionCompetence = em.merge(competenceCollectionCompetence);
            }
             
            em.remove(employe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     @Override
    public void edit2(Employe employe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employe persistentEmploye = em.find(Employe.class, employe.getIdEmploye());
            // Query e = em.createNamedQuery("") ;
            //Employe chef = em.find(Employe.class, employe.getChef().getIdEmploye());

            /*
            Employe chefOld = persistentEmploye.getChef();
            String idchefNew = employe.getChef().getIdEmploye();
            if (!"".equals(idchefNew)) {
                Employe chef = em.find(Employe.class, idchefNew);
                //chefNew = em.getReference(chefNew.getClass(), chefNew.getIdEmploye());
                employe.setChef(chef);
            }else{
            employe.setChef(persistentEmploye.getChef());
            }
             */
          
            employe.setChefDeProjetCollection(null);
            employe.setCompetenceCollection(null);
            employe.setDatedenaissance(null);
            employe.setDatedetravail(null);
            employe.setAdresse(null);
            employe.setDemandeabsenceCollection(null);
            employe.setEmployeCollection(null);
            employe.setEvaluationCollection(null);
           
            employe.setCompte(null);
            employe.setChef(null);
            employe.setIdDepartement(null);
            employe.setIdEquipe(null);
            employe.setIdentificationDuPoste(null);

            employe = em.merge(employe);
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = employe.getIdEmploye();
                if (findEmploye(id) == null) {
                    throw new NonexistentEntityException("The employe with id " + id + " no longer exists.");
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
    public void destroy2(Employe emp) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            /*Employe ee = em.find(Employe.class, emp.getIdEmploye());
            
            ee.setChef(null);
            ee.setIdEquipe(null);
            ee.setCompte(null);
            ee.setIdentificationDuPoste(null);
            ee.setIdDepartement(null);
            
               emp.setChef(null);
            emp.setIdEquipe(null);
            emp.setCompte(null);
            emp.setIdentificationDuPoste(null);
            emp.setIdDepartement(null);
             
*/
            em.remove(emp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Employe> findEmployeEntities() {
        return findEmployeEntities(true, -1, -1);
    }

    @Override
    public List<Employe> findEmployeEntities(int maxResults, int firstResult) {
        return findEmployeEntities(false, maxResults, firstResult);
    }

    private List<Employe> findEmployeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employe.class));
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
    public Employe findEmploye(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employe.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getEmployeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employe> rt = cq.from(Employe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
