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
import tn.ensi.rh.dao.exceptions.PreexistingEntityException;
import tn.ensi.rh.entities.Compte;

/**
 *
 * @author user
 */
public interface ICompteDao {

    void create(Compte compte) throws IllegalOrphanException, PreexistingEntityException, Exception;

    void destroy(String id) throws NonexistentEntityException;

    void edit(Compte compte) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Compte findCompte(String id);

    List<Compte> findCompteEntities();

    List<Compte> findCompteEntities(int maxResults, int firstResult);

    int getCompteCount();

    EntityManager getEntityManager();
    
}
