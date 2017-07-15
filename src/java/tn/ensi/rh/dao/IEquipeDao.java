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
import tn.ensi.rh.entities.Equipe;

/**
 *
 * @author user
 */
public interface IEquipeDao {

    void create(Equipe equipe);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Equipe equipe) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Equipe findEquipe(Integer id);

    List<Equipe> findEquipeEntities();

    List<Equipe> findEquipeEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getEquipeCount();
    
}
