/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.IllegalOrphanException;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Metier;

/**
 *
 * @author user
 */
public interface IMetierDao {

    void create(Metier metier);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Metier metier) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Metier findMetier(Integer id);

    List<Metier> findMetierEntities();

    List<Metier> findMetierEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getMetierCount();
    
}
