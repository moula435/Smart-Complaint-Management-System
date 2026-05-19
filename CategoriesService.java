package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.Categories;
import java.util.List;

public interface CategoriesService {

    // Create
    Categories addCategory(Categories category);

    // Read
    Categories getCategoryById(int categoryId);
    List<Categories> getAllCategories();
    List<Categories> getActiveCategories();

    // Update
    Categories updateCategory(Categories category);

    // Delete
    void deleteCategory(int categoryId);
}