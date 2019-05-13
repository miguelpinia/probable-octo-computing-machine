package com.miguel.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.miguel.model.Marcador;
import com.miguel.model.Tema;
import com.miguel.model.UtilityDB;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author miguel
 */
@ManagedBean
@RequestScoped
public class MarcadoresController {

    private MapModel model;
    private String descripcion;
    private String datos;
    private double latitud;
    private double longitud;
    private Tema tema;
    private UtilityDB util;

    @PostConstruct
    public void init() {
        model = new DefaultMapModel();
        util = new UtilityDB();
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public List<Tema> getTemas() {
        return util.obtenTemas();
    }

    public void addMarker() {
        Marker marker = new Marker(new LatLng(latitud, longitud), descripcion);
        model.addOverlay(marker);
        Marcador m = new Marcador();
        m.setDescripcion(descripcion);
        m.setDatos(datos);
        m.setLatitud(latitud);
        m.setLongitud(longitud);
        m.setTemaId(tema);
        util.guardaMarcador(m);
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Marcador añadido", "Lat:" + latitud + ", Lng:" + longitud));
    }

}
