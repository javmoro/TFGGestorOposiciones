/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

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
@Table(name = "EPIGRAFE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Epigrafe.findAll", query = "SELECT e FROM Epigrafe e"),
    @NamedQuery(name = "Epigrafe.findByNombre", query = "SELECT e FROM Epigrafe e WHERE e.nombre = :nombre")})
public class Epigrafe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "epigrafe")
    private Collection<RelDepEpi> relDepEpiCollection;

    public Epigrafe() {
    }

    public Epigrafe(String nombre) {
        this.nombre = nombre;
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
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Epigrafe)) {
            return false;
        }
        Epigrafe other = (Epigrafe) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.Epigrafe[ nombre=" + nombre + " ]";
    }
    
}
