package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Organization;
import ru.sapteh.model.Passport;

import java.util.List;

public class PassportService implements Dao<Passport,Integer> {

    private final SessionFactory factory;
    public PassportService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Passport passport) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(passport);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Passport passport) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(passport);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Passport passport) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(passport);
            session.getTransaction().commit();
        }
    }

    @Override
    public Passport read(Integer integer) {
        return null;
    }

    @Override
    public List<Passport> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Passport> documentsQuery=session.createQuery("FROM Passport ");
            return documentsQuery.list();
        }
    }
}
