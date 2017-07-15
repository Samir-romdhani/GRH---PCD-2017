/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "projets", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p")
    , @NamedQuery(name = "Projet.findByIdProjet", query = "SELECT p FROM Projet p WHERE p.idProjet = :idProjet")
    , @NamedQuery(name = "Projet.findByNomProjet", query = "SELECT p FROM Projet p WHERE p.nomProjet = :nomProjet")
    , @NamedQuery(name = "Projet.findByDateDebutProjet", query = "SELECT p FROM Projet p WHERE p.dateDebutProjet = :dateDebutProjet")
    , @NamedQuery(name = "Projet.findByDureeProjet", query = "SELECT p FROM Projet p WHERE p.dureeProjet = :dureeProjet")})
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_projet")
    private Integer idProjet;
    @Basic(optional = false)
    @Column(name = "nom_projet")
    private String nomProjet;
    @Basic(optional = false)
    @Column(name = "date_debut_projet")
    @Temporal(TemporalType.DATE)
    private Date dateDebutProjet;
    @Basic(optional = false)
    @Column(name = "duree_projet")
    private int dureeProjet;
    @OneToMany(mappedBy = "idProjet")
    private Collection<Formation> formationCollection;
    @OneToMany(mappedBy = "idProjet")
    private Collection<Mission> missionCollection;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne(optional = false)
    private Equipe idEquipe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProjet")
    private Collection<Tache> tacheCollection;

    public Projet() {
    }

    public Projet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    public Projet(Integer idProjet, String nomProjet, Date dateDebutProjet, int dureeProjet) {
        this.idProjet = idProjet;
        this.nomProjet = nomProjet;
        this.dateDebutProjet = dateDebutProjet;
        this.dureeProjet = dureeProjet;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Date getDateDebutProjet() {
        return dateDebutProjet;
    }

    public void setDateDebutProjet(Date dateDebutProjet) {
        this.dateDebutProjet = dateDebutProjet;
    }

    public int getDureeProjet() {
        return dureeProjet;
    }

    public void setDureeProjet(int dureeProjet) {
        this.dureeProjet = dureeProjet;
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

    public Collection<Tache> getTacheCollection() {
        return tacheCollection;
    }

    public void setTacheCollection(Collection<Tache> tacheCollection) {
        this.tacheCollection = tacheCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProjet != null ? idProjet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projet)) {
            return false;
        }
        Projet other = (Projet) object;
        if ((this.idProjet == null && other.idProjet != null) || (this.idProjet != null && !this.idProjet.equals(other.idProjet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Projet[ idProjet=" + idProjet + " ]";
    }
    
}
