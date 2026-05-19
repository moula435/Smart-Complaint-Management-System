package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.Categories;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.CategoriesService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {

    SessionFactory sf = new Configuration()
            .configure("config.xml")
            .buildSessionFactory();

    @Override
    public Categories addCategory(Categories category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty())
            throw new InvalidInputException("Category name cannot be empty!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(category);
        tx.commit();
        session.close();
        return category;
    }

    @Override
    public Categories getCategoryById(int categoryId) {
        Session session = sf.openSession();
        Categories category = session.get(Categories.class, categoryId);
        session.close();
        if (category == null)
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        return category;
    }

    @Override
    public List<Categories> getAllCategories() {
        Session session = sf.openSession();
        List<Categories> categories = session.createQuery(
                "FROM Categories", Categories.class).list();
        session.close();
        if (categories.isEmpty())
            throw new ResourceNotFoundException("No categories found!");
        return categories;
    }

    @Override
    public List<Categories> getActiveCategories() {
        Session session = sf.openSession();
        List<Categories> categories = session.createQuery(
                "FROM Categories WHERE isActive = true", Categories.class).list();
        session.close();
        if (categories.isEmpty())
            throw new ResourceNotFoundException("No active categories found!");
        return categories;
    }

    @Override
    public Categories updateCategory(Categories category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty())
            throw new InvalidInputException("Category name cannot be empty!");
        getCategoryById(category.getCategoryId());
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(category);
        tx.commit();
        session.close();
        return category;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Categories category = session.get(Categories.class, categoryId);
        if (category == null)
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        session.remove(category);
        tx.commit();
        session.close();
    }
}