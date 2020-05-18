package app;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
	if (sessionFactory == null) {
	    try {

		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			.configure("hibernate.cfg.xml").build();
		Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();

		sessionFactory = metadata.getSessionFactoryBuilder().build();

	    } catch (Exception e) {
		System.out.println("Исключение!" + e);
	    }
	}
	return sessionFactory;
    }

}
