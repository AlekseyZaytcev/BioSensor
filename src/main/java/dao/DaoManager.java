package dao;

import Time.MyTime;
import entitys.SensativityTableEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 11.07.2017.
 */
public class DaoManager {
    static MyTime time = new MyTime();

    public static void setValue(int value) {
        EntityManagerFactory factory = HibernateOGMUtil.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        SensativityTableEntity entity = new SensativityTableEntity();
        Date date = new Date();
        entity.setDate(date);
        entity.setValue(value);
        manager.persist(entity);
        manager.getTransaction().commit();
        manager.close();
    }

    public static List<SensativityTableEntity> getBioEntity(Date timeStart, Date timeEnd) {
        EntityManagerFactory factory = HibernateOGMUtil.getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        List<SensativityTableEntity> result = null;
        result = manager.createNamedQuery("GET_VALUE_BY_DATE", SensativityTableEntity.class).setParameter("timestart", timeStart)
                .setParameter("timeend", timeEnd).getResultList();
        manager.close();
        return result;
    }
}
