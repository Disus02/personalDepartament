package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Medical;
import ru.sapteh.model.Organization;

import java.util.List;

public class OrganizationService implements Dao<Organization,Integer> {
    private final SessionFactory factory;
    public OrganizationService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(Organization organization) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(organization);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Organization organization) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(organization);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Organization organization) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(organization);
            session.getTransaction().commit();
        }
    }

    @Override
    public Organization read(Integer integer) {
        try(Session session=factory.openSession()) {
            return session.get(Organization.class,integer);
        }
    }

    @Override
    public List<Organization> readByAll() {
        try(Session session= factory.openSession()) {
            Query<Organization> documentsQuery=session.createQuery("FROM Organization ");
            return documentsQuery.list();
        }
    }
}
