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
import tn.ensi.rh.entities.Demandeformation;

/**
 *
 * @author user
 */
public interface IDemandeformationDao {

    void create(Demandeformation demandeformation);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Demandeformation demandeformation) throws IllegalOrphanException, NonexistentEntityException, Exception;
    void edit1(Demandeformation demandeformation) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Demandeformation findDemandeformation(Integer id);

    List<Demandeformation> findDemandeformationEntities();

    List<Demandeformation> findDemandeformationEntities(int maxResults, int firstResult);

    int getDemandeformationCount();

    EntityManager getEntityManager();
    
}
