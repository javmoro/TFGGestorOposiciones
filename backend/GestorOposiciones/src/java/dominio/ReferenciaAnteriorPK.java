/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author javie
 */
@Embeddable
public class ReferenciaAnteriorPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "ID_REF_ANTERIOR")
    private String idRefAnterior;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "ID_REF_POSTERIOR")
    private String idRefPosterior;

    public ReferenciaAnteriorPK() {
    }

    public ReferenciaAnteriorPK(String idRefAnterior, String idRefPosterior) {
        this.idRefAnterior = idRefAnterior;
        this.idRefPosterior = idRefPosterior;
    }

    public String getIdRefAnterior() {
        return idRefAnterior;
    }

    public void setIdRefAnterior(String idRefAnterior) {
        this.idRefAnterior = idRefAnterior;
    }

    public String getIdRefPosterior() {
        return idRefPosterior;
    }

    public void setIdRefPosterior(String idRefPosterior) {
        this.idRefPosterior = idRefPosterior;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRefAnterior != null ? idRefAnterior.hashCode() : 0);
        hash += (idRefPosterior != null ? idRefPosterior.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenciaAnteriorPK)) {
            return false;
        }
        ReferenciaAnteriorPK other = (ReferenciaAnteriorPK) object;
        if ((this.idRefAnterior == null && other.idRefAnterior != null) || (this.idRefAnterior != null && !this.idRefAnterior.equals(other.idRefAnterior))) {
            return false;
        }
        if ((this.idRefPosterior == null && other.idRefPosterior != null) || (this.idRefPosterior != null && !this.idRefPosterior.equals(other.idRefPosterior))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.ReferenciaAnteriorPK[ idRefAnterior=" + idRefAnterior + ", idRefPosterior=" + idRefPosterior + " ]";
    }
    
}
