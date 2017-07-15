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
import tn.ensi.rh.entities.Competence;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Demandeformation;
import tn.ensi.rh.entities.Evaluation;

/**
 *
 * @author user
 */
public class EvaluationDaoImp implements Serializable, IEvaluationDao {

        private EntityManagerFactory emf;

    public EvaluationDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU") ;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Evaluation evaluation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Competence idC = evaluation.getIdC();
            if (idC != null) {
                idC = em.getReference(idC.getClass(), idC.getIdC());
                evaluation.setIdC(idC);
            }
            Employe idEmploye = evaluation.getIdEmploye();
            if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getIdEmploye());
                evaluation.setIdEmploye(idEmploye);
            }
            Demandeformation idF = evaluation.getIdF();
            if (idF != null) {
                idF = em.getReference(idF.getClass(), idF.getIdF());
                evaluation.setIdF(idF);
            }
            em.persist(evaluation);
            if (idC != null) {
                idC.getEvaluationCollection().add(evaluation);
                idC = em.merge(idC);
            }
            if (idEmploye != null) {
                idEmploye.getEvaluationCollection().add(evaluation);
                idEmploye = em.merge(idEmploye);
            }
            if (idF != null) {
                idF.getEvaluationCollection().add(evaluation);
                idF = em.merge(idF);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Evaluation evaluation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluation persistentEvaluation = em.find(Evaluation.class, evaluation.getIdEval());
            Competence idCOld = persistentEvaluation.getIdC();
            Competence idCNew = evaluation.getIdC();
            Employe idEmployeOld = persistentEvaluation.getIdEmploye();
            Employe idEmployeNew = evaluation.getIdEmploye();
            Demandeformation idFOld = persistentEvaluation.getIdF();
            Demandeformation idFNew = evaluation.getIdF();
            if (idCNew != null) {
                idCNew = em.getReference(idCNew.getClass(), idCNew.getIdC());
                evaluation.setIdC(idCNew);
            }
            if (idEmployeNew != null) {
                idEmployeNew = em.getReference(idEmployeNew.getClass(), idEmployeNew.getIdEmploye());
                evaluation.setIdEmploye(idEmployeNew);
            }
            if (idFNew != null) {
                idFNew = em.getReference(idFNew.getClass(), idFNew.getIdF());
                evaluation.setIdF(idFNew);
            }
            evaluation = em.merge(evaluation);
            if (idCOld != null && !idCOld.equals(idCNew)) {
                idCOld.getEvaluationCollection().remove(evaluation);
                idCOld = em.merge(idCOld);
            }
            if (idCNew != null && !idCNew.equals(idCOld)) {
                idCNew.getEvaluationCollection().add(evaluation);
                idCNew = em.merge(idCNew);
            }
            if (idEmployeOld != null && !idEmployeOld.equals(idEmployeNew)) {
                idEmployeOld.getEvaluationCollection().remove(evaluation);
                idEmployeOld = em.merge(idEmployeOld);
            }
            if (idEmployeNew != null && !idEmployeNew.equals(idEmployeOld)) {
                idEmployeNew.getEvaluationCollection().add(evaluation);
                idEmployeNew = em.merge(idEmployeNew);
            }
            if (idFOld != null && !idFOld.equals(idFNew)) {
                idFOld.getEvaluationCollection().remove(evaluation);
                idFOld = em.merge(idFOld);
            }
            if (idFNew != null && !idFNew.equals(idFOld)) {
                idFNew.getEvaluationCollection().add(evaluation);
                idFNew = em.merge(idFNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluation.getIdEval();
                if (findEvaluation(id) == null) {
                    throw new NonexistentEntityException("The evaluation with id " + id + " no longer exists.");
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
            Evaluation evaluation;
            try {
                evaluation = em.getReference(Evaluation.class, id);
                evaluation.getIdEval();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluation with id " + id + " no longer exists.", enfe);
            }
            Competence idC = evaluation.getIdC();
            if (idC != null) {
                idC.getEvaluationCollection().remove(evaluation);
                idC = em.merge(idC);
            }
            Employe idEmploye = evaluation.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getEvaluationCollection().remove(evaluation);
                idEmploye = em.merge(idEmploye);
            }
            Demandeformation idF = evaluation.getIdF();
            if (idF != null) {
                idF.getEvaluationCollection().remove(evaluation);
                idF = em.merge(idF);
            }
            em.remove(evaluation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Evaluation> findEvaluationEntities() {
        return findEvaluationEntities(true, -1, -1);
    }

    @Override
    public List<Evaluation> findEvaluationEntities(int maxResults, int firstResult) {
        return findEvaluationEntities(false, maxResults, firstResult);
    }

    private List<Evaluation> findEvaluationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evaluation.class));
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
    public Evaluation findEvaluation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evaluation.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getEvaluationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evaluation> rt = cq.from(Evaluation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
