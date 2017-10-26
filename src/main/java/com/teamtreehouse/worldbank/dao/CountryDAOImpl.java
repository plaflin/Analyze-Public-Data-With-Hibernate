package com.teamtreehouse.worldbank.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.teamtreehouse.worldbank.model.Country;

// Implementation for CountryDAO to perform CRUD operations on World Bank table
// More sophisticated than the previous project
public class CountryDAOImpl implements CountryDAO {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } // End SessionFactory buildSessionFactory()

    public String save(Country country) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String id = (String) session.save(country);
        session.getTransaction().commit();
        session.close();
        return id;
    } // End save(country)

    @SuppressWarnings("unchecked")
    public List<Country> fetchAllCountries(){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Country.class);
        List<Country> countries = criteria.list();
        session.close();
        return countries;
    }  // End fetchAllCountries()

    public void update(Country country) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(country);
        session.getTransaction().commit();
        session.close();
    }  // End update(country)

    public void delete(Country country) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(country);
        session.getTransaction().commit();
        session.close();
    } // End delete(country)

} // End CountryDAOImpl.java
