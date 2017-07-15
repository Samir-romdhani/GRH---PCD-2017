/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.CompteDaoImp;
import tn.ensi.rh.dao.ICompteDao;
import tn.ensi.rh.entities.Compte;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class CompteController {

    private List<Compte> comptes;
    private ICompteDao dao;
    
    private  Compte compte ;
    
    public CompteController() { 
        dao = new CompteDaoImp();
       comptes  = dao.findCompteEntities();
       
       compte = new Compte() ;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    
    public String CreateCompte() {
        try {
            dao.create(compte);
            comptes = dao.findCompteEntities();
        } catch (Exception ex) {
            Logger.getLogger(Compte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "compte.xhtml";
    }
    
    
}
