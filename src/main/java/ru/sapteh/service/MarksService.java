package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Education;
import ru.sapteh.model.Marks;

import java.util.List;

public class MarksService implements Dao<Marks,Integer> {

    private final SessionFactory factory;
    public MarksService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Marks marks) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(marks);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Marks marks) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(marks);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Marks marks) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(marks);
            session.getTransaction().commit();
        }
    }

    @Override
    public Marks read(Integer integer) {
        return null;
    }

    @Override
    public List<Marks> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Marks> documentsQuery=session.createQuery("FROM Marks ");
            return documentsQuery.list();
        }
    }
}
