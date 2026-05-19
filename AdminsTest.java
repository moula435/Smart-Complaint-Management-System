package com.smartcomplaint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Admins;

public class AdminsTest {

    SessionFactory sf = new Configuration()
            .configure("Config.xml")
            .buildSessionFactory();

    // ✅ Delete existing test data before every test run
    @BeforeEach
    public void cleanUp() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.createMutationQuery(
            "DELETE FROM Admins WHERE email = 'admin@complaint.com'")
            .executeUpdate();
        tx.commit();
        session.close();
        System.out.println("🧹 Old test data cleaned!");
    }

    @Test
    public void saveAdminTest() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Admins admin = new Admins();
        admin.setFullName("Super Admin");
        admin.setEmail("admin@complaint.com");
        admin.setPasswordHash("admin@123");
        admin.setPhone("9999999999");
        admin.setRole(Admins.Role.superadmin);

        session.persist(admin);
        tx.commit();

        assertNotNull(admin.getAdminId());
        System.out.println("✅ Save Admin Test Passed! ID: " + admin.getAdminId());

        session.close();
    }

    @Test
    public void getAdminTest() {
        Session session = sf.openSession();

        // First save an admin to get
        Transaction tx = session.beginTransaction();
        Admins admin = new Admins();
        admin.setFullName("Super Admin");
        admin.setEmail("admin@complaint.com");
        admin.setPasswordHash("admin@123");
        admin.setPhone("9999999999");
        admin.setRole(Admins.Role.superadmin);
        session.persist(admin);
        tx.commit();

        // Now get it
        Admins found = session.get(Admins.class, admin.getAdminId());

        assertNotNull(found);
        System.out.println("✅ Get Admin Test Passed! Name: " + found.getFullName()
                + " | Role: " + found.getRole());

        session.close();
    }
}