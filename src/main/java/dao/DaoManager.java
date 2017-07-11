package dao;

import entitys.SensativityTableEntity;
import org.hibernate.Session;

import java.util.Date;

/**
 * Created by root on 11.07.2017.
 */
public class DaoManager {
    public static void setValue(Date date, int value){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        SensativityTableEntity entity = new SensativityTableEntity();
        entity.setTime(new java.sql.Date(date.getTime()));
        entity.setValue(value);
        session.save(entity);
        session.getTransaction().commit();

        session.close();

    }
}
