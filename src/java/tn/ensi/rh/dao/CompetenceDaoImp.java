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
import tn.ensi.rh.entities.Demandeformation;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Evaluation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Competence;

/**
 *
 * @author user
 */
public class CompetenceDaoImp implements Serializable, ICompetenceDao {

        private EntityManagerFactory emf;

    public CompetenceDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Competence competence) {
        if (competence.getEvaluationCollection() == null) {
            competence.setEvaluationCollection(new ArrayList<Evaluation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Demandeformation idF = competence.getIdF();
            if (idF != null) {
                idF = em.getReference(idF.getClass(), idF.getIdF());
                competence.setIdF(idF);
            }
            Employe idEmploye = competence.getIdEmploye();
            if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getIdEmploye());
                competence.setIdEmploye(idEmploye);
            }
            Collection<Evaluation> attachedEvaluationCollection = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionEvaluationToAttach : competence.getEvaluationCollection()) {
                evaluationCollectionEvaluationToAttach = em.getReference(evaluationCollectionEvaluationToAttach.getClass(), evaluationCollectionEvaluationToAttach.getIdEval());
                attachedEvaluationCollection.add(evaluationCollectionEvaluationToAttach);
            }
            competence.setEvaluationCollection(attachedEvaluationCollection);
            em.persist(competence);
            if (idF != null) {
                idF.getCompetenceCollection().add(competence);
                idF = em.merge(idF);
            }
            if (idEmploye != null) {
                idEmploye.getCompetenceCollection().add(competence);
                idEmploye = em.merge(idEmploye);
            }
            for (Evaluation evaluationCollectionEvaluation : competence.getEvaluationCollection()) {
                Competence oldIdCOfEvaluationCollectionEvaluation = evaluationCollectionEvaluation.getIdC();
                evaluationCollectionEvaluation.setIdC(competence);
                evaluationCollectionEvaluation = em.merge(evaluationCollectionEvaluation);
                if (oldIdCOfEvaluationCollectionEvaluation != null) {
                    oldIdCOfEvaluationCollectionEvaluation.getEvaluationCollection().remove(evaluationCollectionEvaluation);
                    oldIdCOfEvaluationCollectionEvaluation = em.merge(oldIdCOfEvaluationCollectionEvaluation);
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
    public void edit(Competence competence) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Competence persistentCompetence = em.find(Competence.class, competence.getIdC());
            Demandeformation idFOld = persistentCompetence.getIdF();
            Demandeformation idFNew = competence.getIdF();
            Employe idEmployeOld = persistentCompetence.getIdEmploye();
            Employe idEmployeNew = competence.getIdEmploye();
            Collection<Evaluation> evaluationCollectionOld = persistentCompetence.getEvaluationCollection();
            Collection<Evaluation> evaluationCollectionNew = competence.getEvaluationCollection();
            List<String> illegalOrphanMessages = null;
            for (Evaluation evaluationCollectionOldEvaluation : evaluationCollectionOld) {
                if (!evaluationCollectionNew.contains(evaluationCollectionOldEvaluation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluation " + evaluationCollectionOldEvaluation + " since its idC field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFNew != null) {
                idFNew = em.getReference(idFNew.getClass(), idFNew.getIdF());
                competence.setIdF(idFNew);
            }
            if (idEmployeNew != null) {
                idEmployeNew = em.getReference(idEmployeNew.getClass(), idEmployeNew.getIdEmploye());
                competence.setIdEmploye(idEmployeNew);
            }
            Collection<Evaluation> attachedEvaluationCollectionNew = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionNewEvaluationToAttach : evaluationCollectionNew) {
                evaluationCollectionNewEvaluationToAttach = em.getReference(evaluationCollectionNewEvaluationToAttach.getClass(), evaluationCollectionNewEvaluationToAttach.getIdEval());
                attachedEvaluationCollectionNew.add(evaluationCollectionNewEvaluationToAttach);
            }
            evaluationCollectionNew = attachedEvaluationCollectionNew;
            competence.setEvaluationCollection(evaluationCollectionNew);
            competence = em.merge(competence);
            if (idFOld != null && !idFOld.equals(idFNew)) {
                idFOld.getCompetenceCollection().remove(competence);
                idFOld = em.merge(idFOld);
            }
            if (idFNew != null && !idFNew.equals(idFOld)) {
                idFNew.getCompetenceCollection().add(competence);
                idFNew = em.merge(idFNew);
            }
            if (idEmployeOld != null && !idEmployeOld.equals(idEmployeNew)) {
                idEmployeOld.getCompetenceCollection().remove(competence);
                idEmployeOld = em.merge(idEmployeOld);
            }
            if (idEmployeNew != null && !idEmployeNew.equals(idEmployeOld)) {
                idEmployeNew.getCompetenceCollection().add(competence);
                idEmployeNew = em.merge(idEmployeNew);
            }
            for (Evaluation evaluationCollectionNewEvaluation : evaluationCollectionNew) {
                if (!evaluationCollectionOld.contains(evaluationCollectionNewEvaluation)) {
                    Competence oldIdCOfEvaluationCollectionNewEvaluation = evaluationCollectionNewEvaluation.getIdC();
                    evaluationCollectionNewEvaluation.setIdC(competence);
                    evaluationCollectionNewEvaluation = em.merge(evaluationCollectionNewEvaluation);
                    if (oldIdCOfEvaluationCollectionNewEvaluation != null && !oldIdCOfEvaluationCollectionNewEvaluation.equals(competence)) {
                        oldIdCOfEvaluationCollectionNewEvaluation.getEvaluationCollection().remove(evaluationCollectionNewEvaluation);
                        oldIdCOfEvaluationCollectionNewEvaluation = em.merge(oldIdCOfEvaluationCollectionNewEvaluation);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = competence.getIdC();
                if (findCompetence(id) == null) {
                    throw new NonexistentEntityException("The competence with id " + id + " no longer exists.");
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
            Competence competence;
            try {
                competence = em.getReference(Competence.class, id);
                competence.getIdC();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The competence with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Evaluation> evaluationCollectionOrphanCheck = competence.getEvaluationCollection();
            for (Evaluation evaluationCollectionOrphanCheckEvaluation : evaluationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Competence (" + competence + ") cannot be destroyed since the Evaluation " + evaluationCollectionOrphanCheckEvaluation + " in its evaluationCollection field has a non-nullable idC field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Demandeformation idF = competence.getIdF();
            if (idF != null) {
                idF.getCompetenceCollection().remove(competence);
                idF = em.merge(idF);
            }
            Employe idEmploye = competence.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getCompetenceCollection().remove(competence);
                idEmploye = em.merge(idEmploye);
            }
            em.remove(competence);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Competence> findCompetenceEntities() {
        return findCompetenceEntities(true, -1, -1);
    }

    @Override
    public List<Competence> findCompetenceEntities(int maxResults, int firstResult) {
        return findCompetenceEntities(false, maxResults, firstResult);
    }

    private List<Competence> findCompetenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Competence.class));
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
    public Competence findCompetence(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Competence.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getCompetenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Competence> rt = cq.from(Competence.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
