/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.ChefDeProjetDaoImp;
import tn.ensi.rh.dao.IChefDeProjetDao;
import tn.ensi.rh.entities.ChefDeProjet;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class ChefDeProjetController {

    private List<ChefDeProjet> chefs ;
    private IChefDeProjetDao dao ;
    
    public ChefDeProjetController() {
        dao = new ChefDeProjetDaoImp() ;
        chefs = dao.findChefDeProjetEntities() ;
    }

    public List<ChefDeProjet> getChefs() {
        return chefs;
    }

    public void setChefs(List<ChefDeProjet> chefs) {
        this.chefs = chefs;
    }
    
}
