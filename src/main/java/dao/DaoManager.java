package dao;

import entitys.SensativityTableEntity;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 11.07.2017.
 */
public class DaoManager {
    public static void setValue(int value) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        SensativityTableEntity entity = new SensativityTableEntity();

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        entity.setTime(currentTimestamp);
        entity.setValue(value);
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<String> getDate() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        org.hibernate.query.Query query = session.createQuery("SELECT time FROM SensativityTableEntity ORDER BY id");
        List<String> list = query.list();
        session.close();
        return list;
    }

    public static List<String> getValue() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        org.hibernate.query.Query query = session.createQuery("SELECT value FROM SensativityTableEntity ORDER BY id");
        List<String> list = query.list();
        session.close();
        return list;
    }
}
