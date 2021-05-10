package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Role;
import ru.sapteh.model.Status;

import java.util.List;

public class StatusService implements Dao<Status,Integer> {

    private final SessionFactory factory;
    public StatusService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Status status) {

    }

    @Override
    public void update(Status status) {

    }

    @Override
    public void delete(Status status) {

    }

    @Override
    public Status read(Integer integer) {
        return null;
    }

    @Override
    public List<Status> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Status> documentsQuery=session.createQuery("FROM Status ");
            return documentsQuery.list();
        }
    }
}
