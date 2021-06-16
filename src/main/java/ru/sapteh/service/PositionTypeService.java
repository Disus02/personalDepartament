package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.PaymentCode;
import ru.sapteh.model.PositionType;

import java.util.List;

public class PositionTypeService implements Dao<PositionType,Integer> {

    private final SessionFactory factory;
    public PositionTypeService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(PositionType positionType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(positionType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PositionType positionType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(positionType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(PositionType positionType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(positionType);
            session.getTransaction().commit();
        }
    }

    @Override
    public PositionType read(Integer integer) {
        return null;
    }

    @Override
    public List<PositionType> readByAll() {
        try(Session session= factory.openSession()) {
            Query<PositionType> documentsQuery=session.createQuery("FROM PositionType ");
            return documentsQuery.list();
        }
    }
}
