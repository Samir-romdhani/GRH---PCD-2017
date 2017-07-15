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
@Table(name = "demandeformations", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Demandeformation.findAll", query = "SELECT d FROM Demandeformation d")
    , @NamedQuery(name = "Demandeformation.findByIdF", query = "SELECT d FROM Demandeformation d WHERE d.idF = :idF")
    , @NamedQuery(name = "Demandeformation.findByLibelle", query = "SELECT d FROM Demandeformation d WHERE d.libelle = :libelle")
    , @NamedQuery(name = "Demandeformation.findByEtat", query = "SELECT d FROM Demandeformation d WHERE d.etat = :etat")
    , @NamedQuery(name = "Demandeformation.findByDateCreation", query = "SELECT d FROM Demandeformation d WHERE d.dateCreation = :dateCreation")
    , @NamedQuery(name = "Demandeformation.findByDateFormation", query = "SELECT d FROM Demandeformation d WHERE d.dateFormation = :dateFormation")})
public class Demandeformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdF")
    private Integer idF;
    @Column(name = "Libelle")
    private String libelle;
    @Column(name = "etat")
    private String etat;
    @Basic(optional = false)
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @Basic(optional = false)
    @Column(name = "date_formation")
    @Temporal(TemporalType.DATE)
    private Date dateFormation;
    @JoinColumn(name = "id_employe", referencedColumnName = "id_employe")
    @ManyToOne
    private Employe idEmploye;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idF")
    private Collection<Evaluation> evaluationCollection;
    @OneToMany(mappedBy = "idF")
    private Collection<Competence> competenceCollection;

    public Demandeformation() {
    }

    public Demandeformation(Integer idF) {
        this.idF = idF;
    }

    public Demandeformation(Integer idF, Date dateCreation, Date dateFormation) {
        this.idF = idF;
        this.dateCreation = dateCreation;
        this.dateFormation = dateFormation;
    }

    public Integer getIdF() {
        return idF;
    }

    public void setIdF(Integer idF) {
        this.idF = idF;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateFormation() {
        return dateFormation;
    }

    public void setDateFormation(Date dateFormation) {
        this.dateFormation = dateFormation;
    }

    public Employe getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Employe idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Collection<Evaluation> getEvaluationCollection() {
        return evaluationCollection;
    }

    public void setEvaluationCollection(Collection<Evaluation> evaluationCollection) {
        this.evaluationCollection = evaluationCollection;
    }

    public Collection<Competence> getCompetenceCollection() {
        return competenceCollection;
    }

    public void setCompetenceCollection(Collection<Competence> competenceCollection) {
        this.competenceCollection = competenceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idF != null ? idF.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demandeformation)) {
            return false;
        }
        Demandeformation other = (Demandeformation) object;
        if ((this.idF == null && other.idF != null) || (this.idF != null && !this.idF.equals(other.idF))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Demandeformation[ idF=" + idF + " ]";
    }
    
}
