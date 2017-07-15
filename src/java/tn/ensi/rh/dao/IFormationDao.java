/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Formation;

/**
 *
 * @author user
 */
public interface IFormationDao {

    void create(Formation formation);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Formation formation) throws NonexistentEntityException, Exception;

    Formation findFormation(Integer id);

    List<Formation> findFormationEntities();

    List<Formation> findFormationEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getFormationCount();
    
}
