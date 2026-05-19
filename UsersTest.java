package com.smartcomplaint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Users;

public class UsersTest {

    SessionFactory sf = new Configuration()
            .configure("Config.xml")
            .buildSessionFactory();

    // ✅ Delete existing test data before every test run
    @BeforeEach
    public void cleanUp() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.createMutationQuery(
            "DELETE FROM Users WHERE email = 'ravi@gmail.com'")
            .executeUpdate();
        tx.commit();
        session.close();
        System.out.println("🧹 Old test data cleaned!");
    }

    @Test
    public void saveUserTest() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Users user = new Users();
        user.setFullName("Ravi Kumar");
        user.setEmail("ravi@gmail.com");
        user.setPasswordHash("ravi@123");
        user.setPhone("9876543210");
        user.setAddress("Hyderabad");
        user.setStatus(Users.Status.active);

        session.persist(user);
        tx.commit();

        assertNotNull(user.getUserId());
        System.out.println("✅ Save User Test Passed! ID: " + user.getUserId());

        session.close();
    }

    @Test
    public void getUserTest() {
        Session session = sf.openSession();

        // First save a user to get
        Transaction tx = session.beginTransaction();
        Users user = new Users();
        user.setFullName("Ravi Kumar");
        user.setEmail("ravi@gmail.com");
        user.setPasswordHash("ravi@123");
        user.setPhone("9876543210");
        user.setAddress("Hyderabad");
        user.setStatus(Users.Status.active);
        session.persist(user);
        tx.commit();

        // Now get it
        Users found = session.get(Users.class, user.getUserId());

        assertNotNull(found);
        System.out.println("✅ Get User Test Passed! Name: " + found.getFullName()
                + " | Email: " + found.getEmail());

        session.close();
    }
}