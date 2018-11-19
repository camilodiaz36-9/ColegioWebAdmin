/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.colegioivr.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author camilo.diaz
 */
@Entity
@Table(name = "Acudiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acudiente.findAll", query = "SELECT a FROM Acudiente a")
    , @NamedQuery(name = "Acudiente.findByIdAcudiente", query = "SELECT a FROM Acudiente a WHERE a.idAcudiente = :idAcudiente")
    , @NamedQuery(name = "Acudiente.findByNombre", query = "SELECT a FROM Acudiente a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Acudiente.findByApellido", query = "SELECT a FROM Acudiente a WHERE a.apellido = :apellido")
    , @NamedQuery(name = "Acudiente.findByNumeroIdentidad", query = "SELECT a FROM Acudiente a WHERE a.numeroIdentidad = :numeroIdentidad")
    , @NamedQuery(name = "Acudiente.findByTelefono", query = "SELECT a FROM Acudiente a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Acudiente.findByCorreo", query = "SELECT a FROM Acudiente a WHERE a.correo = :correo")})
public class Acudiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAcudiente")
    private Integer idAcudiente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroIdentidad")
    private int numeroIdentidad;
    @Column(name = "telefono")
    private Integer telefono;
    @Size(max = 256)
    @Column(name = "correo")
    private String correo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acudienteidAcudiente")
    private List<Estudiante> estudianteList;

    public Acudiente() {
    }

    public Acudiente(Integer idAcudiente) {
        this.idAcudiente = idAcudiente;
    }

    public Acudiente(Integer idAcudiente, String nombre, String apellido, int numeroIdentidad) {
        this.idAcudiente = idAcudiente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentidad = numeroIdentidad;
    }

    public Integer getIdAcudiente() {
        return idAcudiente;
    }

    public void setIdAcudiente(Integer idAcudiente) {
        this.idAcudiente = idAcudiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNumeroIdentidad() {
        return numeroIdentidad;
    }

    public void setNumeroIdentidad(int numeroIdentidad) {
        this.numeroIdentidad = numeroIdentidad;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcudiente != null ? idAcudiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acudiente)) {
            return false;
        }
        Acudiente other = (Acudiente) object;
        if ((this.idAcudiente == null && other.idAcudiente != null) || (this.idAcudiente != null && !this.idAcudiente.equals(other.idAcudiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.colegioivr.model.entities.Acudiente[ idAcudiente=" + idAcudiente + " ]";
    }
    
}
