/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.ITacheDao;
import tn.ensi.rh.dao.TacheDaoImp;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class TacheController {

    private List<Tache> taches;
    private ITacheDao dao;

    public TacheController() {
        dao = new TacheDaoImp();
        taches = dao.findTacheEntities();

    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

}
