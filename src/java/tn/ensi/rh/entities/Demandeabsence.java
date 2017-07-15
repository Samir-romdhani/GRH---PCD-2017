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
@Table(name = "demandeabsences", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Demandeabsence.findAll", query = "SELECT d FROM Demandeabsence d")
    , @NamedQuery(name = "Demandeabsence.findByIdDemande", query = "SELECT d FROM Demandeabsence d WHERE d.idDemande = :idDemande")
    , @NamedQuery(name = "Demandeabsence.findByDatecreation", query = "SELECT d FROM Demandeabsence d WHERE d.datecreation = :datecreation")
    , @NamedQuery(name = "Demandeabsence.findByDatedebut", query = "SELECT d FROM Demandeabsence d WHERE d.datedebut = :datedebut")
    , @NamedQuery(name = "Demandeabsence.findByNombredejours", query = "SELECT d FROM Demandeabsence d WHERE d.nombredejours = :nombredejours")
    , @NamedQuery(name = "Demandeabsence.findByType", query = "SELECT d FROM Demandeabsence d WHERE d.type = :type")
    , @NamedQuery(name = "Demandeabsence.findByDatefin", query = "SELECT d FROM Demandeabsence d WHERE d.datefin = :datefin")
    , @NamedQuery(name = "Demandeabsence.findByCommmentaire", query = "SELECT d FROM Demandeabsence d WHERE d.commmentaire = :commmentaire")
    , @NamedQuery(name = "Demandeabsence.findByEtat", query = "SELECT d FROM Demandeabsence d WHERE d.etat = :etat")})
public class Demandeabsence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdDemande")
    private Integer idDemande;
    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;
    @Column(name = "datedebut")
    @Temporal(TemporalType.DATE)
    private Date datedebut;
    @Basic(optional = false)
    @Column(name = "nombredejours")
    private int nombredejours;
    @Column(name = "type")
    private String type;
    @Column(name = "datefin")
    @Temporal(TemporalType.DATE)
    private Date datefin;
    @Column(name = "commmentaire")
    private String commmentaire;
    @Column(name = "etat")
    private String etat;
    @JoinColumn(name = "id_employe", referencedColumnName = "id_employe")
    @ManyToOne
    private Employe idEmploye;

    public Demandeabsence() {
    }

    public Demandeabsence(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Demandeabsence(Integer idDemande, int nombredejours) {
        this.idDemande = idDemande;
        this.nombredejours = nombredejours;
    }

    public Integer getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public int getNombredejours() {
        return nombredejours;
    }

    public void setNombredejours(int nombredejours) {
        this.nombredejours = nombredejours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getCommmentaire() {
        return commmentaire;
    }

    public void setCommmentaire(String commmentaire) {
        this.commmentaire = commmentaire;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Employe getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Employe idEmploye) {
        this.idEmploye = idEmploye;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDemande != null ? idDemande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demandeabsence)) {
            return false;
        }
        Demandeabsence other = (Demandeabsence) object;
        if ((this.idDemande == null && other.idDemande != null) || (this.idDemande != null && !this.idDemande.equals(other.idDemande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Demandeabsence[ idDemande=" + idDemande + " ]";
    }
    
}
