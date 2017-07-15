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
import tn.ensi.rh.entities.Competence;

/**
 *
 * @author user
 */
public interface ICompetenceDao {

    void create(Competence competence);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Competence competence) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Competence findCompetence(Integer id);

    List<Competence> findCompetenceEntities();

    List<Competence> findCompetenceEntities(int maxResults, int firstResult);

    int getCompetenceCount();

    EntityManager getEntityManager();
    
}
