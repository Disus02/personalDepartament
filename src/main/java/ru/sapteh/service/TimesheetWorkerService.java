package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Timesheet;
import ru.sapteh.model.TimesheetWorker;

import java.util.List;

public class TimesheetWorkerService implements Dao<TimesheetWorker,Integer> {

    private final SessionFactory factory;
    public TimesheetWorkerService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(TimesheetWorker timesheetWorker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(timesheetWorker);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(TimesheetWorker timesheetWorker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(timesheetWorker);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(TimesheetWorker timesheetWorker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(timesheetWorker);
            session.getTransaction().commit();
        }
    }

    @Override
    public TimesheetWorker read(Integer integer) {
        return null;
    }

    @Override
    public List<TimesheetWorker> readByAll() {
        try(Session session= factory.openSession()) {
            Query<TimesheetWorker> documentsQuery=session.createQuery("FROM TimesheetWorker ");
            return documentsQuery.list();
        }
    }
}
