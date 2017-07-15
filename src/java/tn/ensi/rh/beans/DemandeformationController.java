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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import tn.ensi.rh.dao.DemandeformationDaoImp;
import tn.ensi.rh.dao.IDemandeformationDao;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Demandeformation;

@ManagedBean
@RequestScoped
public class DemandeformationController {

    private List<Demandeformation> demandes;
    private IDemandeformationDao dao;
    Demandeformation mademande = new Demandeformation();

    private Demandeformation selected = null;
    private Demandeformation selectedView;
    private List<String> etat;

    private Login dao1;

    private List<Demandeformation> formationATTENTE;
    private List<Demandeformation> formationREFUS;
    private List<Demandeformation> formationACCORDE;

    public DemandeformationController() {
        dao = new DemandeformationDaoImp();
        demandes = dao.findDemandeformationEntities();

        selected = new Demandeformation();
        //selectedView = new Demandeformation();

        dao1 = new Login();
        formationATTENTE = dao1.ListFormationEnEtat("ATTENTE");
        formationREFUS = dao1.ListFormationEnEtat("REFUS");
        formationACCORDE = dao1.ListFormationEnEtat("ACCORDE");

        etat = new ArrayList<>();
        etat.add("ATTENTE");
        etat.add("REFUS");
        etat.add("ACCORDE");

    }

    public Demandeformation getMademande() {
        return mademande;
    }

    public void setMademande(Demandeformation mademande) {
        this.mademande = mademande;
    }

    public List<Demandeformation> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demandeformation> demandes) {
        this.demandes = demandes;
    }

    public Demandeformation getSelected() {
        return selected;
    }

    public void setSelected(Demandeformation selected) {
        this.selected = selected;
    }

    public List<String> getEtat() {
        return etat;
    }

    public void setEtat(List<String> etat) {
        this.etat = etat;
    }

    public List<Demandeformation> getFormationATTENTE() {
        return formationATTENTE;
    }

    public void setFormationATTENTE(List<Demandeformation> formationATTENTE) {
        this.formationATTENTE = formationATTENTE;
    }

    public List<Demandeformation> getFormationREFUS() {
        return formationREFUS;
    }

    public void setFormationREFUS(List<Demandeformation> formationREFUS) {
        this.formationREFUS = formationREFUS;
    }

    public List<Demandeformation> getFormationACCORDE() {
        return formationACCORDE;
    }

    public void setFormationACCORDE(List<Demandeformation> formationACCORDE) {
        this.formationACCORDE = formationACCORDE;
    }

    public Demandeformation getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(Demandeformation selectedView) {
        this.selectedView = selectedView;
    }

    //
    public String poster() {
        try {
            dao.create(mademande);
            demandes = dao.findDemandeformationEntities();
        } catch (Exception ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }

    public String delete(Integer id) throws IllegalOrphanException {
        try {
            dao.destroy(id);
            demandes = dao.findDemandeformationEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String delete1(Integer id) throws IllegalOrphanException {
        try {
            dao.destroy(id);
            demandes = dao.findDemandeformationEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }

    public String PrepareEdit(Demandeformation e) {
        selected = e;
        return "edit.xhtml";
    }

    public String Select(Demandeformation e) {
        selected = e;
        return "edit1.xhtml";
    }

    public String View(Demandeformation e) {
        selectedView = e;
        return "view.xhtml";
    }

    public String edit() throws Exception {
        try {
            dao.edit(selected);
            demandes = dao.findDemandeformationEntities();
            formationATTENTE = dao1.ListFormationEnEtat("ATTENTE");
            formationREFUS = dao1.ListFormationEnEtat("REFUS");
            formationACCORDE = dao1.ListFormationEnEtat("ACCORDE");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }

    public String edit1() throws Exception {
        try {
            dao.edit1(selected);
            demandes = dao.findDemandeformationEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Demande Selected", ((Demandeformation) event.getObject()).getIdF().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
