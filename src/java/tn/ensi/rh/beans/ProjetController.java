/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.IProjetDao;
import tn.ensi.rh.dao.ProjetDaoImp;
import tn.ensi.rh.entities.Projet;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class ProjetController {

    private List<Projet> projets;
    private IProjetDao dao;

    public ProjetController() {
        dao = new ProjetDaoImp();
        projets = dao.findProjetEntities();
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

}
