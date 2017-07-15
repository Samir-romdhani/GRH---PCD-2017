/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "chef_de_projets", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "ChefDeProjet.findAll", query = "SELECT c FROM ChefDeProjet c")
    , @NamedQuery(name = "ChefDeProjet.findByIdChefDeProjet", query = "SELECT c FROM ChefDeProjet c WHERE c.idChefDeProjet = :idChefDeProjet")})
public class ChefDeProjet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chef_de_projet")
    private Integer idChefDeProjet;
    @JoinColumn(name = "id_employe", referencedColumnName = "id_employe")
    @ManyToOne(optional = false)
    private Employe idEmploye;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne
    private Equipe idEquipe;

    public ChefDeProjet() {
    }

    public ChefDeProjet(Integer idChefDeProjet) {
        this.idChefDeProjet = idChefDeProjet;
    }

    public Integer getIdChefDeProjet() {
        return idChefDeProjet;
    }

    public void setIdChefDeProjet(Integer idChefDeProjet) {
        this.idChefDeProjet = idChefDeProjet;
    }

    public Employe getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Employe idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Equipe getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Equipe idEquipe) {
        this.idEquipe = idEquipe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChefDeProjet != null ? idChefDeProjet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChefDeProjet)) {
            return false;
        }
        ChefDeProjet other = (ChefDeProjet) object;
        if ((this.idChefDeProjet == null && other.idChefDeProjet != null) || (this.idChefDeProjet != null && !this.idChefDeProjet.equals(other.idChefDeProjet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.ChefDeProjet[ idChefDeProjet=" + idChefDeProjet + " ]";
    }
    
}
