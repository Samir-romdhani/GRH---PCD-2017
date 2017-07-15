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
@Table(name = "missions", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Mission.findAll", query = "SELECT m FROM Mission m")
    , @NamedQuery(name = "Mission.findByIdMission", query = "SELECT m FROM Mission m WHERE m.idMission = :idMission")
    , @NamedQuery(name = "Mission.findByDateDebut", query = "SELECT m FROM Mission m WHERE m.dateDebut = :dateDebut")
    , @NamedQuery(name = "Mission.findByDateFin", query = "SELECT m FROM Mission m WHERE m.dateFin = :dateFin")
    , @NamedQuery(name = "Mission.findByLieuMission", query = "SELECT m FROM Mission m WHERE m.lieuMission = :lieuMission")})
public class Mission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mission")
    private Integer idMission;
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Column(name = "lieu_mission")
    private String lieuMission;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne
    private Equipe idEquipe;
    @JoinColumn(name = "id_projet", referencedColumnName = "id_projet")
    @ManyToOne
    private Projet idProjet;
    @JoinColumn(name = "id_tache", referencedColumnName = "id_tache")
    @ManyToOne
    private Tache idTache;

    public Mission() {
    }

    public Mission(Integer idMission) {
        this.idMission = idMission;
    }

    public Integer getIdMission() {
        return idMission;
    }

    public void setIdMission(Integer idMission) {
        this.idMission = idMission;
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

    public String getLieuMission() {
        return lieuMission;
    }

    public void setLieuMission(String lieuMission) {
        this.lieuMission = lieuMission;
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
        hash += (idMission != null ? idMission.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mission)) {
            return false;
        }
        Mission other = (Mission) object;
        if ((this.idMission == null && other.idMission != null) || (this.idMission != null && !this.idMission.equals(other.idMission))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Mission[ idMission=" + idMission + " ]";
    }
    
}
