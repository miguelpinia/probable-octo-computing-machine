package com.miguel.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.miguel.model.Usuario;
import com.miguel.model.UtilityDB;

/**
 *
 * @author miguel
 */
@RequestScoped
@ManagedBean
public class LoginController {

    private static final String INICIO = "secured/inicio?faces-redirect=true";
    private static final String INDEX = "index?faces-redirect=true";

    private String usuario;
    private String contraseña;
    private final UtilityDB utility;

    public LoginController() {
        utility = new UtilityDB();
    }

    public String login() {
        Usuario u = utility.obtenUsuario(usuario, contraseña);
        System.out.println("Holi\n" + u);
        if (u != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", u);
            return INICIO;
        }
        return INDEX;
    }

    /**
     * Cierra la sesión http del usuario.
     *
     * @return La página de inicio.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return INDEX;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
