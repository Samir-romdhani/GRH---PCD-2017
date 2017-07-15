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
import tn.ensi.rh.entities.Projet;

/**
 *
 * @author user
 */
public interface IProjetDao {

    void create(Projet projet);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Projet projet) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Projet findProjet(Integer id);

    List<Projet> findProjetEntities();

    List<Projet> findProjetEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getProjetCount();
    
}
