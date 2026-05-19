package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.Admins;
import com.smartcomplaint.Exceptions.DuplicateEntryException;
import com.smartcomplaint.Exceptions.InvalidCredentialsException;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.AdminService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    SessionFactory sf = new Configuration()
            .configure("config.xml")
            .buildSessionFactory();

    @Override
    public Admins registerAdmin(Admins admin) {
        if (admin.getFullName() == null || admin.getFullName().isEmpty())
            throw new InvalidInputException("Full name cannot be empty!");
        if (admin.getEmail() == null || admin.getEmail().isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        if (admin.getPasswordHash() == null || admin.getPasswordHash().isEmpty())
            throw new InvalidInputException("Password cannot be empty!");

        Admins existing = getAdminByEmail(admin.getEmail());
        if (existing != null)
            throw new DuplicateEntryException("Email already exists: " + admin.getEmail());

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(admin);
        tx.commit();
        session.close();
        return admin;
    }

    @Override
    public Admins getAdminById(int adminId) {
        Session session = sf.openSession();
        Admins admin = session.get(Admins.class, adminId);
        session.close();
        if (admin == null)
            throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
        return admin;
    }

    @Override
    public Admins getAdminByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        Session session = sf.openSession();
        Admins admin = session.createQuery(
                "FROM Admins WHERE email = :email", Admins.class)
                .setParameter("email", email)
                .uniqueResult();
        session.close();
        return admin;
    }

    @Override
    public List<Admins> getAllAdmins() {
        Session session = sf.openSession();
        List<Admins> admins = session.createQuery(
                "FROM Admins", Admins.class).list();
        session.close();
        if (admins.isEmpty())
            throw new ResourceNotFoundException("No admins found!");
        return admins;
    }

    @Override
    public Admins updateAdmin(Admins admin) {
        if (admin.getFullName() == null || admin.getFullName().isEmpty())
            throw new InvalidInputException("Full name cannot be empty!");
        getAdminById(admin.getAdminId());
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(admin);
        tx.commit();
        session.close();
        return admin;
    }

    @Override
    public void deleteAdmin(int adminId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Admins admin = session.get(Admins.class, adminId);
        if (admin == null)
            throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
        session.remove(admin);
        tx.commit();
        session.close();
    }

    @Override
    public Admins loginAdmin(String email, String password) {
        if (email == null || email.isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        if (password == null || password.isEmpty())
            throw new InvalidInputException("Password cannot be empty!");
        Session session = sf.openSession();
        Admins admin = session.createQuery(
                "FROM Admins WHERE email = :email AND passwordHash = :password", Admins.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        if (admin == null)
            throw new InvalidCredentialsException("Invalid email or password!");
        return admin;
    }
}