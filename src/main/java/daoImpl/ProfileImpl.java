package daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import app.HibernateSessionFactory;
import dao.CrudInterface;
import entity.Profile;

public class ProfileImpl implements CrudInterface<Profile> {

    public void create(Profile obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.save(obj);
	transaction.commit();
	session.close();
    }

    public void update(Profile obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.update(obj);
	transaction.commit();
	session.close();
    }

    public void delete(Profile obj) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Transaction transaction = session.beginTransaction();
	session.delete(obj);
	transaction.commit();
	session.close();
    }

    @SuppressWarnings("deprecation")
    public Profile findByName(String name) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Object result = session.createQuery("From Profile where login =: name").setString("name", name).uniqueResult();
	session.close();
	return (Profile) result;
    }

    @SuppressWarnings("unchecked")
    public List<Profile> findAll() {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	List<Profile> profiles = session.createQuery("From Profile").list();
	// session.close();
	return profiles;
    }

    public Profile findById(int id) {
	// TODO Auto-generated method stub
	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	Object result = session.get(Profile.class, id);
	session.close();
	return (Profile) result;
    }

}
