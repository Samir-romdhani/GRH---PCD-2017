/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.EquipeDaoImp;
import tn.ensi.rh.dao.IEquipeDao;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Equipe;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class Equipecontroller {

     private List<Equipe> equipes;
     private  Equipe equipe ;
    private IEquipeDao dao;
    
    private List<Employe> employes;
   
    

   
    public Equipecontroller() {
         dao = new EquipeDaoImp();
        equipes = dao.findEquipeEntities();
        
        equipe = new Equipe();
       
       
        //for (int i = 0; i < equipes.size(); i++) {
          //  eventModel.addEvent(new DefaultScheduleEvent(Demandeconges.get(i).getIdEmploye().getNom(),
          //          Demandeconges.get(i).getDatedebut(), Demandeconges.get(i).getDatefin()));
        //}
    }
    public List<Employe> ListeEmployeEquipe(Equipe e) {
        Collection entities = e.getEmployeCollection();
       employes = new ArrayList<>(entities);
       return employes ;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    

       
}
