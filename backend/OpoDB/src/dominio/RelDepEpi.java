/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javie
 */
@Entity
@Table(name = "REL_DEP_EPI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelDepEpi.findAll", query = "SELECT r FROM RelDepEpi r"),
    @NamedQuery(name = "RelDepEpi.findByNombreep", query = "SELECT r FROM RelDepEpi r WHERE r.relDepEpiPK.nombreep = :nombreep"),
    @NamedQuery(name = "RelDepEpi.findByEtqdep", query = "SELECT r FROM RelDepEpi r WHERE r.relDepEpiPK.etqdep = :etqdep")})
public class RelDepEpi implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelDepEpiPK relDepEpiPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relDepEpi")
    private Collection<Oposicion> oposicionCollection;
    @JoinColumn(name = "ETQDEP", referencedColumnName = "ETQ", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamento departamento;
    @JoinColumn(name = "NOMBREEP", referencedColumnName = "NOMBRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Epigrafe epigrafe;

    public RelDepEpi() {
    }

    public RelDepEpi(RelDepEpiPK relDepEpiPK) {
        this.relDepEpiPK = relDepEpiPK;
    }

    public RelDepEpi(String nombreep, String etqdep) {
        this.relDepEpiPK = new RelDepEpiPK(nombreep, etqdep);
    }

    public RelDepEpiPK getRelDepEpiPK() {
        return relDepEpiPK;
    }

    public void setRelDepEpiPK(RelDepEpiPK relDepEpiPK) {
        this.relDepEpiPK = relDepEpiPK;
    }

    @XmlTransient
    public Collection<Oposicion> getOposicionCollection() {
        return oposicionCollection;
    }

    public void setOposicionCollection(Collection<Oposicion> oposicionCollection) {
        this.oposicionCollection = oposicionCollection;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Epigrafe getEpigrafe() {
        return epigrafe;
    }

    public void setEpigrafe(Epigrafe epigrafe) {
        this.epigrafe = epigrafe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relDepEpiPK != null ? relDepEpiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelDepEpi)) {
            return false;
        }
        RelDepEpi other = (RelDepEpi) object;
        if ((this.relDepEpiPK == null && other.relDepEpiPK != null) || (this.relDepEpiPK != null && !this.relDepEpiPK.equals(other.relDepEpiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.RelDepEpi[ relDepEpiPK=" + relDepEpiPK + " ]";
    }
    
}
