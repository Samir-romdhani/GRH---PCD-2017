/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Evaluation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Competence;
import tn.ensi.rh.entities.Demandeformation;

/**
 *
 * @author user
 */
public class DemandeformationDaoImp implements Serializable, IDemandeformationDao {

    private EntityManagerFactory emf;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public DemandeformationDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU");
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Demandeformation demandeformation) {
        /*
        if (demandeformation.getEvaluationCollection() == null) {
            demandeformation.setEvaluationCollection(new ArrayList<Evaluation>());
        }
        if (demandeformation.getCompetenceCollection() == null) {
            demandeformation.setCompetenceCollection(new ArrayList<Competence>());
        }
         */
        EntityManager em = null;
        try {
            em = getEntityManager();
            demandeformation.setEtat("ATTENTE");
            Employe userId = new Employe(UserDao.getNom());
            demandeformation.setIdEmploye(userId);
            demandeformation.setDateCreation(sdf.parse(sdf.format(timestamp)));
            

            em.getTransaction().begin();

            /*if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getIdEmploye());
                demandeformation.setIdEmploye(idEmploye);
            }
            Collection<Evaluation> attachedEvaluationCollection = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionEvaluationToAttach : demandeformation.getEvaluationCollection()) {
                evaluationCollectionEvaluationToAttach = em.getReference(evaluationCollectionEvaluationToAttach.getClass(), evaluationCollectionEvaluationToAttach.getIdEval());
                attachedEvaluationCollection.add(evaluationCollectionEvaluationToAttach);
            }
            demandeformation.setEvaluationCollection(attachedEvaluationCollection);
            Collection<Competence> attachedCompetenceCollection = new ArrayList<Competence>();
            for (Competence competenceCollectionCompetenceToAttach : demandeformation.getCompetenceCollection()) {
                competenceCollectionCompetenceToAttach = em.getReference(competenceCollectionCompetenceToAttach.getClass(), competenceCollectionCompetenceToAttach.getIdC());
                attachedCompetenceCollection.add(competenceCollectionCompetenceToAttach);
            }
            demandeformation.setCompetenceCollection(attachedCompetenceCollection);
            em.persist(demandeformation);
            if (idEmploye != null) {
                idEmploye.getDemandeformationCollection().add(demandeformation);
                idEmploye = em.merge(idEmploye);
            }
            for (Evaluation evaluationCollectionEvaluation : demandeformation.getEvaluationCollection()) {
                Demandeformation oldIdFOfEvaluationCollectionEvaluation = evaluationCollectionEvaluation.getIdF();
                evaluationCollectionEvaluation.setIdF(demandeformation);
                evaluationCollectionEvaluation = em.merge(evaluationCollectionEvaluation);
                if (oldIdFOfEvaluationCollectionEvaluation != null) {
                    oldIdFOfEvaluationCollectionEvaluation.getEvaluationCollection().remove(evaluationCollectionEvaluation);
                    oldIdFOfEvaluationCollectionEvaluation = em.merge(oldIdFOfEvaluationCollectionEvaluation);
                }
            }
            for (Competence competenceCollectionCompetence : demandeformation.getCompetenceCollection()) {
                Demandeformation oldIdFOfCompetenceCollectionCompetence = competenceCollectionCompetence.getIdF();
                competenceCollectionCompetence.setIdF(demandeformation);
                competenceCollectionCompetence = em.merge(competenceCollectionCompetence);
                if (oldIdFOfCompetenceCollectionCompetence != null) {
                    oldIdFOfCompetenceCollectionCompetence.getCompetenceCollection().remove(competenceCollectionCompetence);
                    oldIdFOfCompetenceCollectionCompetence = em.merge(oldIdFOfCompetenceCollectionCompetence);
                }
            }*/
            em.persist(demandeformation);
            em.getTransaction().commit();
        } catch (ParseException ex) {
            Logger.getLogger(DemandeformationDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Demandeformation demandeformation) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Demandeformation dd = em.find(Demandeformation.class, demandeformation.getIdF());
            demandeformation.setIdEmploye(dd.getIdEmploye());
            demandeformation.setDateCreation(dd.getDateCreation());
            demandeformation.setLibelle(dd.getLibelle());
 
            demandeformation = em.merge(demandeformation);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = demandeformation.getIdF();
                if (findDemandeformation(id) == null) {
                    throw new NonexistentEntityException("The demandeformation with id " + id + " no longer exists.");
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
    public void edit1(Demandeformation demandeformation) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Demandeformation persistentDemandeformation = em.find(Demandeformation.class, demandeformation.getIdF());
            demandeformation.setIdEmploye(persistentDemandeformation.getIdEmploye());
            demandeformation.setDateCreation(persistentDemandeformation.getDateCreation());
            demandeformation.setEtat(persistentDemandeformation.getEtat()) ;
                    
            demandeformation = em.merge(demandeformation);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = demandeformation.getIdF();
                if (findDemandeformation(id) == null) {
                    throw new NonexistentEntityException("The demandeformation with id " + id + " no longer exists.");
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
            Demandeformation demandeformation;
            try {
                demandeformation = em.getReference(Demandeformation.class, id);
                demandeformation.getIdF();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The demandeformation with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Evaluation> evaluationCollectionOrphanCheck = demandeformation.getEvaluationCollection();
            for (Evaluation evaluationCollectionOrphanCheckEvaluation : evaluationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Demandeformation (" + demandeformation + ") cannot be destroyed since the Evaluation " + evaluationCollectionOrphanCheckEvaluation + " in its evaluationCollection field has a non-nullable idF field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employe idEmploye = demandeformation.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getDemandeformationCollection().remove(demandeformation);
                idEmploye = em.merge(idEmploye);
            }
            Collection<Competence> competenceCollection = demandeformation.getCompetenceCollection();
            for (Competence competenceCollectionCompetence : competenceCollection) {
                competenceCollectionCompetence.setIdF(null);
                competenceCollectionCompetence = em.merge(competenceCollectionCompetence);
            }
            em.remove(demandeformation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Demandeformation> findDemandeformationEntities() {
        return findDemandeformationEntities(true, -1, -1);
    }

    @Override
    public List<Demandeformation> findDemandeformationEntities(int maxResults, int firstResult) {
        return findDemandeformationEntities(false, maxResults, firstResult);
    }

    private List<Demandeformation> findDemandeformationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Demandeformation.class));
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
    public Demandeformation findDemandeformation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Demandeformation.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getDemandeformationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Demandeformation> rt = cq.from(Demandeformation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
