package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Division;
import ru.sapteh.model.Documents;

import java.util.List;

public class DocumentsService implements Dao<Documents,Integer> {
    private final SessionFactory factory;
    public DocumentsService(SessionFactory factory){
        this.factory=factory;
    }

    @Override
    public void create(Documents documents) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(documents);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Documents documents) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(documents);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Documents documents) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(documents);
            session.getTransaction().commit();
        }
    }

    @Override
    public Documents read(Integer integer) {
        return null;
    }

    @Override
    public List<Documents> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Documents> documentsQuery=session.createQuery("FROM Documents ");
            return documentsQuery.list();
        }
    }
}
