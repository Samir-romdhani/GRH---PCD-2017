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
import tn.ensi.rh.entities.Condidat;

/**
 *
 * @author user
 */
public interface ICondidatDao {

    void create(Condidat condidat);

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    void edit(Condidat condidat) throws IllegalOrphanException, NonexistentEntityException, Exception;

    Condidat findCondidat(Integer id);

    List<Condidat> findCondidatEntities();

    List<Condidat> findCondidatEntities(int maxResults, int firstResult);

    int getCondidatCount();

    EntityManager getEntityManager();
    
}
