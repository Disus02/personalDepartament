package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.CorrespondentAccount;
import ru.sapteh.model.Division;

import java.util.List;

public class DivisionService implements Dao<Division,Integer> {
    private final SessionFactory factory;
    public DivisionService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Division division) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(division);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Division division) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(division);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Division division) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(division);
            session.getTransaction().commit();
        }
    }

    @Override
    public Division read(Integer integer) {
        return null;
    }

    @Override
    public List<Division> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Division> divisionQuery=session.createQuery("FROM Division ");
            return divisionQuery.list();
        }
    }
}
