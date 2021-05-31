/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author javie
 */
@Embeddable
public class RelDepEpiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "NOMBREEP")
    private String nombreep;
    @Basic(optional = false)
    @Column(name = "ETQDEP")
    private String etqdep;

    public RelDepEpiPK() {
    }

    public RelDepEpiPK(String nombreep, String etqdep) {
        this.nombreep = nombreep;
        this.etqdep = etqdep;
    }

    public String getNombreep() {
        return nombreep;
    }

    public void setNombreep(String nombreep) {
        this.nombreep = nombreep;
    }

    public String getEtqdep() {
        return etqdep;
    }

    public void setEtqdep(String etqdep) {
        this.etqdep = etqdep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreep != null ? nombreep.hashCode() : 0);
        hash += (etqdep != null ? etqdep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelDepEpiPK)) {
            return false;
        }
        RelDepEpiPK other = (RelDepEpiPK) object;
        if ((this.nombreep == null && other.nombreep != null) || (this.nombreep != null && !this.nombreep.equals(other.nombreep))) {
            return false;
        }
        if ((this.etqdep == null && other.etqdep != null) || (this.etqdep != null && !this.etqdep.equals(other.etqdep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.RelDepEpiPK[ nombreep=" + nombreep + ", etqdep=" + etqdep + " ]";
    }
    
}
