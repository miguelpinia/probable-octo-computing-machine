package com.miguel.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.miguel.model.Usuario;
import com.miguel.model.UtilityDB;

/**
 *
 * @author miguel
 */
public class Validacion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String hash = request.getParameter("val");
        UtilityDB util = new UtilityDB();
        System.out.println("Hash: " + hash);
        if (hash != null) {
            Usuario u = util.obtenUsuario(hash);
            u.setActivo(true);
            util.actualizaUsuario(u);
        }
        response.sendRedirect("index.xhtml");
        return;
    }

}
