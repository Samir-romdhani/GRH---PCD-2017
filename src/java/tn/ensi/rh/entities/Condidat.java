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
@Table(name = "condidats", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Condidat.findAll", query = "SELECT c FROM Condidat c")
    , @NamedQuery(name = "Condidat.findById", query = "SELECT c FROM Condidat c WHERE c.id = :id")
    , @NamedQuery(name = "Condidat.findByNom", query = "SELECT c FROM Condidat c WHERE c.nom = :nom")
    , @NamedQuery(name = "Condidat.findByAdresse", query = "SELECT c FROM Condidat c WHERE c.adresse = :adresse")
    , @NamedQuery(name = "Condidat.findByVille", query = "SELECT c FROM Condidat c WHERE c.ville = :ville")
    , @NamedQuery(name = "Condidat.findByTel", query = "SELECT c FROM Condidat c WHERE c.tel = :tel")
    , @NamedQuery(name = "Condidat.findBySexe", query = "SELECT c FROM Condidat c WHERE c.sexe = :sexe")
    , @NamedQuery(name = "Condidat.findByCodepostal", query = "SELECT c FROM Condidat c WHERE c.codepostal = :codepostal")
    , @NamedQuery(name = "Condidat.findByPrenom", query = "SELECT c FROM Condidat c WHERE c.prenom = :prenom")
    , @NamedQuery(name = "Condidat.findByMail", query = "SELECT c FROM Condidat c WHERE c.mail = :mail")
    , @NamedQuery(name = "Condidat.findByDatedenaissance", query = "SELECT c FROM Condidat c WHERE c.datedenaissance = :datedenaissance")
    , @NamedQuery(name = "Condidat.findByScolarit\u00e9", query = "SELECT c FROM Condidat c WHERE c.scolarit\u00e9 = :scolarit\u00e9")
    , @NamedQuery(name = "Condidat.findByCompetences", query = "SELECT c FROM Condidat c WHERE c.competences = :competences")
    , @NamedQuery(name = "Condidat.findByCertifications", query = "SELECT c FROM Condidat c WHERE c.certifications = :certifications")})
public class Condidat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "Adresse")
    private String adresse;
    @Column(name = "Ville")
    private String ville;
    @Column(name = "Tel")
    private Integer tel;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "codepostal")
    private String codepostal;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "mail")
    private String mail;
    @Column(name = "datedenaissance")
    @Temporal(TemporalType.DATE)
    private Date datedenaissance;
    @Column(name = "scolarit\u00e9")
    private String scolarité;
    @Column(name = "competences")
    private String competences;
    @Column(name = "certifications")
    private String certifications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private Collection<Demandeemploi> demandeemploiCollection;

    public Condidat() {
    }

    public Condidat(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(Date datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getScolarité() {
        return scolarité;
    }

    public void setScolarité(String scolarité) {
        this.scolarité = scolarité;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public Collection<Demandeemploi> getDemandeemploiCollection() {
        return demandeemploiCollection;
    }

    public void setDemandeemploiCollection(Collection<Demandeemploi> demandeemploiCollection) {
        this.demandeemploiCollection = demandeemploiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Condidat)) {
            return false;
        }
        Condidat other = (Condidat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Condidat[ id=" + id + " ]";
    }
    
}
