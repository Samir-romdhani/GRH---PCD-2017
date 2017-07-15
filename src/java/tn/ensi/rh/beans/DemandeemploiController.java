/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.DemandeemploiDaoImp;
import tn.ensi.rh.dao.IDemandeemploiDao;
import tn.ensi.rh.entities.Demandeemploi;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class DemandeemploiController {

    private List<Demandeemploi> emplois;
    private IDemandeemploiDao dao;

    public DemandeemploiController() {
        dao = new DemandeemploiDaoImp();
        emplois = dao.findDemandeemploiEntities();
    }

    public List<Demandeemploi> getEmplois() {
        return emplois;
    }

    public void setEmplois(List<Demandeemploi> emplois) {
        this.emplois = emplois;
    }

}
