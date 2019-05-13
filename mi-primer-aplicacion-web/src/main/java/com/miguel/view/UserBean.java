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

    private static final String USUARIO = "USUARIO";
    private static final String INFORMADOR = "INFORMADOR";
    private static final String ADMINISTRADOR = "ADMINISTRADOR";

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

    public boolean isAdmin() {
        Usuario u = getUsuario();
        return u.getUsuarioRolList().stream().anyMatch((rol) -> (rol.getRolId().getRol().equals(ADMINISTRADOR)));
    }

    public boolean isInformador() {
        Usuario u = getUsuario();
        return u.getUsuarioRolList().stream().anyMatch((rol) -> (rol.getRolId().getRol().equals(INFORMADOR)));
    }

    public boolean isAdminOrInformador() {
        return isAdmin() || isInformador();
    }

}
