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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "employes", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Employe.findAll", query = "SELECT e FROM Employe e")
    , @NamedQuery(name = "Employe.findByIdEmploye", query = "SELECT e FROM Employe e WHERE e.idEmploye = :idEmploye")
    , @NamedQuery(name = "Employe.findByNom", query = "SELECT e FROM Employe e WHERE e.nom = :nom")
    , @NamedQuery(name = "Employe.findByTel", query = "SELECT e FROM Employe e WHERE e.tel = :tel")
    , @NamedQuery(name = "Employe.findBySexe", query = "SELECT e FROM Employe e WHERE e.sexe = :sexe")
    , @NamedQuery(name = "Employe.findByAdresse", query = "SELECT e FROM Employe e WHERE e.adresse = :adresse")
    , @NamedQuery(name = "Employe.findByPrenom", query = "SELECT e FROM Employe e WHERE e.prenom = :prenom")
    , @NamedQuery(name = "Employe.findByMail", query = "SELECT e FROM Employe e WHERE e.mail = :mail")
    , @NamedQuery(name = "Employe.findByDatedenaissance", query = "SELECT e FROM Employe e WHERE e.datedenaissance = :datedenaissance")
    , @NamedQuery(name = "Employe.findByDatedetravail", query = "SELECT e FROM Employe e WHERE e.datedetravail = :datedetravail")
    , @NamedQuery(name = "Employe.findByMotdepass", query = "SELECT e FROM Employe e WHERE e.motdepass = :motdepass")})
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_employe")
    private String idEmploye;
    @Column(name = "nom")
    private String nom;
    @Column(name = "Tel")
    private Integer tel;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "Adresse")
    private String adresse;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "mail")
    private String mail;
    @Column(name = "datedenaissance")
    @Temporal(TemporalType.DATE)
    private Date datedenaissance;
    @Column(name = "datedetravail")
    @Temporal(TemporalType.DATE)
    private Date datedetravail;
    @Column(name = "motdepass")
    private String motdepass;
    @OneToMany(mappedBy = "idEmploye")
    private Collection<Demandeformation> demandeformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmploye")
    private Collection<Evaluation> evaluationCollection;
    @OneToMany(mappedBy = "idEmploye")
    private Collection<Demandeabsence> demandeabsenceCollection;
    @OneToMany(mappedBy = "idEmploye")
    private Collection<Competence> competenceCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employe")
    private Compte compte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmploye")
    private Collection<ChefDeProjet> chefDeProjetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chef")
    private Collection<Employe> employeCollection;
    @JoinColumn(name = "chef", referencedColumnName = "id_employe")
    @ManyToOne(optional = false)
    private Employe chef;
    @JoinColumn(name = "id_equipe", referencedColumnName = "id_equipe")
    @ManyToOne
    private Equipe idEquipe;
    @JoinColumn(name = "id_departement", referencedColumnName = "id_departement")
    @ManyToOne
    private Departement idDepartement;
    @JoinColumn(name = "identification_du_poste", referencedColumnName = "identification_du_poste")
    @ManyToOne(optional = false)
    private Metier identificationDuPoste;

    public Employe() {
    }

    public Employe(String idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(String idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public Date getDatedetravail() {
        return datedetravail;
    }

    public void setDatedetravail(Date datedetravail) {
        this.datedetravail = datedetravail;
    }

    public String getMotdepass() {
        return motdepass;
    }

    public void setMotdepass(String motdepass) {
        this.motdepass = motdepass;
    }

    public Collection<Demandeformation> getDemandeformationCollection() {
        return demandeformationCollection;
    }

    public void setDemandeformationCollection(Collection<Demandeformation> demandeformationCollection) {
        this.demandeformationCollection = demandeformationCollection;
    }

    public Collection<Evaluation> getEvaluationCollection() {
        return evaluationCollection;
    }

    public void setEvaluationCollection(Collection<Evaluation> evaluationCollection) {
        this.evaluationCollection = evaluationCollection;
    }

    public Collection<Demandeabsence> getDemandeabsenceCollection() {
        return demandeabsenceCollection;
    }

    public void setDemandeabsenceCollection(Collection<Demandeabsence> demandeabsenceCollection) {
        this.demandeabsenceCollection = demandeabsenceCollection;
    }

    public Collection<Competence> getCompetenceCollection() {
        return competenceCollection;
    }

    public void setCompetenceCollection(Collection<Competence> competenceCollection) {
        this.competenceCollection = competenceCollection;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
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

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }

    public Equipe getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Equipe idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Departement getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(Departement idDepartement) {
        this.idDepartement = idDepartement;
    }

    public Metier getIdentificationDuPoste() {
        return identificationDuPoste;
    }

    public void setIdentificationDuPoste(Metier identificationDuPoste) {
        this.identificationDuPoste = identificationDuPoste;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmploye != null ? idEmploye.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employe)) {
            return false;
        }
        Employe other = (Employe) object;
        if ((this.idEmploye == null && other.idEmploye != null) || (this.idEmploye != null && !this.idEmploye.equals(other.idEmploye))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idEmploye;
    }
    
}
