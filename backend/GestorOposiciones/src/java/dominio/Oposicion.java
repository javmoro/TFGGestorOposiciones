/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javie
 */
@Entity
@Table(name = "OPOSICION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oposicion.findAll", query = "SELECT o FROM Oposicion o"),
    @NamedQuery(name = "Oposicion.findById", query = "SELECT o FROM Oposicion o WHERE o.id = :id"),
    @NamedQuery(name = "Oposicion.findByFecha", query = "SELECT o FROM Oposicion o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "Oposicion.findByControl", query = "SELECT o FROM Oposicion o WHERE o.control = :control")})
public class Oposicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "FECHA")
    private String fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CONTROL")
    private String control;
    @JoinColumns({
        @JoinColumn(name = "NOMBREEP", referencedColumnName = "NOMBREEP"),
        @JoinColumn(name = "ETQDEP", referencedColumnName = "ETQDEP")})
    @ManyToOne(optional = false)
    private RelDepEpi relDepEpi;

    public Oposicion() {
    }

    public Oposicion(String id) {
        this.id = id;
    }

    public Oposicion(String id, String fecha, String control) {
        this.id = id;
        this.fecha = fecha;
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public RelDepEpi getRelDepEpi() {
        return relDepEpi;
    }

    public void setRelDepEpi(RelDepEpi relDepEpi) {
        this.relDepEpi = relDepEpi;
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
        if (!(object instanceof Oposicion)) {
            return false;
        }
        Oposicion other = (Oposicion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.Oposicion[ id=" + id + " ]";
    }
    
}
