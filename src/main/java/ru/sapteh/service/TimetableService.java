package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.TimesheetWorker;
import ru.sapteh.model.Timetable;

import java.util.List;

public class TimetableService implements Dao<Timetable,Integer> {

    private final SessionFactory factory;
    public TimetableService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Timetable timetable) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(timetable);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Timetable timetable) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(timetable);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Timetable timetable) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(timetable);
            session.getTransaction().commit();
        }
    }

    @Override
    public Timetable read(Integer integer) {
        return null;
    }

    @Override
    public List<Timetable> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Timetable> documentsQuery=session.createQuery("FROM Timetable ");
            return documentsQuery.list();
        }
    }
}
