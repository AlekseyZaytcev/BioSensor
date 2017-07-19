package dao;

import entitys.SensativityTableEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 11.07.2017.
 */
public class DaoManager {

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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

    public static List<SensativityTableEntity> getBioEntity(String timestart, String timeend) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<SensativityTableEntity> result = null;
        try {
            Timestamp timestampStart = new Timestamp(format.parse(timestart).getTime());
            Timestamp timestampEnd = new Timestamp(format.parse(timeend).getTime());
            result = session.createNamedQuery("GET_VALUE_BY_DATE").getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.close();
        return result;
    }
}
