/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tn.ensi.rh.dao.IMissionDao;
import tn.ensi.rh.dao.MissionDaoImp;
import tn.ensi.rh.entities.Mission;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class MissionController {

    private List<Mission> missions ;
    private IMissionDao dao ;
    
    public MissionController() {
        dao = new  MissionDaoImp() ;
        missions = dao.findMissionEntities() ;
        
        
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }
    
}
