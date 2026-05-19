package com.smartcomplaint.BusinessLogic;

import com.smartcomplaint.Entities.Admins;
import com.smartcomplaint.Entities.Complaints;
import com.smartcomplaint.Entities.Users;

public class ComplaintBusinessLogic {

    // ✅ CHECK IF USER IS ACTIVE
    public static boolean isUserActive(Users user) {
        return user.getStatus() == Users.Status.active;
    }

    // ✅ CHECK IF COMPLAINT IS PENDING
    public static boolean isComplaintPending(Complaints complaint) {
        return complaint.getStatus() == Complaints.Status.pending;
    }

    // ✅ CHECK IF COMPLAINT IS RESOLVED
    public static boolean isComplaintResolved(Complaints complaint) {
        return complaint.getStatus() == Complaints.Status.resolved;
    }

    // ✅ CHECK IF COMPLAINT CAN BE ASSIGNED
    public static boolean canAssignComplaint(Complaints complaint) {
        return complaint.getStatus() == Complaints.Status.pending
            || complaint.getStatus() == Complaints.Status.in_progress;
    }

    // ✅ ASSIGN COMPLAINT TO ADMIN
    public static void assignComplaint(Complaints complaint, Admins admin) {
        complaint.setAssignedAdmin(admin);
        complaint.setStatus(Complaints.Status.in_progress);

        System.out.println("\n===== COMPLAINT ASSIGNED =====");
        System.out.println("Complaint   : " + complaint.getTitle());
        System.out.println("Assigned To : " + admin.getFullName());
        System.out.println("Status      : " + complaint.getStatus());
        System.out.println("==============================");
    }

    // ✅ UPDATE COMPLAINT STATUS
    public static void updateStatus(Complaints complaint,
                                    Complaints.Status newStatus) {
        Complaints.Status oldStatus = complaint.getStatus();
        complaint.setStatus(newStatus);

        System.out.println("\n===== STATUS UPDATED =====");
        System.out.println("Complaint  : " + complaint.getTitle());
        System.out.println("Old Status : " + oldStatus);
        System.out.println("New Status : " + complaint.getStatus());
        System.out.println("==========================");
    }

    // ✅ DISPLAY COMPLAINT SUMMARY
    public static void displayComplaintSummary(int total,
                                               int resolved,
                                               int pending) {
        int inProgress = total - resolved - pending;

        System.out.println("\n===== COMPLAINT SUMMARY =====");
        System.out.println("Total Complaints : " + total);
        System.out.println("Pending          : " + pending);
        System.out.println("In Progress      : " + inProgress);
        System.out.println("Resolved         : " + resolved);
        System.out.println("=============================");
    }

    // ✅ VALIDATE COMPLAINT FIELDS
    public static boolean isValidComplaint(String title,
                                           String description,
                                           String location) {
        return title != null && !title.isEmpty()
            && description != null && !description.isEmpty()
            && location != null && !location.isEmpty();
    }

    // ✅ CHECK PRIORITY
    public static boolean isHighPriority(Complaints complaint) {
        return complaint.getPriority() == Complaints.Priority.high;
    }
}