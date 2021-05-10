package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.WorkBook;
import ru.sapteh.model.Worker;

import java.util.List;

public class WorkerService implements Dao<Worker,Integer> {

    private final SessionFactory factory;
    public WorkerService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Worker worker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(worker);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Worker worker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(worker);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Worker worker) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(worker);
            session.getTransaction().commit();
        }
    }

    @Override
    public Worker read(Integer integer) {
        return null;
    }

    @Override
    public List<Worker> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Worker> documentsQuery=session.createQuery("FROM Worker ");
            return documentsQuery.list();
        }
    }
}
