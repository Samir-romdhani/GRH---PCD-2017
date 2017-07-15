/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Demandeabsence;

/**
 *
 * @author user
 */
public interface IDemandeabsenceDao {

    void create(Demandeabsence demandeabsence);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Demandeabsence demandeabsence) throws NonexistentEntityException, Exception;

    void edit1(Demandeabsence demandeabsence) throws NonexistentEntityException, Exception;

    Demandeabsence findDemandeabsence(Integer id);

    List<Demandeabsence> findDemandeabsenceEntities();

    List<Demandeabsence> findDemandeabsenceEntities(int maxResults, int firstResult);

    int getDemandeabsenceCount();

    EntityManager getEntityManager();

}
