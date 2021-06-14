/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javie
 */
@Entity
@Table(name = "REFERENCIA_ANTERIOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReferenciaAnterior.findAll", query = "SELECT r FROM ReferenciaAnterior r"),
    @NamedQuery(name = "ReferenciaAnterior.findByIdRefAnterior", query = "SELECT r FROM ReferenciaAnterior r WHERE r.referenciaAnteriorPK.idRefAnterior = :idRefAnterior"),
    @NamedQuery(name = "ReferenciaAnterior.findByIdRefPosterior", query = "SELECT r FROM ReferenciaAnterior r WHERE r.referenciaAnteriorPK.idRefPosterior = :idRefPosterior"),
    @NamedQuery(name = "ReferenciaAnterior.findByPalabra", query = "SELECT r FROM ReferenciaAnterior r WHERE r.palabra = :palabra"),
    @NamedQuery(name = "ReferenciaAnterior.findByTexto", query = "SELECT r FROM ReferenciaAnterior r WHERE r.texto = :texto")})
public class ReferenciaAnterior implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReferenciaAnteriorPK referenciaAnteriorPK;
    @Size(max = 1000)
    @Column(name = "PALABRA")
    private String palabra;
    @Size(max = 1000)
    @Column(name = "TEXTO")
    private String texto;
    @JoinColumn(name = "ID_REF_POSTERIOR", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Oposicion oposicion;
    @JoinColumn(name = "ID_REF_ANTERIOR", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Oposicion oposicion1;

    public ReferenciaAnterior() {
    }

    public ReferenciaAnterior(ReferenciaAnteriorPK referenciaAnteriorPK) {
        this.referenciaAnteriorPK = referenciaAnteriorPK;
    }

    public ReferenciaAnterior(String idRefAnterior, String idRefPosterior) {
        this.referenciaAnteriorPK = new ReferenciaAnteriorPK(idRefAnterior, idRefPosterior);
    }
    
    public ReferenciaAnterior(String idRefAnterior, String idRefPosterior,String palabra, String texto) {
        this.referenciaAnteriorPK = new ReferenciaAnteriorPK(idRefAnterior, idRefPosterior);
        this.palabra = palabra;
        this.texto = texto;
    }

    public ReferenciaAnteriorPK getReferenciaAnteriorPK() {
        return referenciaAnteriorPK;
    }

    public void setReferenciaAnteriorPK(ReferenciaAnteriorPK referenciaAnteriorPK) {
        this.referenciaAnteriorPK = referenciaAnteriorPK;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Oposicion getOposicion() {
        return oposicion;
    }

    public void setOposicion(Oposicion oposicion) {
        this.oposicion = oposicion;
    }

    public Oposicion getOposicion1() {
        return oposicion1;
    }

    public void setOposicion1(Oposicion oposicion1) {
        this.oposicion1 = oposicion1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenciaAnteriorPK != null ? referenciaAnteriorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenciaAnterior)) {
            return false;
        }
        ReferenciaAnterior other = (ReferenciaAnterior) object;
        if ((this.referenciaAnteriorPK == null && other.referenciaAnteriorPK != null) || (this.referenciaAnteriorPK != null && !this.referenciaAnteriorPK.equals(other.referenciaAnteriorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.ReferenciaAnterior[ referenciaAnteriorPK=" + referenciaAnteriorPK + " ]";
    }
    
}
