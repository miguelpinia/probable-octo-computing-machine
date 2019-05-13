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
@Table(catalog = "test", schema = "mapita", name = "tema")
@NamedQueries(
        @NamedQuery(name = "Tema.findTemas", query = "SELECT t from Tema t")
)
public class Tema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String datos;
    @JoinColumn(name = "color_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Color colorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temaId")
    private List<Marcador> marcadorList;

    public Tema() {
    }

    public Tema(Integer id) {
        this.id = id;
    }

    public Tema(Integer id, String nombre, String datos) {
        this.id = id;
        this.nombre = nombre;
        this.datos = datos;
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

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public Color getColorId() {
        return colorId;
    }

    public void setColorId(Color colorId) {
        this.colorId = colorId;
    }

    public List<Marcador> getMarcadorList() {
        return marcadorList;
    }

    public void setMarcadorList(List<Marcador> marcadorList) {
        this.marcadorList = marcadorList;
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
        if (!(object instanceof Tema)) {
            return false;
        }
        Tema other = (Tema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
