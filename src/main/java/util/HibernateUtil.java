package util;

import org.hibernate.SessionFactory;

import java.lang.module.Configuration;

public class HibernateUtil {

    private static final SessionFactory sesionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sesionFactory;
    }

    public static void  shutdown() {
        getSessionFactory().close();
    }
}
