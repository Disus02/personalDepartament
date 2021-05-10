package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Users;
import ru.sapteh.model.WorkBook;

import java.util.List;

public class WorkBookService implements Dao<WorkBook,Integer> {


    private final SessionFactory factory;
    public WorkBookService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(WorkBook workBook) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(workBook);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(WorkBook workBook) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(workBook);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(WorkBook workBook) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(workBook);
            session.getTransaction().commit();
        }
    }

    @Override
    public WorkBook read(Integer integer) {
        return null;
    }

    @Override
    public List<WorkBook> readByAll() {
        try(Session session= factory.openSession()) {
            Query<WorkBook> documentsQuery=session.createQuery("FROM WorkBook ");
            return documentsQuery.list();
        }
    }
}
