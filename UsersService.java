package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.Users;
import java.util.List;

public interface UsersService {

    // Create
    Users registerUser(Users user);

    // Read
    Users getUserById(int userId);
    Users getUserByEmail(String email);
    List<Users> getAllUsers();

    // Update
    Users updateUser(Users user);

    // Delete
    void deleteUser(int userId);

    // Login
    Users loginUser(String email, String password);
}