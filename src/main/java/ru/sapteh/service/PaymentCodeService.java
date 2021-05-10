package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Passport;
import ru.sapteh.model.PaymentCode;

import java.util.List;

public class PaymentCodeService implements Dao<PaymentCode,Integer> {

    private final SessionFactory factory;
    public PaymentCodeService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(PaymentCode paymentCode) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(paymentCode);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PaymentCode paymentCode) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(paymentCode);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(PaymentCode paymentCode) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(paymentCode);
            session.getTransaction().commit();
        }
    }

    @Override
    public PaymentCode read(Integer integer) {
        return null;
    }

    @Override
    public List<PaymentCode> readByAll() {
        try(Session session= factory.openSession()) {
            Query<PaymentCode> documentsQuery=session.createQuery("FROM PaymentCode ");
            return documentsQuery.list();
        }
    }
}
