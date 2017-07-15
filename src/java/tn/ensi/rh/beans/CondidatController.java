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
import tn.ensi.rh.dao.CondidatDaoImp;
import tn.ensi.rh.dao.ICondidatDao;
import tn.ensi.rh.entities.Condidat;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class CondidatController {

    private List<Condidat> condidats;
    private ICondidatDao dao ;
    private Condidat condidat ; 

    public CondidatController() {
        dao = new CondidatDaoImp();
        condidats = dao.findCondidatEntities();
        condidat = new Condidat() ;
    }

    public List<Condidat> getCondidats() {
        return condidats;
    }

    public void setCondidats(List<Condidat> condidats) {
        this.condidats = condidats;
    }

    public Condidat getCondidat() {
        return condidat;
    }

    public void setCondidat(Condidat condidat) {
        this.condidat = condidat;
    }
    
    
    public String poster() {
        try {
            dao.create(condidat);
            condidats = dao.findCondidatEntities();
        } catch (Exception ex) {
            Logger.getLogger(Condidat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "condidat/envoimail.xhtml";
    }
    

}
