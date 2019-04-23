package com.miguel.view;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.miguel.model.Usuario;
import com.miguel.model.UtilityDB;

import org.primefaces.model.UploadedFile;

@ManagedBean
@RequestScoped
public class RegisterController {

    private Usuario user = new Usuario();
    private UtilityDB u = new UtilityDB();
    private String confirmacionPassword;
    private UploadedFile fotografia;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getConfirmacionPassword() {
        return confirmacionPassword;
    }

    public void setConfirmacionPassword(String confirmacionPassword) {
        this.confirmacionPassword = confirmacionPassword;
    }

    public UploadedFile getFotografia() {
        return fotografia;
    }

    public void setFotografia(UploadedFile fotografia) {
        this.fotografia = fotografia;
    }

    public RegisterController() {
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }

    public String addUser() {
        if (!user.getPassword().equals(confirmacionPassword)) {
            FacesContext.getCurrentInstance()
                    .addMessage("growl",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error!", "Fallo de registro: Las contraseñas deben coincidir"));
        } else {
            if (fotografia != null) {
                user.setFotografia(fotografia.getContents());
            } else {
                user.setFotografia(null);
            }
            u.guardaUsuario(user);
            user = null;
            FacesContext.getCurrentInstance()
                    .addMessage("growl",
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Info", "Felicidades, el registro se ha realizado correctamente"));

        }
        return Pages.REGISTRO;
    }
}
