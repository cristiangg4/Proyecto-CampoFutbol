/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cristiangg.campofutbol.entities;

import es.cristiangg.campofutbol.entities.Division;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Usuario
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "ESTADIO")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Estadio.findAll", query = "SELECT e FROM Estadio e"),
    @javax.persistence.NamedQuery(name = "Estadio.findById", query = "SELECT e FROM Estadio e WHERE e.id = :id"),
    @javax.persistence.NamedQuery(name = "Estadio.findByNombre", query = "SELECT e FROM Estadio e WHERE e.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Estadio.findByLocalizacion", query = "SELECT e FROM Estadio e WHERE e.localizacion = :localizacion"),
    @javax.persistence.NamedQuery(name = "Estadio.findByTelefono", query = "SELECT e FROM Estadio e WHERE e.telefono = :telefono"),
    @javax.persistence.NamedQuery(name = "Estadio.findByEmail", query = "SELECT e FROM Estadio e WHERE e.email = :email"),
    @javax.persistence.NamedQuery(name = "Estadio.findByProvincia", query = "SELECT e FROM Estadio e WHERE e.provincia = :provincia"),
    @javax.persistence.NamedQuery(name = "Estadio.findByFechaFundacion", query = "SELECT e FROM Estadio e WHERE e.fechaFundacion = :fechaFundacion"),
    @javax.persistence.NamedQuery(name = "Estadio.findByCategoriaClubs", query = "SELECT e FROM Estadio e WHERE e.categoriaClubs = :categoriaClubs"),
    @javax.persistence.NamedQuery(name = "Estadio.findByPrecio", query = "SELECT e FROM Estadio e WHERE e.precio = :precio"),
    @javax.persistence.NamedQuery(name = "Estadio.findByMedidaCampo", query = "SELECT e FROM Estadio e WHERE e.medidaCampo = :medidaCampo"),
    @javax.persistence.NamedQuery(name = "Estadio.findByEntradasDisponibles", query = "SELECT e FROM Estadio e WHERE e.entradasDisponibles = :entradasDisponibles"),
    @javax.persistence.NamedQuery(name = "Estadio.findByFotoEstadio", query = "SELECT e FROM Estadio e WHERE e.fotoEstadio = :fotoEstadio")})
public class Estadio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "LOCALIZACION")
    private String localizacion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "FECHA_FUNDACION")
    @Temporal(TemporalType.DATE)
    private Date fechaFundacion;
    @Column(name = "CATEGORIA_CLUBS")
    private Character categoriaClubs;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "MEDIDA_CAMPO")
    private Short medidaCampo;
    @Column(name = "ENTRADAS_DISPONIBLES")
    private Boolean entradasDisponibles;
    @Column(name = "FOTO_ESTADIO")
    private String fotoEstadio;
    @JoinColumn(name = "DIVISION", referencedColumnName = "ID")
    @ManyToOne
    private Division division;

    public Estadio() {
    }

    public Estadio(Integer id) {
        this.id = id;
    }

    public Estadio(Integer id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public Character getCategoriaClubs() {
        return categoriaClubs;
    }

    public void setCategoriaClubs(Character categoriaClubs) {
        this.categoriaClubs = categoriaClubs;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Short getMedidaCampo() {
        return medidaCampo;
    }

    public void setMedidaCampo(Short medidaCampo) {
        this.medidaCampo = medidaCampo;
    }

    public Boolean getEntradasDisponibles() {
        return entradasDisponibles;
    }

    public void setEntradasDisponibles(Boolean entradasDisponibles) {
        this.entradasDisponibles = entradasDisponibles;
    }

    public String getFotoEstadio() {
        return fotoEstadio;
    }

    public void setFotoEstadio(String fotoEstadio) {
        this.fotoEstadio = fotoEstadio;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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
        if (!(object instanceof Estadio)) {
            return false;
        }
        Estadio other = (Estadio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.cristiangg.campofutbol.entities.Estadio[ id=" + id + " ]";
    }
    
}
