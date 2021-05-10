package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Status;
import ru.sapteh.model.Timesheet;

import java.util.List;

public class TimesheetService implements Dao<Timesheet,Integer> {

    private final SessionFactory factory;
    public TimesheetService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Timesheet timesheet) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(timesheet);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Timesheet timesheet) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(timesheet);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Timesheet timesheet) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(timesheet);
            session.getTransaction().commit();
        }
    }

    @Override
    public Timesheet read(Integer integer) {
        return null;
    }

    @Override
    public List<Timesheet> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Timesheet> documentsQuery=session.createQuery("FROM Timesheet ");
            return documentsQuery.list();
        }
    }
}
