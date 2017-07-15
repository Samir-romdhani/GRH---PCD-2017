/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Demandeemploi;

/**
 *
 * @author user
 */
public interface IDemandeemploiDao {

    void create(Demandeemploi demandeemploi);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Demandeemploi demandeemploi) throws NonexistentEntityException, Exception;

    Demandeemploi findDemandeemploi(Integer id);

    List<Demandeemploi> findDemandeemploiEntities();

    List<Demandeemploi> findDemandeemploiEntities(int maxResults, int firstResult);

    int getDemandeemploiCount();

    EntityManager getEntityManager();
    
}
