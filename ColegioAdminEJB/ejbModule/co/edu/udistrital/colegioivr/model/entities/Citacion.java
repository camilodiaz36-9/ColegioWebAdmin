/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.colegioivr.model.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camilo.diaz
 */
@Entity
@Table(name = "Citacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citacion.findAll", query = "SELECT c FROM Citacion c")
    , @NamedQuery(name = "Citacion.findByIdCitacion", query = "SELECT c FROM Citacion c WHERE c.idCitacion = :idCitacion")
    , @NamedQuery(name = "Citacion.findByFecha", query = "SELECT c FROM Citacion c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Citacion.findByDetalles", query = "SELECT c FROM Citacion c WHERE c.detalles = :detalles")
    , @NamedQuery(name = "Citacion.findByNumeroCitacion", query = "SELECT c FROM Citacion c WHERE c.numeroCitacion = :numeroCitacion")})
public class Citacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCitacion")
    private Integer idCitacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "detalles")
    private String detalles;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroCitacion")
    private int numeroCitacion;
    @JoinColumn(name = "Estudiante_idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudianteidEstudiante;

    public Citacion() {
    }

    public Citacion(Integer idCitacion) {
        this.idCitacion = idCitacion;
    }

    public Citacion(Integer idCitacion, Date fecha, String detalles, int numeroCitacion) {
        this.idCitacion = idCitacion;
        this.fecha = fecha;
        this.detalles = detalles;
        this.numeroCitacion = numeroCitacion;
    }

    public Integer getIdCitacion() {
        return idCitacion;
    }

    public void setIdCitacion(Integer idCitacion) {
        this.idCitacion = idCitacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getNumeroCitacion() {
        return numeroCitacion;
    }

    public void setNumeroCitacion(int numeroCitacion) {
        this.numeroCitacion = numeroCitacion;
    }

    public Estudiante getEstudianteidEstudiante() {
        return estudianteidEstudiante;
    }

    public void setEstudianteidEstudiante(Estudiante estudianteidEstudiante) {
        this.estudianteidEstudiante = estudianteidEstudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCitacion != null ? idCitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citacion)) {
            return false;
        }
        Citacion other = (Citacion) object;
        if ((this.idCitacion == null && other.idCitacion != null) || (this.idCitacion != null && !this.idCitacion.equals(other.idCitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.colegioivr.model.entities.Citacion[ idCitacion=" + idCitacion + " ]";
    }
    
}
