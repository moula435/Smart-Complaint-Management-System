package com.smartcomplaint;

import com.smartcomplaint.BusinessLogic.ComplaintBusinessLogic;
import com.smartcomplaint.Entities.*;
import com.smartcomplaint.Exceptions.*;
import com.smartcomplaint.ServiceImpl.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static UsersServiceImpl usersService = new UsersServiceImpl();
    static AdminServiceImpl adminService = new AdminServiceImpl();
    static CategoriesServiceImpl categoriesService = new CategoriesServiceImpl();
    static ComplaintsServiceImpl complaintsService = new ComplaintsServiceImpl();
    static ComplaintAttachmentsServiceImpl attachmentsService = new ComplaintAttachmentsServiceImpl();
    static StatusHistoryServiceImpl statusHistoryService = new StatusHistoryServiceImpl();

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  Smart Complaint Management System");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n============ MAIN MENU ============");
            System.out.println("1. User Menu");
            System.out.println("2. Admin Menu");
            System.out.println("3. Category Menu");
            System.out.println("4. Complaint Menu");
            System.out.println("5. Attachment Menu");
            System.out.println("6. Status History Menu");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 : userMenu();
                break;
                case 2 : adminMenu();
                break;
                case 3 : categoryMenu();
                break;
                case 4 : complaintMenu();
                break;
                case 5 : attachmentMenu();
                break;
                case 6 : statusHistoryMenu();
                break;
                case 0 : {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                break;
                default : System.out.println("Invalid choice! Try again.");
            }
        }
        sc.close();
    }

    // =============================================
    // USER MENU
    // =============================================
    static void userMenu() {
        System.out.println("\n--- USER MENU ---");
        System.out.println("1. Register User");
        System.out.println("2. Get User By ID");
        System.out.println("3. Get All Users");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. User Login");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    System.out.println("\n-- Register User --");
                    Users user = new Users();
                    System.out.print("Full Name: ");
                    user.setFullName(sc.nextLine());
                    System.out.print("Email: ");
                    user.setEmail(sc.nextLine());
                    System.out.print("Password: ");
                    user.setPasswordHash(sc.nextLine());
                    System.out.print("Phone: ");
                    user.setPhone(sc.nextLine());
                    System.out.print("Address: ");
                    user.setAddress(sc.nextLine());
                    user.setStatus(Users.Status.active);
                    Users saved = usersService.registerUser(user);
                    System.out.println("User Registered Successfully! ID: " + saved.getUserId());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (DuplicateEntryException e) {
                    System.out.println("Duplicate Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Users user = usersService.getUserById(id);
                    System.out.println("Name: " + user.getFullName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Phone: " + user.getPhone());
                    System.out.println("Address: " + user.getAddress());
                    System.out.println("Status: " + user.getStatus());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    List<Users> users = usersService.getAllUsers();
                    users.forEach(u -> System.out.println(
                            "ID: " + u.getUserId()
                            + " | Name: " + u.getFullName()
                            + " | Email: " + u.getEmail()
                            + " | Status: " + u.getStatus()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 4 : {
                try {
                    System.out.print("Enter User ID to Update: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Users user = usersService.getUserById(id);
                    System.out.print("New Full Name (" + user.getFullName() + "): ");
                    user.setFullName(sc.nextLine());
                    System.out.print("New Phone (" + user.getPhone() + "): ");
                    user.setPhone(sc.nextLine());
                    System.out.print("New Address (" + user.getAddress() + "): ");
                    user.setAddress(sc.nextLine());
                    usersService.updateUser(user);
                    System.out.println("User Updated Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 5 : {
                try {
                    System.out.print("Enter User ID to Delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    usersService.deleteUser(id);
                    System.out.println("User Deleted Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 6 : {
                try {
                    System.out.println("\n-- User Login --");
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    Users user = usersService.loginUser(email, password);

                    // ✅ Business Logic - Check if user is active
                    if (ComplaintBusinessLogic.isUserActive(user)) {
                        System.out.println("Login Successful! Welcome "
                                + user.getFullName());
                    } else {
                        System.out.println("Your account is not active!");
                    }
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (InvalidCredentialsException e) {
                    System.out.println("Login Failed: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }

    // =============================================
    // ADMIN MENU
    // =============================================
    static void adminMenu() {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. Register Admin");
        System.out.println("2. Get Admin By ID");
        System.out.println("3. Get All Admins");
        System.out.println("4. Update Admin");
        System.out.println("5. Delete Admin");
        System.out.println("6. Admin Login");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    System.out.println("\n-- Register Admin --");
                    Admins admin = new Admins();
                    System.out.print("Full Name: ");
                    admin.setFullName(sc.nextLine());
                    System.out.print("Email: ");
                    admin.setEmail(sc.nextLine());
                    System.out.print("Password: ");
                    admin.setPasswordHash(sc.nextLine());
                    System.out.print("Phone: ");
                    admin.setPhone(sc.nextLine());
                    System.out.println("Role (1. superadmin  2. admin): ");
                    int role = sc.nextInt();
                    sc.nextLine();
                    admin.setRole(role == 1 ? Admins.Role.superadmin : Admins.Role.admin);
                    Admins saved = adminService.registerAdmin(admin);
                    System.out.println("Admin Registered Successfully! ID: " + saved.getAdminId());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (DuplicateEntryException e) {
                    System.out.println("Duplicate Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter Admin ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Admins admin = adminService.getAdminById(id);
                    System.out.println("Name: " + admin.getFullName());
                    System.out.println("Email: " + admin.getEmail());
                    System.out.println("Phone: " + admin.getPhone());
                    System.out.println("Role: " + admin.getRole());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    List<Admins> admins = adminService.getAllAdmins();
                    admins.forEach(a -> System.out.println(
                            "ID: " + a.getAdminId()
                            + " | Name: " + a.getFullName()
                            + " | Role: " + a.getRole()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 4 : {
                try {
                    System.out.print("Enter Admin ID to Update: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Admins admin = adminService.getAdminById(id);
                    System.out.print("New Full Name (" + admin.getFullName() + "): ");
                    admin.setFullName(sc.nextLine());
                    System.out.print("New Phone (" + admin.getPhone() + "): ");
                    admin.setPhone(sc.nextLine());
                    adminService.updateAdmin(admin);
                    System.out.println("Admin Updated Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 5 : {
                try {
                    System.out.print("Enter Admin ID to Delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    adminService.deleteAdmin(id);
                    System.out.println("Admin Deleted Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 6 : {
                try {
                    System.out.println("\n-- Admin Login --");
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    Admins admin = adminService.loginAdmin(email, password);
                    System.out.println("Login Successful! Welcome " + admin.getFullName()
                            + " | Role: " + admin.getRole());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (InvalidCredentialsException e) {
                    System.out.println("Login Failed: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }

    // =============================================
    // CATEGORY MENU
    // =============================================
    static void categoryMenu() {
        System.out.println("\n--- CATEGORY MENU ---");
        System.out.println("1. Add Category");
        System.out.println("2. Get Category By ID");
        System.out.println("3. Get All Categories");
        System.out.println("4. Update Category");
        System.out.println("5. Delete Category");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    Categories category = new Categories();
                    System.out.print("Category Name: ");
                    category.setCategoryName(sc.nextLine());
                    System.out.print("Description: ");
                    category.setDescription(sc.nextLine());
                    category.setActive(true);
                    Categories saved = categoriesService.addCategory(category);
                    System.out.println("Category Added! ID: " + saved.getCategoryId());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter Category ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Categories category = categoriesService.getCategoryById(id);
                    System.out.println("Name: " + category.getCategoryName());
                    System.out.println("Description: " + category.getDescription());
                    System.out.println("Active: " + category.isActive());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    categoriesService.getAllCategories().forEach(c ->
                            System.out.println("ID: " + c.getCategoryId()
                                    + " | Name: " + c.getCategoryName()
                                    + " | Active: " + c.isActive()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 4 : {
                try {
                    System.out.print("Enter Category ID to Update: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Categories category = categoriesService.getCategoryById(id);
                    System.out.print("New Name (" + category.getCategoryName() + "): ");
                    category.setCategoryName(sc.nextLine());
                    System.out.print("New Description: ");
                    category.setDescription(sc.nextLine());
                    categoriesService.updateCategory(category);
                    System.out.println("Category Updated Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 5 : {
                try {
                    System.out.print("Enter Category ID to Delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    categoriesService.deleteCategory(id);
                    System.out.println("Category Deleted Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }

    // =============================================
    // COMPLAINT MENU
    // =============================================
    static void complaintMenu() {
        System.out.println("\n--- COMPLAINT MENU ---");
        System.out.println("1. Submit Complaint");
        System.out.println("2. Get Complaint By ID");
        System.out.println("3. Get All Complaints");
        System.out.println("4. Get Complaints By User");
        System.out.println("5. Get Complaints By Status");
        System.out.println("6. Assign Complaint to Admin");
        System.out.println("7. Update Complaint Status");
        System.out.println("8. Delete Complaint");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    System.out.println("\n-- Submit Complaint --");
                    Complaints complaint = new Complaints();
                    System.out.print("User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();
                    Users user = usersService.getUserById(userId);
                    complaint.setUser(user);
                    System.out.print("Category ID: ");
                    int catId = sc.nextInt();
                    sc.nextLine();
                    complaint.setCategory(categoriesService.getCategoryById(catId));
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String description = sc.nextLine();
                    System.out.print("Location: ");
                    String location = sc.nextLine();
                    System.out.println("Priority (1. low  2. medium  3. high): ");
                    int priority = sc.nextInt();
                    sc.nextLine();
                    complaint.setTitle(title);
                    complaint.setDescription(description);
                    complaint.setLocation(location);
                    complaint.setPriority(priority == 1 ? Complaints.Priority.low
                            : priority == 3 ? Complaints.Priority.high
                            : Complaints.Priority.medium);
                    complaint.setStatus(Complaints.Status.pending);

                    // ✅ Business Logic - Validate fields
                    if (ComplaintBusinessLogic.isValidComplaint(title, description, location)) {
                        Complaints saved = complaintsService.submitComplaint(complaint);
                        System.out.println("Complaint Submitted! ID: " + saved.getComplaintId()
                                + " | Status: " + saved.getStatus());

                        // ✅ Business Logic - Check high priority
                        if (ComplaintBusinessLogic.isHighPriority(saved)) {
                            System.out.println("⚠️ HIGH PRIORITY complaint submitted!");
                        }
                    } else {
                        System.out.println("Invalid complaint fields!");
                    }
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter Complaint ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Complaints c = complaintsService.getComplaintById(id);
                    System.out.println("Title: " + c.getTitle());
                    System.out.println("Description: " + c.getDescription());
                    System.out.println("Location: " + c.getLocation());
                    System.out.println("Status: " + c.getStatus());
                    System.out.println("Priority: " + c.getPriority());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    List<Complaints> all = complaintsService.getAllComplaints();

                    // ✅ Business Logic - Display Summary
                    long resolved = all.stream().filter(ComplaintBusinessLogic::isComplaintResolved).count();
                    long pending  = all.stream().filter(ComplaintBusinessLogic::isComplaintPending).count();
                    ComplaintBusinessLogic.displayComplaintSummary(all.size(), (int) resolved, (int) pending);

                    all.forEach(c ->
                            System.out.println("ID: " + c.getComplaintId()
                                    + " | Title: " + c.getTitle()
                                    + " | Status: " + c.getStatus()
                                    + " | Priority: " + c.getPriority()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 4 : {
                try {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();
                    complaintsService.getComplaintsByUser(userId).forEach(c ->
                            System.out.println("ID: " + c.getComplaintId()
                                    + " | Title: " + c.getTitle()
                                    + " | Status: " + c.getStatus()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 5 : {
                try {
                    System.out.println("Status (pending / in_progress / resolved / rejected): ");
                    String status = sc.nextLine();
                    complaintsService.getComplaintsByStatus(status).forEach(c ->
                            System.out.println("ID: " + c.getComplaintId()
                                    + " | Title: " + c.getTitle()
                                    + " | Status: " + c.getStatus()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 6 : {
                try {
                    System.out.print("Enter Complaint ID: ");
                    int complaintId = sc.nextInt();
                    System.out.print("Enter Admin ID: ");
                    int adminId = sc.nextInt();
                    sc.nextLine();

                    Complaints c = complaintsService.getComplaintById(complaintId);
                    Admins a = adminService.getAdminById(adminId);

                    // ✅ Business Logic - Check if complaint can be assigned
                    if (ComplaintBusinessLogic.canAssignComplaint(c)) {
                        ComplaintBusinessLogic.assignComplaint(c, a);
                        complaintsService.updateComplaint(c);
                    } else {
                        System.out.println("Cannot assign! Complaint is already resolved or rejected.");
                    }
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 7 : {
                try {
                    System.out.print("Enter Complaint ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("New Status (pending / in_progress / resolved / rejected): ");
                    String status = sc.nextLine();

                    Complaints c = complaintsService.getComplaintById(id);

                    // ✅ Business Logic - Check if complaint is not resolved
                    if (!ComplaintBusinessLogic.isComplaintResolved(c)) {
                        ComplaintBusinessLogic.updateStatus(c, Complaints.Status.valueOf(status));
                        complaintsService.updateComplaintStatus(id, status);
                    } else {
                        System.out.println("Cannot update! Complaint is already resolved.");
                    }
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 8 : {
                try {
                    System.out.print("Enter Complaint ID to Delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    complaintsService.deleteComplaint(id);
                    System.out.println("Complaint Deleted Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }

    // =============================================
    // ATTACHMENT MENU
    // =============================================
    static void attachmentMenu() {
        System.out.println("\n--- ATTACHMENT MENU ---");
        System.out.println("1. Add Attachment");
        System.out.println("2. Get Attachment By ID");
        System.out.println("3. Get Attachments By Complaint");
        System.out.println("4. Delete Attachment");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    ComplaintAttachments attachment = new ComplaintAttachments();
                    System.out.print("Complaint ID: ");
                    int complaintId = sc.nextInt();
                    sc.nextLine();
                    attachment.setComplaint(complaintsService.getComplaintById(complaintId));
                    System.out.print("File Name: ");
                    attachment.setFileName(sc.nextLine());
                    System.out.print("File Path: ");
                    attachment.setFilePath(sc.nextLine());
                    System.out.println("File Type (1. image  2. video): ");
                    int type = sc.nextInt();
                    sc.nextLine();
                    attachment.setFileType(type == 1
                            ? ComplaintAttachments.FileType.image
                            : ComplaintAttachments.FileType.video);
                    System.out.print("File Format (jpg/png/mp4): ");
                    attachment.setFileFormat(sc.nextLine());
                    System.out.print("File Size (KB): ");
                    attachment.setFileSizeKb(sc.nextInt());
                    sc.nextLine();
                    ComplaintAttachments saved = attachmentsService.addAttachment(attachment);
                    System.out.println("Attachment Added! ID: " + saved.getAttachmentId());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter Attachment ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    ComplaintAttachments a = attachmentsService.getAttachmentById(id);
                    System.out.println("File Name: " + a.getFileName());
                    System.out.println("File Path: " + a.getFilePath());
                    System.out.println("File Type: " + a.getFileType());
                    System.out.println("File Format: " + a.getFileFormat());
                    System.out.println("File Size: " + a.getFileSizeKb() + " KB");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    System.out.print("Enter Complaint ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    attachmentsService.getAttachmentsByComplaint(id).forEach(a ->
                            System.out.println("ID: " + a.getAttachmentId()
                                    + " | File: " + a.getFileName()
                                    + " | Type: " + a.getFileType()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 4 : {
                try {
                    System.out.print("Enter Attachment ID to Delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    attachmentsService.deleteAttachment(id);
                    System.out.println("Attachment Deleted Successfully!");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }

    // =============================================
    // STATUS HISTORY MENU
    // =============================================
    static void statusHistoryMenu() {
        System.out.println("\n--- STATUS HISTORY MENU ---");
        System.out.println("1. Add Status History");
        System.out.println("2. Get History By Complaint");
        System.out.println("3. Get History By Admin");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 : {
                try {
                    StatusHistory history = new StatusHistory();
                    System.out.print("Complaint ID: ");
                    int complaintId = sc.nextInt();
                    sc.nextLine();
                    history.setComplaint(complaintsService.getComplaintById(complaintId));
                    System.out.print("Admin ID: ");
                    int adminId = sc.nextInt();
                    sc.nextLine();
                    history.setChangedByAdmin(adminService.getAdminById(adminId));
                    System.out.println("Old Status (pending/in_progress/resolved/rejected): ");
                    history.setOldStatus(Complaints.Status.valueOf(sc.nextLine()));
                    System.out.println("New Status (pending/in_progress/resolved/rejected): ");
                    history.setNewStatus(Complaints.Status.valueOf(sc.nextLine()));
                    System.out.print("Remarks: ");
                    history.setRemarks(sc.nextLine());
                    StatusHistory saved = statusHistoryService.addStatusHistory(history);
                    System.out.println("Status History Added! ID: " + saved.getHistoryId());
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (InvalidInputException e) {
                    System.out.println("Input Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 2 : {
                try {
                    System.out.print("Enter Complaint ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    statusHistoryService.getHistoryByComplaint(id).forEach(h ->
                            System.out.println("ID: " + h.getHistoryId()
                                    + " | " + h.getOldStatus()
                                    + " -> " + h.getNewStatus()
                                    + " | Remarks: " + h.getRemarks()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            case 3 : {
                try {
                    System.out.print("Enter Admin ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    statusHistoryService.getHistoryByAdmin(id).forEach(h ->
                            System.out.println("ID: " + h.getHistoryId()
                                    + " | Complaint ID: " + h.getComplaint().getComplaintId()
                                    + " | " + h.getOldStatus()
                                    + " -> " + h.getNewStatus()));
                } catch (ResourceNotFoundException e) {
                    System.out.println("Not Found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
            break;
            default : System.out.println("Invalid choice!");
        }
    }
}