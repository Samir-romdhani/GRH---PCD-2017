/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Mission;

/**
 *
 * @author user
 */
public interface IMissionDao {

    void create(Mission mission);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Mission mission) throws NonexistentEntityException, Exception;

    Mission findMission(Integer id);

    List<Mission> findMissionEntities();

    List<Mission> findMissionEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getMissionCount();
    
}
