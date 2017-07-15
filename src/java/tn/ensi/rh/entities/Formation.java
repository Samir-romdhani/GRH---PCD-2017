/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.ensi.rh.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "formations", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Formation.findAll", query = "SELECT f FROM Formation f")
    , @NamedQuery(name = "Formation.findByIdFormation", query = "SELECT f FROM Formation f WHERE f.idFormation = :idFormation")
    , @NamedQuery(name = "Formation.findByDateDebut", query = "SELECT f FROM Formation f WHERE f.dateDebut = :dateDebut")
    , @NamedQuery(name = "Formation.findByDateFin", query = "SELECT f FROM Formation f WHERE f.dateFin = :dateFin")
    , @NamedQuery(name = "Formation.findByLieuFormation", query = "SELECT f FROM Formation f WHERE f.lieuFormation = :lieuFormation")})
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formation")
    private Integer idFormation;
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Column(name = "lieu_formation")
    private String lieuFormation;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne
    private Equipe idEquipe;
    @JoinColumn(name = "id_projet", referencedColumnName = "id_projet")
    @ManyToOne
    private Projet idProjet;
    @JoinColumn(name = "id_tache", referencedColumnName = "id_tache")
    @ManyToOne
    private Tache idTache;

    public Formation() {
    }

    public Formation(Integer idFormation) {
        this.idFormation = idFormation;
    }

    public Integer getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Integer idFormation) {
        this.idFormation = idFormation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieuFormation() {
        return lieuFormation;
    }

    public void setLieuFormation(String lieuFormation) {
        this.lieuFormation = lieuFormation;
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

    public Tache getIdTache() {
        return idTache;
    }

    public void setIdTache(Tache idTache) {
        this.idTache = idTache;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormation != null ? idFormation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formation)) {
            return false;
        }
        Formation other = (Formation) object;
        if ((this.idFormation == null && other.idFormation != null) || (this.idFormation != null && !this.idFormation.equals(other.idFormation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Formation[ idFormation=" + idFormation + " ]";
    }
    
}
