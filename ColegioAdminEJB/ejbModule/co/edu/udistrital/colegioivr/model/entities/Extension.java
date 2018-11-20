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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author camilo.diaz
 */
@Entity
@Table(name = "Extension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Extension.findAll", query = "SELECT e FROM Extension e")
    , @NamedQuery(name = "Extension.findByIdExtension", query = "SELECT e FROM Extension e WHERE e.idExtension = :idExtension")
    , @NamedQuery(name = "Extension.findByNumero", query = "SELECT e FROM Extension e WHERE e.numero = :numero")})
public class Extension implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idExtension")
    private Integer idExtension;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private int numero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extension")
    private List<Usuario> usuarioList;

    public Extension() {
    }

    public Extension(Integer idExtension) {
        this.idExtension = idExtension;
    }

    public Extension(Integer idExtension, int numero) {
        this.idExtension = idExtension;
        this.numero = numero;
    }

    public Integer getIdExtension() {
        return idExtension;
    }

    public void setIdExtension(Integer idExtension) {
        this.idExtension = idExtension;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExtension != null ? idExtension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Extension)) {
            return false;
        }
        Extension other = (Extension) object;
        if ((this.idExtension == null && other.idExtension != null) || (this.idExtension != null && !this.idExtension.equals(other.idExtension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.colegioivr.model.entities.Extension[ idExtension=" + idExtension + " ]";
    }
    
}
