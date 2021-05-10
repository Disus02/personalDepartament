package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.DocumentType;
import ru.sapteh.model.Documents;

import java.util.List;

public class DocumentTypeService implements Dao<DocumentType,Integer> {

    private final SessionFactory factory;
    public DocumentTypeService(SessionFactory factory){
        this.factory=factory;
    }
    @Override
    public void create(DocumentType documentType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.save(documentType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(DocumentType documentType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.update(documentType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(DocumentType documentType) {
        try(Session session= factory.openSession()) {
            session.beginTransaction();
            session.delete(documentType);
            session.getTransaction().commit();
        }
    }

    @Override
    public DocumentType read(Integer integer) {
        return null;
    }

    @Override
    public List<DocumentType> readByAll() {
        try(Session session= factory.openSession()) {
            Query<DocumentType> documentsQuery=session.createQuery("FROM DocumentType ");
            return documentsQuery.list();
        }
    }
}
