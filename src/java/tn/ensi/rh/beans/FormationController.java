/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.FormationDaoImp;
import tn.ensi.rh.dao.IFormationDao;
import tn.ensi.rh.entities.Formation;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class FormationController {

    private  List<Formation> formations ;
    private  IFormationDao dao ;
    public FormationController() {
        dao = new FormationDaoImp() ;
        formations = dao.findFormationEntities() ;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }
    
}
