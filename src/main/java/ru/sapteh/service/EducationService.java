package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Education;

import java.util.List;

public class EducationService implements Dao<Education,Integer> {

    private final SessionFactory factory;
    public EducationService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Education education) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(education);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Education education) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(education);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Education education) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(education);
            session.getTransaction().commit();
        }
    }

    @Override
    public Education read(Integer integer) {
        return null;
    }

    @Override
    public List<Education> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Education> documentsQuery=session.createQuery("FROM Education ");
            return documentsQuery.list();
        }
    }
}
