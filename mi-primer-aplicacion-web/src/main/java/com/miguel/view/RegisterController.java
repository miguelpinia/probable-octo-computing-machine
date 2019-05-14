package com.miguel.view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final UtilityDB u = new UtilityDB();
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
                    .addMessage("registroForm:growl",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error!", "Fallo de registro: Las contraseñas deben coincidir"));
        } else if (u.existeCorreo(user.getCorreo())) {
            FacesContext.getCurrentInstance()
                    .addMessage("registroForm:growl",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error!", "El correo ya ha sido registrado."));
        } else {
            if (fotografia != null) {
                user.setFotografia(fotografia.getContents());
            } else {
                user.setFotografia(null);
            }
            String nombre = user.getNombre();
            String pass = user.getPassword();
            String hash = md5(user.getNombre() + user.getPassword() + user.getCorreo());
            user.setActivacion(hash);
            u.guardaUsuario(user);
            user = null;
            FacesContext.getCurrentInstance()
                    .addMessage("growl",
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Info", "Felicidades, el registro se ha realizado correctamente"));

        }
        return Pages.REGISTRO;
    }

    public static String md5(String entrada) {
        try {
            char[] CONSTS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] salida = digest.digest(entrada.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * salida.length);
            for (int i = 0; i < salida.length; i++) {
                int bajo = (int) (salida[i] & 0x0f);
                int alto = (int) ((salida[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
