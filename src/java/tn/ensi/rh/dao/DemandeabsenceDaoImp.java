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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Compte;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Employe;

/**
 *
 * @author user
 */
public class DemandeabsenceDaoImp implements Serializable, IDemandeabsenceDao {

    private EntityManagerFactory emf;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH/mm/ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public DemandeabsenceDaoImp() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU");
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Demandeabsence demandeabsence) {
        EntityManager em = null;
        try {
            em = getEntityManager();

            demandeabsence.setEtat("ATTENTE");
            Employe userId = new Employe(UserDao.getNom());
            demandeabsence.setIdEmploye(userId);
            String tt = demandeabsence.getType();

            demandeabsence.setDatecreation(sdf.parse(sdf.format(timestamp)));

            //Nombre de jours
            Calendar calendar1 = new GregorianCalendar();
            calendar1.setTime(demandeabsence.getDatedebut());
            Date date1 = calendar1.getTime();
            
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(demandeabsence.getDatefin());
            Date date2 = calendar2.getTime();
            // Différence
            long diff = Math.abs(date2.getTime() - date1.getTime());
            long numberOfDay = (long) diff / 86400000;
            //System.err.println("Le nombre de jour est : " + numberOfDay);
            demandeabsence.setNombredejours((int) numberOfDay + 1);
            
            em.getTransaction().begin();
            /*Employe idEmploye = demandeabsence.getIdEmploye();
            if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getIdEmploye());
                demandeabsence.setIdEmploye(idEmploye);
            }*/
            em.persist(demandeabsence);
            /*if (idEmploye != null) {
                idEmploye.getDemandeabsenceCollection().add(demandeabsence);
                idEmploye = em.merge(idEmploye);
            }*/
            em.getTransaction().commit();
        } catch (ParseException ex) {
            Logger.getLogger(DemandeabsenceDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Demandeabsence demandeabsence) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Demandeabsence dd = em.find(Demandeabsence.class, demandeabsence.getIdDemande());
            
            demandeabsence.setIdEmploye(dd.getIdEmploye());
            demandeabsence.setNombredejours(dd.getNombredejours());
            demandeabsence.setDatedebut(dd.getDatedebut());
            demandeabsence.setDatefin(dd.getDatefin());
            demandeabsence.setDatecreation(dd.getDatecreation());
            demandeabsence.setType(dd.getType());
            demandeabsence.setCommmentaire(dd.getCommmentaire());
            
            String ee = demandeabsence.getEtat() ;
            if("ACCORDE".equals(ee))
            {
                Compte cc = em.find(Compte.class, demandeabsence.getIdEmploye().getIdEmploye());
                cc.setSolde(cc.getSolde()- demandeabsence.getNombredejours());
                
            }

            demandeabsence = em.merge(demandeabsence);
            //em.flush();
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = demandeabsence.getIdDemande();
                if (findDemandeabsence(id) == null) {
                    throw new NonexistentEntityException("The demandeabsence with id " + id + " no longer exists.");
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
    public void edit1(Demandeabsence demandeabsence) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Demandeabsence dd = em.find(Demandeabsence.class, demandeabsence.getIdDemande());
            
            demandeabsence.setIdEmploye(dd.getIdEmploye());
            demandeabsence.setEtat(dd.getEtat());
            
                    //Nombre de jours
            Calendar calendar1 = new GregorianCalendar();
            calendar1.setTime(demandeabsence.getDatedebut());
            Date date1 = calendar1.getTime();
            
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(demandeabsence.getDatefin());
            Date date2 = calendar2.getTime();
            // Différence
            long diff = Math.abs(date2.getTime() - date1.getTime());
            long numberOfDay = (long) diff / 86400000;
            //System.err.println("Le nombre de jour est : " + numberOfDay);
            demandeabsence.setNombredejours((int) numberOfDay + 1);

            demandeabsence = em.merge(demandeabsence);
            //em.flush();
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = demandeabsence.getIdDemande();
                if (findDemandeabsence(id) == null) {
                    throw new NonexistentEntityException("The demandeabsence with id " + id + " no longer exists.");
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
            Demandeabsence demandeabsence;
            try {
                demandeabsence = em.getReference(Demandeabsence.class, id);
                demandeabsence.getIdDemande();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The demandeabsence with id " + id + " no longer exists.", enfe);
            }
            Employe idEmploye = demandeabsence.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getDemandeabsenceCollection().remove(demandeabsence);
                idEmploye = em.merge(idEmploye);
            }
            em.remove(demandeabsence);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Demandeabsence> findDemandeabsenceEntities() {
        return findDemandeabsenceEntities(true, -1, -1);
    }

    @Override
    public List<Demandeabsence> findDemandeabsenceEntities(int maxResults, int firstResult) {
        return findDemandeabsenceEntities(false, maxResults, firstResult);
    }

    private List<Demandeabsence> findDemandeabsenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Demandeabsence.class));
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
    public Demandeabsence findDemandeabsence(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Demandeabsence.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getDemandeabsenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Demandeabsence> rt = cq.from(Demandeabsence.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
