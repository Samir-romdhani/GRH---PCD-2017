/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import tn.ensi.rh.dao.UserDao;
import java.io.Serializable;








import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import tn.ensi.rh.dao.Database;
import static tn.ensi.rh.dao.UserDao.login;
import tn.ensi.rh.entities.Competence;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Demandeformation;
import tn.ensi.rh.entities.Departement;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Evaluation;
import tn.ensi.rh.entities.Formation;
import tn.ensi.rh.entities.Metier;

@ManagedBean(name = "login")
@SessionScoped

public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    private String password;
    private String message, uname;

    private String nom;
    private String prenom;
    private String mail;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Login() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4_2PU");
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }


    /*
    private Employe employes = new Employe(uname, password) ;

    public Employe getEmployes() {
        return employes;
    }

    public void setEmployes(Employe employes) {
        this.employes = employes;
    }
     */
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String loginProject() {
        boolean result = UserDao.login(uname, password);
        if (result && "E100".equals(uname)) {

            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);

            return "template0.xhtml";
        } else if (result) {

            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);

            return "template.xhtml";
        } else {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid Login!",
                            "Please Try Again!"));

            return "login.xhtml";
        }
    }

    public String loginCondidat() {

        return "condidat/login1.xhtml";

    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    /*
    public Departement d1(int id) throws SQLException {
        return UserDao.departement1(id);
    }

    public Metier m1(int id) throws SQLException {
        return UserDao.metier1(id);
    }

    public Formation f1(int id) throws SQLException {
        return UserDao.formation1(id);
    }

    public List<Formation> listformation(String id) throws SQLException {
        return UserDao.listformation(id);
    }

    public List<Formation> listformationetat(String id, String etat) throws SQLException {
        return UserDao.listformationetat(id, etat);
    }

    public Demandeabsence conge(int id) throws SQLException {
        return UserDao.conge(id);
    }

    public List<Demandeabsence> listconge(String id) throws SQLException, ParseException {
        return UserDao.listconge(id);
    }
     */
    public List<Competence> listcompetence(int idF) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT c FROM Competence c WHERE c.idF.idF = :idf");
        queryByFK.setParameter("idf", idF);
        Collection entities = queryByFK.getResultList();
        List<Competence> userList = new ArrayList<>(entities);
        return userList;
    }

    /*
    public List<Competence> listecompetencenote(List<Formation> listeformation) throws SQLException {
        return UserDao.listecompetencenote(listeformation);
    }

    public Evaluation eval1(int id) throws SQLException {
        return UserDao.evaluation1(id);
    }

    public List< Evaluation> listevaluation(int idc, int idf) throws SQLException {
        return UserDao.listevaluation(idc, idf);
    }
     */
    public List<Demandeformation> findDemandeFormationUser(String Userid) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT f FROM Demandeformation f WHERE f.idEmploye.idEmploye = :user and f.etat != 'ACCORDE' order by f.dateCreation desc ");
        queryByFK.setParameter("user", Userid);
        Collection entities = queryByFK.getResultList();
        List<Demandeformation> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeformation> findDemandeFormationUser(String Userid, String etat) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT f FROM Demandeformation f WHERE f.idEmploye.idEmploye = :user and f.etat = :etats order by f.dateFormation desc ");
        queryByFK.setParameter("user", Userid);
        queryByFK.setParameter("etats", etat);
        Collection entities = queryByFK.getResultList();
        List<Demandeformation> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Evaluation> findEvaluationUser(Integer idf) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT ev FROM Evaluation ev WHERE ev.idF = :idff");
        queryByFK.setParameter("idff", idf);
        Collection entities = queryByFK.getResultList();
        List<Evaluation> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeabsence> ListCongeEnEtat(String etat) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeabsence d WHERE d.etat = :etats order by d.datecreation desc ");
        queryByFK.setParameter("etats", etat);
        Collection entities = queryByFK.getResultList();
        List<Demandeabsence> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeabsence> ListCongeEmploye(String idEmploye) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeabsence d WHERE d.idEmploye.idEmploye = :id and d.etat != 'ACCORDE'");
        queryByFK.setParameter("id", idEmploye);
        Collection entities = queryByFK.getResultList();
        List<Demandeabsence> userList = new ArrayList<>(entities);
        return userList;
    }
    

    public List<Demandeabsence> getdemandecal(String iddemande) {
        Integer idd = Integer.parseInt(iddemande );
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeabsence d WHERE d.idDemande = :id");
        queryByFK.setParameter("id", idd);
        Collection entities = queryByFK.getResultList();
        List<Demandeabsence> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeabsence> ListCongeEmploye(String idEmploye, String etat) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeabsence d WHERE d.idEmploye.idEmploye = :id and d.etat = :etats");
        queryByFK.setParameter("id", idEmploye);
        queryByFK.setParameter("etats", etat);
        Collection entities = queryByFK.getResultList();
        List<Demandeabsence> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeabsence> Demande(Integer iddemande) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeabsence d WHERE d.idDemande = :id");
        queryByFK.setParameter("id", iddemande);
        Collection entities = queryByFK.getResultList();
        List<Demandeabsence> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Demandeformation> ListFormationEnEtat(String etat) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT d FROM Demandeformation d WHERE d.etat = :etats");
        queryByFK.setParameter("etats", etat);
        Collection entities = queryByFK.getResultList();
        List<Demandeformation> userList = new ArrayList<>(entities);
        return userList;
    }

    public List<Evaluation> Listevaluation(Integer idf) {
        EntityManager em = getEntityManager();
        Query queryByFK = em.createQuery("SELECT e FROM Evaluation e WHERE e.idF = :idFs");
        queryByFK.setParameter("idFs", idf);
        Collection entities = queryByFK.getResultList();
        List<Evaluation> userList = new ArrayList<>(entities);
        return userList;
    }

}
