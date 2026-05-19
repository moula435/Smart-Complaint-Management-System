package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.Admins;
import java.util.List;

public interface AdminService {

    // Create
    Admins registerAdmin(Admins admin);

    // Read
    Admins getAdminById(int adminId);
    Admins getAdminByEmail(String email);
    List<Admins> getAllAdmins();

    // Update
    Admins updateAdmin(Admins admin);

    // Delete
    void deleteAdmin(int adminId);

    // Login
    Admins loginAdmin(String email, String password);
}