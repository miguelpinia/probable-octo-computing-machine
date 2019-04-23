/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miguel.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author miguel
 */
public class UtilityDB {

    static Session sessionObj;

    public Usuario obtenUsuario(String nombre, String password) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            Query query = sessionObj.getNamedQuery("Usuario.findByNombrePassword");
            query.setParameter("nombre", nombre).setParameter("password", password);
            Usuario usuario = (Usuario) query.uniqueResult();
            return usuario;
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }

    public void guardaUsuario(Usuario usuario) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            // Le asignamos el rol de usuario
            UsuarioRol ur = new UsuarioRol();
            ur.setRolId(new Rol(1));
            ur.setUsuarioId(usuario);
            usuario.setUsuarioRolList(new ArrayList<>(Arrays.asList(ur)));
            sessionObj.save(usuario);
            sessionObj.getTransaction().commit();
        } catch (HibernateException ex) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }

}
