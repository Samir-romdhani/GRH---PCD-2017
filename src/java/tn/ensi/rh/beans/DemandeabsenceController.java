/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.showcase.view.data.ScheduleView;
import tn.ensi.rh.dao.DemandeabsenceDaoImp;
import tn.ensi.rh.dao.IDemandeabsenceDao;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Employe;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class DemandeabsenceController {

    //
    private List<Demandeabsence> conges;
    private IDemandeabsenceDao dao;
    private List<String> etat = new ArrayList<>();
    
    //Liste type congé
    private List<String> typesconge = new ArrayList<>();
    
    private Demandeabsence demandecal ;
    private List<Demandeabsence> listdemandecal;

    //
    private Demandeabsence mademande = new Demandeabsence();
    private Demandeabsence selected = null;
    private Employe selectedEmploye = null ;
    private Demandeabsence selectedView ;

    //List Congés avec leurs etats
    private Login dao1;
    private List<Demandeabsence> congesATTENTE;
    private List<Demandeabsence> congesREFUS;
    private List<Demandeabsence> congesACCORDE;
    private List<Demandeabsence> congeEmploye ;

    //Calendrier
    private ScheduleView cal;
    private ScheduleModel eventModel;


    public DemandeabsenceController() {
        dao = new DemandeabsenceDaoImp();
        conges = dao.findDemandeabsenceEntities();

        selected = new Demandeabsence();
        //selectedView = new Demandeabsence();
        
        demandecal = new Demandeabsence();
        listdemandecal = new ArrayList() ;
        
       
        
        selectedEmploye = new Employe();

        dao1 = new Login();
        congesATTENTE = dao1.ListCongeEnEtat("ATTENTE");
        congesREFUS = dao1.ListCongeEnEtat("REFUS");
        congesACCORDE = dao1.ListCongeEnEtat("ACCORDE");
        congeEmploye = dao1.ListCongeEmploye("E100");
        
        etat.add("ATTENTE");
        etat.add("REFUS");
        etat.add("ACCORDE");
        
        typesconge.add(" ");
        typesconge.add("événements familiaux");
        typesconge.add("Voyage");
        typesconge.add("Maternité/Paternité");
        typesconge.add("proche aidant");
                
        
        /*
        eventModel = new DefaultScheduleModel();
        for(int i=0; i<conges.size(); i++) 
        eventModel.addEvent(new DefaultScheduleEvent(conges.get(i).getIdEmploye().getNom(),
                conges.get(i).getDatedebut(),conges.get(i).getDatefin()));
         */
    }

    public List<String> getEtat() {
        return etat;
    }

    public void setEtat(List<String> etat) {
        this.etat = etat;
    }

    public Demandeabsence getMademande() {
        return mademande;
    }

    public void setMademande(Demandeabsence mademande) {
        this.mademande = mademande;
    }

    public Demandeabsence getSelected() {
        return selected;
    }

    public void setSelected(Demandeabsence selected) {
        this.selected = selected;
    }

    public List<Demandeabsence> getConges() {
        return conges;
    }

    public void setConges(List<Demandeabsence> conge) {
        this.conges = conge;
    }

    public List<Demandeabsence> getCongesATTENTE() {
        return congesATTENTE;
    }

    public void setCongesATTENTE(List<Demandeabsence> congesATTENTE) {
        this.congesATTENTE = congesATTENTE;
    }

    public List<Demandeabsence> getCongesREFUS() {
        return congesREFUS;
    }

    public void setCongesREFUS(List<Demandeabsence> congesREFUS) {
        this.congesREFUS = congesREFUS;
    }

    public List<Demandeabsence> getCongesACCORDE() {
        return congesACCORDE;
    }

    public void setCongesACCORDE(List<Demandeabsence> congesACCORDE) {
        this.congesACCORDE = congesACCORDE;
    }

    public Demandeabsence getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(Demandeabsence selectedView) {
        this.selectedView = selectedView;
    }

    public List<String> getTypesconge() {
        return typesconge;
    }

    public void setTypesconge(List<String> typesconge) {
        this.typesconge = typesconge;
    }

    public List<Demandeabsence> getCongeEmploye() {
        return congeEmploye;
    }

    public void setCongeEmploye(List<Demandeabsence> congeEmploye) {
        this.congeEmploye = congeEmploye;
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }

    public Demandeabsence getDemandecal() {
        return demandecal;
    }

    public void setDemandecal(Demandeabsence demandecal) {
        this.demandecal = demandecal;
    }

    public List<Demandeabsence> getListdemandecal() {
        return listdemandecal;
    }

    public void setListdemandecal(List<Demandeabsence> listdemandecal) {
        this.listdemandecal = listdemandecal;
    }

  


    
    
    
    

    public String poster() {
        try {
            dao.create(mademande);
            conges = dao.findDemandeabsenceEntities();
        } catch (Exception ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }

    public String delete(Integer id) {
        try {
            dao.destroy(id);
            conges = dao.findDemandeabsenceEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }
        public String delete1(Integer id) {
        try {
            dao.destroy(id);
            conges = dao.findDemandeabsenceEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }

    public String prepareedit(Integer id) {
        selected = new Demandeabsence(id);
        return "edit.xhtml";
    }

    //EDIT
    public String Select(Demandeabsence e) {
        selected = e;
        return "edit.xhtml";
    }
    public String PrepareEdit(Demandeabsence e) {
        selected = e;
        return "edit.xhtml";
    }
    public String PrepareEditAdm (Demandeabsence e) {
        selected = e;
        return "modifierAdm.xhtml";
    }
    //VIEW
    public String View(Demandeabsence e) {
        selectedView = e;
        selectedEmploye = e.getIdEmploye() ;
        return "view.xhtml";
    }    

    public String edit() throws Exception {
        try {
            dao.edit1(selected);
            conges = dao.findDemandeabsenceEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "List.xhtml";
    }
    
     public String edit1() throws Exception {
        try {
            dao.edit1(selected);
            conges = dao.findDemandeabsenceEntities();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "mesdemandes.xhtml";
    }
      public String editAdmin() throws Exception {
        try {
            dao.edit(selected);
           // conges = dao.findDemandeabsenceEntities();
        congesATTENTE = dao1.ListCongeEnEtat("ATTENTE");
        congesREFUS = dao1.ListCongeEnEtat("REFUS");
        congesACCORDE = dao1.ListCongeEnEtat("ACCORDE");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Demandeabsence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "gererconges.xhtml";
    }
      
      public Demandeabsence getdemandecal(String id){
          listdemandecal = dao1.getdemandecal(id);
          demandecal = listdemandecal.get(0); 
          //demandecal = dao.findDemandeabsence(id);
           return demandecal;
    }
      
      

      
      public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Demande Selected", ((Demandeabsence) event.getObject()).getIdDemande().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
      
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
}
