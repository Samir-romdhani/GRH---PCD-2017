/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.EvaluationDaoImp;
import tn.ensi.rh.dao.IEvaluationDao;
import tn.ensi.rh.entities.Evaluation;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class EvaluationController {

    private List<Evaluation> evaluations;
    private IEvaluationDao dao;
    
    private Login dao1 ;
    private List<Evaluation> listevaluations;

    public EvaluationController() {
        dao = new EvaluationDaoImp();
        evaluations = dao.findEvaluationEntities();
        
        dao1 = new Login();
        //listevaluations = dao1.Listevaluation() ;
        
    }

    public List<Evaluation> getListevaluations() {
        return listevaluations;
    }

    public void setListevaluations(List<Evaluation> listevaluations) {
        this.listevaluations = listevaluations;
    }

    public Login getDao1() {
        return dao1;
    }

    public void setDao1(Login dao1) {
        this.dao1 = dao1;
    }

 

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

}
