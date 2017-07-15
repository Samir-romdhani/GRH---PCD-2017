/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "equipes", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e")
    , @NamedQuery(name = "Equipe.findByIdEquipe", query = "SELECT e FROM Equipe e WHERE e.idEquipe = :idEquipe")
    , @NamedQuery(name = "Equipe.findByNomequipe", query = "SELECT e FROM Equipe e WHERE e.nomequipe = :nomequipe")})
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipe")
    private Integer idEquipe;
    @Basic(optional = false)
    @Column(name = "Nom_equipe")
    private String nomequipe;
    @OneToMany(mappedBy = "idEquipe")
    private Collection<Formation> formationCollection;
    @OneToMany(mappedBy = "idEquipe")
    private Collection<Mission> missionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEquipe")
    private Collection<Projet> projetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEquipe")
    private Collection<Tache> tacheCollection;
    @OneToMany(mappedBy = "idEquipe")
    private Collection<ChefDeProjet> chefDeProjetCollection;
    @OneToMany(mappedBy = "idEquipe")
    private Collection<Employe> employeCollection;

    public Equipe() {
    }

    public Equipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Equipe(Integer idEquipe, String nomequipe) {
        this.idEquipe = idEquipe;
        this.nomequipe = nomequipe;
    }

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomequipe() {
        return nomequipe;
    }

    public void setNomequipe(String nomequipe) {
        this.nomequipe = nomequipe;
    }

    public Collection<Formation> getFormationCollection() {
        return formationCollection;
    }

    public void setFormationCollection(Collection<Formation> formationCollection) {
        this.formationCollection = formationCollection;
    }

    public Collection<Mission> getMissionCollection() {
        return missionCollection;
    }

    public void setMissionCollection(Collection<Mission> missionCollection) {
        this.missionCollection = missionCollection;
    }

    public Collection<Projet> getProjetCollection() {
        return projetCollection;
    }

    public void setProjetCollection(Collection<Projet> projetCollection) {
        this.projetCollection = projetCollection;
    }

    public Collection<Tache> getTacheCollection() {
        return tacheCollection;
    }

    public void setTacheCollection(Collection<Tache> tacheCollection) {
        this.tacheCollection = tacheCollection;
    }

    public Collection<ChefDeProjet> getChefDeProjetCollection() {
        return chefDeProjetCollection;
    }

    public void setChefDeProjetCollection(Collection<ChefDeProjet> chefDeProjetCollection) {
        this.chefDeProjetCollection = chefDeProjetCollection;
    }

    public Collection<Employe> getEmployeCollection() {
        return employeCollection;
    }

    public void setEmployeCollection(Collection<Employe> employeCollection) {
        this.employeCollection = employeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipe != null ? idEquipe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipe)) {
            return false;
        }
        Equipe other = (Equipe) object;
        if ((this.idEquipe == null && other.idEquipe != null) || (this.idEquipe != null && !this.idEquipe.equals(other.idEquipe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Equipe[ idEquipe=" + idEquipe + " ]";
    }
    
}
