/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Oposicion.findByControl", query = "SELECT o FROM Oposicion o WHERE o.control = :control"),
    @NamedQuery(name = "Oposicion.findByUrlpdf", query = "SELECT o FROM Oposicion o WHERE o.urlpdf = :urlpdf"),
    @NamedQuery(name = "Oposicion.findByUrlxml", query = "SELECT o FROM Oposicion o WHERE o.urlxml = :urlxml"),
    @NamedQuery(name = "Oposicion.findByTitulo", query = "SELECT o FROM Oposicion o WHERE o.titulo = :titulo")})
public class Oposicion implements Serializable {
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "CONTROL")
    private String control;
    @Basic(optional = false)
    @Column(name = "URLPDF")
    private String urlpdf;
    @Basic(optional = false)
    @Column(name = "URLXML")
    private String urlxml;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oposicion")
    private Collection<ReferenciaAnterior> referenciaAnteriorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oposicion1")
    private Collection<ReferenciaAnterior> referenciaAnteriorCollection1;
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

    public Oposicion(String id, Date fecha, String control, String urlpdf, String urlxml, String titulo) {
        this.id = id;
        this.fecha = fecha;
        this.control = control;
        this.urlpdf = urlpdf;
        this.urlxml = urlxml;
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUrlpdf() {
        return urlpdf;
    }

    public void setUrlpdf(String urlpdf) {
        this.urlpdf = urlpdf;
    }

    public String getUrlxml() {
        return urlxml;
    }

    public void setUrlxml(String urlxml) {
        this.urlxml = urlxml;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public Collection<ReferenciaAnterior> getReferenciaAnteriorCollection() {
        return referenciaAnteriorCollection;
    }

    public void setReferenciaAnteriorCollection(Collection<ReferenciaAnterior> referenciaAnteriorCollection) {
        this.referenciaAnteriorCollection = referenciaAnteriorCollection;
    }

    @XmlTransient
    public Collection<ReferenciaAnterior> getReferenciaAnteriorCollection1() {
        return referenciaAnteriorCollection1;
    }

    public void setReferenciaAnteriorCollection1(Collection<ReferenciaAnterior> referenciaAnteriorCollection1) {
        this.referenciaAnteriorCollection1 = referenciaAnteriorCollection1;
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
        return "Dominio.Oposicion[ id=" + id + " ]";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
