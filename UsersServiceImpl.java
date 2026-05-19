package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.Users;
import com.smartcomplaint.Exceptions.DuplicateEntryException;
import com.smartcomplaint.Exceptions.InvalidCredentialsException;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.UsersService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class UsersServiceImpl implements UsersService {

    SessionFactory sf = new Configuration()
            .configure("config.xml")
            .buildSessionFactory();

    @Override
    public Users registerUser(Users user) {
        if (user.getFullName() == null || user.getFullName().isEmpty())
            throw new InvalidInputException("Full name cannot be empty!");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty())
            throw new InvalidInputException("Password cannot be empty!");

        Users existing = getUserByEmail(user.getEmail());
        if (existing != null)
            throw new DuplicateEntryException("Email already exists: " + user.getEmail());

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
        return user;
    }

    @Override
    public Users getUserById(int userId) {
        Session session = sf.openSession();
        Users user = session.get(Users.class, userId);
        session.close();
        if (user == null)
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        return user;
    }

    @Override
    public Users getUserByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        Session session = sf.openSession();
        Users user = session.createQuery(
                "FROM Users WHERE email = :email", Users.class)
                .setParameter("email", email)
                .uniqueResult();
        session.close();
        return user;
    }

    @Override
    public List<Users> getAllUsers() {
        Session session = sf.openSession();
        List<Users> users = session.createQuery(
                "FROM Users", Users.class).list();
        session.close();
        if (users.isEmpty())
            throw new ResourceNotFoundException("No users found!");
        return users;
    }

    @Override
    public Users updateUser(Users user) {
        if (user.getFullName() == null || user.getFullName().isEmpty())
            throw new InvalidInputException("Full name cannot be empty!");
        getUserById(user.getUserId());
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(user);
        tx.commit();
        session.close();
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Users user = session.get(Users.class, userId);
        if (user == null)
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        session.remove(user);
        tx.commit();
        session.close();
    }

    @Override
    public Users loginUser(String email, String password) {
        if (email == null || email.isEmpty())
            throw new InvalidInputException("Email cannot be empty!");
        if (password == null || password.isEmpty())
            throw new InvalidInputException("Password cannot be empty!");
        Session session = sf.openSession();
        Users user = session.createQuery(
                "FROM Users WHERE email = :email AND passwordHash = :password", Users.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        if (user == null)
            throw new InvalidCredentialsException("Invalid email or password!");
        return user;
    }
}