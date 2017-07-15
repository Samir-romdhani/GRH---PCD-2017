/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.CompetenceDaoImp;
import tn.ensi.rh.dao.ICompetenceDao;
import tn.ensi.rh.entities.Competence;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class CompetenceController {

    /*     private List<> ;
    private IDao dao;
     dao = new DaoImp();
         = dao.findEntities();
     */
    private List<Competence> competences;
    private ICompetenceDao dao;

    public CompetenceController() {
        dao = new CompetenceDaoImp();
        competences = dao.findCompetenceEntities();
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

}
