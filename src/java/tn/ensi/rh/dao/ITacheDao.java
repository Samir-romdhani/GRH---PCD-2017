/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Tache;

/**
 *
 * @author user
 */
public interface ITacheDao {

    void create(Tache tache);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Tache tache) throws NonexistentEntityException, Exception;

    Tache findTache(Integer id);

    List<Tache> findTacheEntities();

    List<Tache> findTacheEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getTacheCount();
    
}
