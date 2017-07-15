/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "taches", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tache.findAll", query = "SELECT t FROM Tache t")
    , @NamedQuery(name = "Tache.findByIdTache", query = "SELECT t FROM Tache t WHERE t.idTache = :idTache")
    , @NamedQuery(name = "Tache.findByLibelleTache", query = "SELECT t FROM Tache t WHERE t.libelleTache = :libelleTache")})
public class Tache implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tache")
    private Integer idTache;
    @Basic(optional = false)
    @Column(name = "libelle_tache")
    private String libelleTache;
    @OneToMany(mappedBy = "idTache")
    private Collection<Formation> formationCollection;
    @OneToMany(mappedBy = "idTache")
    private Collection<Mission> missionCollection;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne(optional = false)
    private Equipe idEquipe;
    @JoinColumn(name = "id_projet", referencedColumnName = "id_projet")
    @ManyToOne(optional = false)
    private Projet idProjet;

    public Tache() {
    }

    public Tache(Integer idTache) {
        this.idTache = idTache;
    }

    public Tache(Integer idTache, String libelleTache) {
        this.idTache = idTache;
        this.libelleTache = libelleTache;
    }

    public Integer getIdTache() {
        return idTache;
    }

    public void setIdTache(Integer idTache) {
        this.idTache = idTache;
    }

    public String getLibelleTache() {
        return libelleTache;
    }

    public void setLibelleTache(String libelleTache) {
        this.libelleTache = libelleTache;
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

    public Equipe getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Equipe idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Projet getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Projet idProjet) {
        this.idProjet = idProjet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTache != null ? idTache.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tache)) {
            return false;
        }
        Tache other = (Tache) object;
        if ((this.idTache == null && other.idTache != null) || (this.idTache != null && !this.idTache.equals(other.idTache))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Tache[ idTache=" + idTache + " ]";
    }
    
}
