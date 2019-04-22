package com.miguel.proyecto.web;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.miguel.modelo.User;
import com.miguel.model.Utility;

@ManagedBean
@RequestScoped
public class RegisterController {

    private User user = new User();
    private Utility u = new Utility();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegisterController() {
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }

    public String addUser() {
        if (!user.getContrasena().equals(user.getConfirmaContrasena())) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Fallo de registro: Las contraseñas deben coincidir", ""));
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Felicidades, el registro se ha realizado correctamente", ""));
            u.save(user);
            user = null;
        }
        return null;
    }
}
