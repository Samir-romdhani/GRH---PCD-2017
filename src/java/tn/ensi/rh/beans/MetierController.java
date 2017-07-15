/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.IMetierDao;
import tn.ensi.rh.dao.MetierDaoImp;
import tn.ensi.rh.entities.Metier;

@ManagedBean
@RequestScoped
public class MetierController {

    private List<Metier> metiers;
    private IMetierDao dao;

    public MetierController() {
        dao = new MetierDaoImp();
        metiers = dao.findMetierEntities();
    }

    public List<Metier> getMetiers() {
        return metiers;
    }

    public void setMetiers(List<Metier> metiers) {
        this.metiers = metiers;
    }
}
