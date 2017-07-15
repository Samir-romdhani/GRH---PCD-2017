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
@Table(name = "demandeemplois", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Demandeemploi.findAll", query = "SELECT d FROM Demandeemploi d")
    , @NamedQuery(name = "Demandeemploi.findByIdDemandeemplois", query = "SELECT d FROM Demandeemploi d WHERE d.idDemandeemplois = :idDemandeemplois")
    , @NamedQuery(name = "Demandeemploi.findByTitreEmploiRecherchee", query = "SELECT d FROM Demandeemploi d WHERE d.titreEmploiRecherchee = :titreEmploiRecherchee")
    , @NamedQuery(name = "Demandeemploi.findByEtat", query = "SELECT d FROM Demandeemploi d WHERE d.etat = :etat")})
public class Demandeemploi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_demandeemplois")
    private Integer idDemandeemplois;
    @Column(name = "titre_emploi_recherchee")
    private String titreEmploiRecherchee;
    @Basic(optional = false)
    @Column(name = "etat")
    private String etat;
    @JoinColumn(name = "id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Condidat id;

    public Demandeemploi() {
    }

    public Demandeemploi(Integer idDemandeemplois) {
        this.idDemandeemplois = idDemandeemplois;
    }

    public Demandeemploi(Integer idDemandeemplois, String etat) {
        this.idDemandeemplois = idDemandeemplois;
        this.etat = etat;
    }

    public Integer getIdDemandeemplois() {
        return idDemandeemplois;
    }

    public void setIdDemandeemplois(Integer idDemandeemplois) {
        this.idDemandeemplois = idDemandeemplois;
    }

    public String getTitreEmploiRecherchee() {
        return titreEmploiRecherchee;
    }

    public void setTitreEmploiRecherchee(String titreEmploiRecherchee) {
        this.titreEmploiRecherchee = titreEmploiRecherchee;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Condidat getId() {
        return id;
    }

    public void setId(Condidat id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDemandeemplois != null ? idDemandeemplois.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demandeemploi)) {
            return false;
        }
        Demandeemploi other = (Demandeemploi) object;
        if ((this.idDemandeemplois == null && other.idDemandeemplois != null) || (this.idDemandeemplois != null && !this.idDemandeemplois.equals(other.idDemandeemplois))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Demandeemploi[ idDemandeemplois=" + idDemandeemplois + " ]";
    }
    
}
