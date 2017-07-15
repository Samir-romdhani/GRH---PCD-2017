/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.ChefDeProjet;

/**
 *
 * @author user
 */
public interface IChefDeProjetDao {

    void create(ChefDeProjet chefDeProjet);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(ChefDeProjet chefDeProjet) throws NonexistentEntityException, Exception;

    ChefDeProjet findChefDeProjet(Integer id);

    List<ChefDeProjet> findChefDeProjetEntities();

    List<ChefDeProjet> findChefDeProjetEntities(int maxResults, int firstResult);

    int getChefDeProjetCount();

    EntityManager getEntityManager();
    
}
