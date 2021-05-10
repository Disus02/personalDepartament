package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Marks;
import ru.sapteh.model.Medical;

import java.util.List;

public class MedicalService implements Dao<Medical,Integer> {
    private final SessionFactory factory;
    public MedicalService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Medical medical) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(medical);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Medical medical) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(medical);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Medical medical) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(medical);
            session.getTransaction().commit();
        }
    }

    @Override
    public Medical read(Integer integer) {
        return null;
    }

    @Override
    public List<Medical> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Medical> documentsQuery=session.createQuery("FROM Medical ");
            return documentsQuery.list();
        }
    }
}
