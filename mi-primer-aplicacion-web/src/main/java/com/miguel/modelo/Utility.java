/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miguel.modelo;

import java.util.Date;
import java.util.Random;

import org.hibernate.Session;

/**
 *
 * @author miguel
 */
public class Utility {

    static User userObj;
    static Session sessionObj;

    public void save() {
        Random r = new Random();
        System.out.println(".......Hibernate Maven Example.......\n");
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            System.out.println("Sssion " + sessionObj);
            sessionObj.beginTransaction();

            for (int i = 101; i <= 105; i++) {
                userObj = new User();
                userObj.setName("Editor " + r.nextInt());
                userObj.setCreatedBy("Administrator");
                userObj.setCreatedDate(new Date());

                sessionObj.save(userObj);
            }
            System.out.println("\n.......Records Saved Successfully To The Database.......\n");

            // Committing The Transactions To The Database
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
