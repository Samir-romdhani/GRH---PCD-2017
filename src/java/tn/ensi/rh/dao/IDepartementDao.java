/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Departement;

/**
 *
 * @author user
 */
public interface IDepartementDao {

    void create(Departement departement);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Departement departement) throws NonexistentEntityException, Exception;

    Departement findDepartement(Integer id);

    List<Departement> findDepartementEntities();

    List<Departement> findDepartementEntities(int maxResults, int firstResult);

    int getDepartementCount();

    EntityManager getEntityManager();
    
}
