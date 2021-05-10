package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.CorrespondentAccount;

import java.util.List;

public class CorrespondentAccountService implements Dao<CorrespondentAccount,Integer> {

    private final SessionFactory factory;
    public CorrespondentAccountService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(CorrespondentAccount correspondentAccount) {

    }

    @Override
    public void update(CorrespondentAccount correspondentAccount) {

    }

    @Override
    public void delete(CorrespondentAccount correspondentAccount) {

    }

    @Override
    public CorrespondentAccount read(Integer integer) {
        return null;
    }

    @Override
    public List<CorrespondentAccount> readByAll() {
        try(Session session= factory.openSession()) {
            Query<CorrespondentAccount> accountQuery=session.createQuery("FROM CorrespondentAccount ");
            return accountQuery.list();
        }
    }
}
