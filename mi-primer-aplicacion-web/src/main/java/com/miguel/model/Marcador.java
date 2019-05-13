/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miguel.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author miguel
 */
@Entity
@Table(catalog = "test", schema = "mapita")
@NamedQueries(
        @NamedQuery(name = "Marcador.findMarcadores", query = "SELECT m FROM Marcador m")
)
public class Marcador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String descripcion;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String datos;
    @Basic(optional = false)
    @Column(nullable = false)
    private Double latitud;
    @Basic(optional = false)
    @Column(nullable = false)
    private Double longitud;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marcadorId")
    private List<ListaComentario> listaComentarioList;
    @JoinColumn(name = "tema_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Tema temaId;

    public Marcador() {
    }

    public Marcador(Integer id) {
        this.id = id;
    }

    public Marcador(Integer id, String descripcion, String datos, Double latitud, Double longitud) {
        this.id = id;
        this.descripcion = descripcion;
        this.datos = datos;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public List<ListaComentario> getListaComentarioList() {
        return listaComentarioList;
    }

    public void setListaComentarioList(List<ListaComentario> listaComentarioList) {
        this.listaComentarioList = listaComentarioList;
    }

    public Tema getTemaId() {
        return temaId;
    }

    public void setTemaId(Tema temaId) {
        this.temaId = temaId;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
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
        if (!(object instanceof Marcador)) {
            return false;
        }
        Marcador other = (Marcador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.miguel.model.Marcador[ id=" + id + " ]";
    }

}
