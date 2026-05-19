package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.Admins;
import com.smartcomplaint.Entities.Complaints;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.ComplaintsService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class ComplaintsServiceImpl implements ComplaintsService {

    SessionFactory sf = new Configuration()
            .configure("Config.xml")
            .buildSessionFactory();

    @Override
    public Complaints submitComplaint(Complaints complaint) {
        if (complaint.getTitle() == null || complaint.getTitle().isEmpty())
            throw new InvalidInputException("Complaint title cannot be empty!");
        if (complaint.getDescription() == null || complaint.getDescription().isEmpty())
            throw new InvalidInputException("Complaint description cannot be empty!");
        if (complaint.getLocation() == null || complaint.getLocation().isEmpty())
            throw new InvalidInputException("Complaint location cannot be empty!");
        if (complaint.getUser() == null)
            throw new InvalidInputException("User must be provided!");
        if (complaint.getCategory() == null)
            throw new InvalidInputException("Category must be provided!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(complaint);
        tx.commit();
        session.close();
        return complaint;
    }

    @Override
    public Complaints getComplaintById(int complaintId) {
        Session session = sf.openSession();
        Complaints complaint = session.get(Complaints.class, complaintId);
        session.close();
        if (complaint == null)
            throw new ResourceNotFoundException("Complaint not found with ID: " + complaintId);
        return complaint;
    }

    @Override
    public List<Complaints> getAllComplaints() {
        Session session = sf.openSession();
        List<Complaints> complaints = session.createQuery(
                "FROM Complaints", Complaints.class).list();
        session.close();
        if (complaints.isEmpty())
            throw new ResourceNotFoundException("No complaints found!");
        return complaints;
    }

    @Override
    public List<Complaints> getComplaintsByUser(int userId) {
        Session session = sf.openSession();
        List<Complaints> complaints = session.createQuery(
                "FROM Complaints WHERE user.userId = :userId", Complaints.class)
                .setParameter("userId", userId)
                .list();
        session.close();
        if (complaints.isEmpty())
            throw new ResourceNotFoundException("No complaints found for User ID: " + userId);
        return complaints;
    }

    @Override
    public List<Complaints> getComplaintsByStatus(String status) {
        if (status == null || status.isEmpty())
            throw new InvalidInputException("Status cannot be empty!");
        Session session = sf.openSession();
        List<Complaints> complaints = session.createQuery(
                "FROM Complaints WHERE status = :status", Complaints.class)
                .setParameter("status", Complaints.Status.valueOf(status))
                .list();
        session.close();
        if (complaints.isEmpty())
            throw new ResourceNotFoundException("No complaints found with status: " + status);
        return complaints;
    }

    @Override
    public List<Complaints> getComplaintsByAdmin(int adminId) {
        Session session = sf.openSession();
        List<Complaints> complaints = session.createQuery(
                "FROM Complaints WHERE assignedAdmin.adminId = :adminId", Complaints.class)
                .setParameter("adminId", adminId)
                .list();
        session.close();
        if (complaints.isEmpty())
            throw new ResourceNotFoundException("No complaints found for Admin ID: " + adminId);
        return complaints;
    }

    // ✅ NEW - Update Complaint
    @Override
    public Complaints updateComplaint(Complaints complaint) {
        if (complaint.getTitle() == null || complaint.getTitle().isEmpty())
            throw new InvalidInputException("Complaint title cannot be empty!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(complaint);
        tx.commit();
        session.close();
        return complaint;
    }

    @Override
    public Complaints assignComplaintToAdmin(int complaintId, int adminId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Complaints complaint = session.get(Complaints.class, complaintId);
        if (complaint == null)
            throw new ResourceNotFoundException("Complaint not found with ID: " + complaintId);
        Admins admin = session.get(Admins.class, adminId);
        if (admin == null)
            throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
        complaint.setAssignedAdmin(admin);
        complaint.setStatus(Complaints.Status.in_progress);
        session.merge(complaint);
        tx.commit();
        session.close();
        return complaint;
    }

    @Override
    public Complaints updateComplaintStatus(int complaintId, String status) {
        if (status == null || status.isEmpty())
            throw new InvalidInputException("Status cannot be empty!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Complaints complaint = session.get(Complaints.class, complaintId);
        if (complaint == null)
            throw new ResourceNotFoundException("Complaint not found with ID: " + complaintId);
        complaint.setStatus(Complaints.Status.valueOf(status));
        session.merge(complaint);
        tx.commit();
        session.close();
        return complaint;
    }

    @Override
    public void deleteComplaint(int complaintId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Complaints complaint = session.get(Complaints.class, complaintId);
        if (complaint == null)
            throw new ResourceNotFoundException("Complaint not found with ID: " + complaintId);
        session.remove(complaint);
        tx.commit();
        session.close();
    }
}