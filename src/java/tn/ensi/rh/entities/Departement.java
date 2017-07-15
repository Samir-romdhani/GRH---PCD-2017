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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "departements", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Departement.findAll", query = "SELECT d FROM Departement d")
    , @NamedQuery(name = "Departement.findByIdDepartement", query = "SELECT d FROM Departement d WHERE d.idDepartement = :idDepartement")
    , @NamedQuery(name = "Departement.findByNomDepartement", query = "SELECT d FROM Departement d WHERE d.nomDepartement = :nomDepartement")
    , @NamedQuery(name = "Departement.findByDescriptionDepartement", query = "SELECT d FROM Departement d WHERE d.descriptionDepartement = :descriptionDepartement")})
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_departement")
    private Integer idDepartement;
    @Basic(optional = false)
    @Column(name = "nom_departement")
    private String nomDepartement;
    @Basic(optional = false)
    @Column(name = "description_departement")
    private String descriptionDepartement;
    @OneToMany(mappedBy = "idDepartement")
    private Collection<Employe> employeCollection;

    public Departement() {
    }

    public Departement(Integer idDepartement) {
        this.idDepartement = idDepartement;
    }

    public Departement(Integer idDepartement, String nomDepartement, String descriptionDepartement) {
        this.idDepartement = idDepartement;
        this.nomDepartement = nomDepartement;
        this.descriptionDepartement = descriptionDepartement;
    }

    public Integer getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(Integer idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public String getDescriptionDepartement() {
        return descriptionDepartement;
    }

    public void setDescriptionDepartement(String descriptionDepartement) {
        this.descriptionDepartement = descriptionDepartement;
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
        hash += (idDepartement != null ? idDepartement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departement)) {
            return false;
        }
        Departement other = (Departement) object;
        if ((this.idDepartement == null && other.idDepartement != null) || (this.idDepartement != null && !this.idDepartement.equals(other.idDepartement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Departement[ idDepartement=" + idDepartement + " ]";
    }
    
}
