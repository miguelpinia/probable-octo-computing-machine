package com.miguel.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.miguel.model.Usuario;

import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author miguel
 */
@RequestScoped
@ManagedBean
public class UserBean {

    private FacesContext context;

    public UserBean() {
        context = FacesContext.getCurrentInstance();
    }

    public boolean isLogged() {
        return getUsuario() != null;
    }

    public Usuario getUsuario() {
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    public StreamedContent getFotografia() {
        Usuario u = getUsuario();
        if (u.getFotografia() != null) {
            return new ByteArrayContent(u.getFotografia());
        }
        return null;
    }

}
