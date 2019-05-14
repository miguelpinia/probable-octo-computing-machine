package com.miguel.view;

import javax.faces.application.FacesMessage;
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

    private String usuario;
    private String contraseña;
    private final UtilityDB utility;

    public LoginController() {
        utility = new UtilityDB();
    }

    public String login() {
        Usuario u = utility.obtenUsuario(usuario, contraseña);
        if (u != null) {
            if (!u.isActivo()) {
                FacesContext.getCurrentInstance()
                        .addMessage("loginGrowl",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Error!", "Verificar correo de validación"));
                return Pages.INDEX;
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", u);
            return Pages.INICIO;
        }
        FacesContext.getCurrentInstance()
                .addMessage("loginGrowl",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error!", "Error al iniciar sesión, verificar usuario y/o contraseña"));
        return Pages.INDEX;
    }

    /**
     * Cierra la sesión http del usuario.
     *
     * @return La página de inicio.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return Pages.INDEX;
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
