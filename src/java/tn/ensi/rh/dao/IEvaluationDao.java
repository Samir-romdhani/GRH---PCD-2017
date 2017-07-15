/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tn.ensi.rh.dao.exceptions.NonexistentEntityException;
import tn.ensi.rh.entities.Evaluation;

/**
 *
 * @author user
 */
public interface IEvaluationDao {

    void create(Evaluation evaluation);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Evaluation evaluation) throws NonexistentEntityException, Exception;

    Evaluation findEvaluation(Integer id);

    List<Evaluation> findEvaluationEntities();

    List<Evaluation> findEvaluationEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getEvaluationCount();
    
}
