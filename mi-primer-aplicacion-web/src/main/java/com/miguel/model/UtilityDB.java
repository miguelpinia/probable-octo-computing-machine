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

    static Session session;

    public Usuario obtenUsuario(String nombre, String password) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.getNamedQuery("Usuario.findByNombrePassword");
            query.setParameter("nombre", nombre).setParameter("password", password);
            Usuario usuario = (Usuario) query.uniqueResult();
            return usuario;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void guardaUsuario(Usuario usuario) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            // Le asignamos el rol de usuario
            UsuarioRol ur = new UsuarioRol();
            ur.setRolId(new Rol(1));
            ur.setUsuarioId(usuario);
            usuario.setUsuarioRolList(new ArrayList<>(Arrays.asList(ur)));
            session.save(usuario);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (null != session.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void actualizaUsuario(Usuario usuario) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (null != session.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Usuario obtenUsuario(String hash) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.getNamedQuery("Usuario.findByHash");
            query.setParameter("hash", hash);
            Usuario usuario = (Usuario) query.uniqueResult();
            return usuario;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
