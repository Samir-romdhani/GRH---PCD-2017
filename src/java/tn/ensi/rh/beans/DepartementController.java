/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.DepartementDaoImp;
import tn.ensi.rh.dao.IDepartementDao;
import tn.ensi.rh.entities.Departement;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class DepartementController {

    private List<Departement> departements;
    private IDepartementDao dao;

    public DepartementController() {
        dao = new DepartementDaoImp();
        departements = dao.findDepartementEntities();
    }

    public List<Departement> getDepartements() {
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

}
