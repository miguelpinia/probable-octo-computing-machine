/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miguel.model;

import com.miguel.modelo.User;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author miguel
 */
public class UtilityDB {

    static Session sessionObj;

    public Usuario obtenUsuario(String nombre, String password) {
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        Query query = sessionObj.getNamedQuery("Usuario.findByNombrePassword");
        query.setParameter("nombre", nombre).setParameter("password", password);
        Usuario usuario = (Usuario) query.uniqueResult();
        return usuario;
    }

    public void save(User usuario) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(usuario);
            sessionObj.getTransaction().commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }

}
