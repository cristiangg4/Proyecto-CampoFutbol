/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cristiangg.campofutbol.entities;

import es.cristiangg.campofutbol.entities.Estadio;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "DIVISION")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Division.findAll", query = "SELECT d FROM Division d"),
    @javax.persistence.NamedQuery(name = "Division.findById", query = "SELECT d FROM Division d WHERE d.id = :id"),
    @javax.persistence.NamedQuery(name = "Division.findByCodigo", query = "SELECT d FROM Division d WHERE d.codigo = :codigo"),
    @javax.persistence.NamedQuery(name = "Division.findByNombre", query = "SELECT d FROM Division d WHERE d.nombre = :nombre")})
public class Division implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "division")
    private Collection<Estadio> estadioCollection;

    public Division() {
    }

    public Division(Integer id) {
        this.id = id;
    }

    public Division(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Estadio> getEstadioCollection() {
        return estadioCollection;
    }

    public void setEstadioCollection(Collection<Estadio> estadioCollection) {
        this.estadioCollection = estadioCollection;
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
        if (!(object instanceof Division)) {
            return false;
        }
        Division other = (Division) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.cristiangg.campofutbol.entities.Division[ id=" + id + " ]";
    }
    
}
