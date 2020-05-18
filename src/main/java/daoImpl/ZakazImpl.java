package daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import app.HibernateSessionFactory;
import dao.CrudInterface;
import entity.Zakaz;

public class ZakazImpl implements CrudInterface<Zakaz> {

    public void create(Zakaz obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.save(obj);
	transaction.commit();
	session.close();
    }

    public void update(Zakaz obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.update(obj);
	transaction.commit();
	session.close();
    }

    public void delete(Zakaz obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.delete(obj);
	transaction.commit();
	session.close();
    }

    public Zakaz findByName(String name) {
	// TODO Auto-generated method stub
	return null;
    }

    public Zakaz findById(int id) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Object result = session.get(Zakaz.class, id);
	session.close();
	return (Zakaz) result;
    }

    @SuppressWarnings("unchecked")
    public List<Zakaz> findAll() {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	List<Zakaz> zakazes = session.createQuery("From Zakaz").list();
	session.close();
	return zakazes;
    }

}
