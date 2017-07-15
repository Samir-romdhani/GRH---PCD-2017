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
@Table(name = "evaluations", catalog = "rhdb1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e")
    , @NamedQuery(name = "Evaluation.findByIdEval", query = "SELECT e FROM Evaluation e WHERE e.idEval = :idEval")
    , @NamedQuery(name = "Evaluation.findByNote", query = "SELECT e FROM Evaluation e WHERE e.note = :note")})
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdEval")
    private Integer idEval;
    @Column(name = "note")
    private Integer note;
    @JoinColumn(name = "IdC", referencedColumnName = "IdC")
    @ManyToOne(optional = false)
    private Competence idC;
    @JoinColumn(name = "id_employe", referencedColumnName = "id_employe")
    @ManyToOne(optional = false)
    private Employe idEmploye;
    @JoinColumn(name = "IdF", referencedColumnName = "IdF")
    @ManyToOne(optional = false)
    private Demandeformation idF;

    public Evaluation() {
    }

    public Evaluation(Integer idEval) {
        this.idEval = idEval;
    }

    public Integer getIdEval() {
        return idEval;
    }

    public void setIdEval(Integer idEval) {
        this.idEval = idEval;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Competence getIdC() {
        return idC;
    }

    public void setIdC(Competence idC) {
        this.idC = idC;
    }

    public Employe getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Employe idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Demandeformation getIdF() {
        return idF;
    }

    public void setIdF(Demandeformation idF) {
        this.idF = idF;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEval != null ? idEval.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluation)) {
            return false;
        }
        Evaluation other = (Evaluation) object;
        if ((this.idEval == null && other.idEval != null) || (this.idEval != null && !this.idEval.equals(other.idEval))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.ensi.rh.entities.Evaluation[ idEval=" + idEval + " ]";
    }
    
}
