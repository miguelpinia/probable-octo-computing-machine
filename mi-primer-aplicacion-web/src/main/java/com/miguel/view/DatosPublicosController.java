package com.miguel.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.miguel.model.Marcador;
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
public class DatosPublicosController {

    private MapModel model;
    private final UtilityDB util;

    public DatosPublicosController() {
        util = new UtilityDB();
        model = new DefaultMapModel();
        List<Marcador> marcadores = util.obtenMarcadores();
        marcadores.forEach((marcador) -> {
            model.addOverlay(new Marker(new LatLng(marcador.getLatitud(), marcador.getLongitud()), marcador.getDescripcion()));
        });
    }

    public MapModel getModel() {
        return model;
    }

}
