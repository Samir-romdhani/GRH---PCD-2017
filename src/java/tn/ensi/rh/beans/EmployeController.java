/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.CompteDaoImp;
import tn.ensi.rh.dao.EmployeDaoImp;
import tn.ensi.rh.dao.ICompteDao;
import tn.ensi.rh.dao.IEmployeDao;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Compte;
import tn.ensi.rh.entities.Employe;

/**
 *
 * @author user
 * @Named("employeController")
 * @SessionScoped
 */
@ManagedBean
@RequestScoped
public class EmployeController {

    private List<Employe> employes;
    private List<Employe> chefs;
    private IEmployeDao dao;
    private Employe employe;
    private Employe user;
    private Employe SelectedEmploye = null;
    private Employe selectedView = null;

    //Compte
    private ICompteDao dao1;
    private Compte compte;

    private Login dao2;
    //List Chefs
    private List<String> ListChefs = new ArrayList<>();

    public EmployeController() {
        dao = new EmployeDaoImp();
        employes = dao.findEmployeEntities();
        chefs = dao.findEmployeEntities();

        employe = new Employe();

        dao2 = new Login();
        user = new Employe(dao2.getUname());

        //Compte
        dao1 = new CompteDaoImp();

        //ListChefs
        for (int i = 0; i < chefs.size(); i++) {
            ListChefs.add(chefs.get(i).getIdEmploye());
        }
        //Edit
        SelectedEmploye = new Employe();
        selectedView = new Employe();

    }

    public List<Employe> getItemsAvailableSelectOne() {
        return dao.findEmployeEntities();
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    //Getter Setter   compte
    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    //Getter Seter ListChefs et chefs

    public List<Employe> getChefs() {
        return chefs;
    }

    public void setChefs(List<Employe> chefs) {
        this.chefs = chefs;
    }

    public List<String> getListChefs() {
        return ListChefs;
    }

    public void setListChefs(List<String> ListChefs) {
        this.ListChefs = ListChefs;
    }

    public Employe getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(Employe selectedView) {
        this.selectedView = selectedView;
    }

    //User
    public Employe getUser() {
        return user;
    }

    public void setUser(Employe user) {
        this.user = user;
    }

    //
    public String recruter() {
        try {
            dao.create(employe);
            employes = dao.findEmployeEntities();
            //Compte associé
            compte = new Compte(employe.getIdEmploye(), 30);
            dao1.create(compte);
        } catch (Exception ex) {
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    //VIEW
    public String View(Employe e) {
        selectedView = e;
        return "view.xhtml";
    }

    //EDIT
    //Getter Setter
    public Employe getSelectedEmploye() {
        return SelectedEmploye;
    }

    public void setSelectedEmploye(Employe SelectedEmploye) {
        this.SelectedEmploye = SelectedEmploye;
    }

    public String Select(Employe e) {
        SelectedEmploye = e;
        return "edit.xhtml";
    }

    public String edit2(Employe e) throws Exception {
        SelectedEmploye = e;
        try {
            dao.edit2(SelectedEmploye);
           // dao.destroy2(SelectedEmploye);
            employes = dao.findEmployeEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String delete2(Employe ee) throws IllegalOrphanException, Exception {

        try {
            //Compte associé

            dao.destroy2(ee);
            employes = dao.findEmployeEntities();

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String delete(String id) throws IllegalOrphanException {
        try {

            //Compte associé
            dao.destroy(id);
            employes = dao.findEmployeEntities();

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String edit() throws Exception {
        try {
            dao.edit(SelectedEmploye);
            employes = dao.findEmployeEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String edit1() throws Exception {
        dao.edit1(SelectedEmploye);
        employes = dao.findEmployeEntities();
        return "List.xhtml";
    }

}
