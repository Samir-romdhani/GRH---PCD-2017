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
import tn.ensi.rh.entities.Employe;

/**
 *
 * @author user
 */
public interface IEmployeDao {

    void create(Employe employe) throws PreexistingEntityException, Exception;
    
    void destroy2(Employe emp) throws IllegalOrphanException, NonexistentEntityException;

    void destroy(String id) throws IllegalOrphanException, NonexistentEntityException;

    void edit1(Employe employe) ;
    
    void edit(Employe employe) throws IllegalOrphanException, NonexistentEntityException, Exception;
    
    void edit2(Employe employe) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Employe findEmploye(String id);

    List<Employe> findEmployeEntities();

    List<Employe> findEmployeEntities(int maxResults, int firstResult);

    int getEmployeCount();

    EntityManager getEntityManager();
    
}
