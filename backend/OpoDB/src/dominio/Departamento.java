/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DEPARTAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByEtq", query = "SELECT d FROM Departamento d WHERE d.etq = :etq"),
    @NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre = :nombre")})
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ETQ")
    private String etq;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departamento")
    private Collection<RelDepEpi> relDepEpiCollection;

    public Departamento() {
    }

    public Departamento(String etq) {
        this.etq = etq;
    }

    public Departamento(String etq, String nombre) {
        this.etq = etq;
        this.nombre = nombre;
    }

    public String getEtq() {
        return etq;
    }

    public void setEtq(String etq) {
        this.etq = etq;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<RelDepEpi> getRelDepEpiCollection() {
        return relDepEpiCollection;
    }

    public void setRelDepEpiCollection(Collection<RelDepEpi> relDepEpiCollection) {
        this.relDepEpiCollection = relDepEpiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etq != null ? etq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.etq == null && other.etq != null) || (this.etq != null && !this.etq.equals(other.etq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.Departamento[ etq=" + etq + " ]";
    }
    
}
