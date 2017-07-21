package dao;

import Time.MyTime;
import entitys.SensativityTableEntity;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 * Created by root on 11.07.2017.
 */
public class DaoManager {
    static MyTime time = new MyTime();

    public static void setValue(int value) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        SensativityTableEntity entity = new SensativityTableEntity();
        Timestamp currentTime = time.currentTime();
        entity.setTime(currentTime);
        entity.setValue(value);
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<SensativityTableEntity> getBioEntity(String timestart, String timeend) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<SensativityTableEntity> result = null;
        try {
            Timestamp timestampStart = time.timeStart(timestart);
            Timestamp timestampEnd = time.timeEnd(timeend);
            result = session.createNamedQuery("GET_VALUE_BY_DATE").setParameter("timestart", timestampStart).setParameter("timeend", timestampEnd).getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.close();
        return result;
    }
}
