/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.dao;

import tn.ensi.rh.dao.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import tn.ensi.rh.entities.Competence;
import tn.ensi.rh.entities.Demandeabsence;
import tn.ensi.rh.entities.Departement;
import tn.ensi.rh.entities.Employe;
import tn.ensi.rh.entities.Evaluation;
import tn.ensi.rh.entities.Formation;
import tn.ensi.rh.entities.Metier;

public class UserDao {
    
    // private EntityManagerFactory emf ;

    static String nom;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH/mm/ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        UserDao.nom = nom;
    }
/*
    public UserDao() {
        emf = Persistence.createEntityManagerFactory("Application_GRH4PU");
    }

   
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
  */  

    public static boolean login(String user, String password) {

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement(
                    "select id_employe, motdepass from employes where id_employe= ? and motdepass= ? ");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                nom = rs.getString("id_employe");
                //System.err.println(nom);
                System.out.println(rs.getString("id_employe"));
                // System.out.println(rs.getString("nom"));

                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
    }
        
        /*
        
        public List<Formation> findFormationUser() {
        EntityManager em = getEntityManager();

        Query queryByFK = em.createQuery("SELECT f FROM Formation f WHERE f.userId = :user");
        queryByFK.setParameter("user", "E700");
        Collection entities = queryByFK.getResultList();
        List<Formation> userList = new ArrayList<>(entities);
        return  userList ;
           //findFormationEntities(true, -1, -1);
    }
*/
}


