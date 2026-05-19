package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.StatusHistory;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.StatusHistoryService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class StatusHistoryServiceImpl implements StatusHistoryService {

    SessionFactory sf = new Configuration()
            .configure("config.xml")
            .buildSessionFactory();

    @Override
    public StatusHistory addStatusHistory(StatusHistory statusHistory) {
        if (statusHistory.getComplaint() == null)
            throw new InvalidInputException("Complaint must be provided!");
        if (statusHistory.getNewStatus() == null)
            throw new InvalidInputException("New status must be provided!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(statusHistory);
        tx.commit();
        session.close();
        return statusHistory;
    }

    @Override
    public StatusHistory getStatusHistoryById(int historyId) {
        Session session = sf.openSession();
        StatusHistory history = session.get(StatusHistory.class, historyId);
        session.close();
        if (history == null)
            throw new ResourceNotFoundException("Status history not found with ID: " + historyId);
        return history;
    }

    @Override
    public List<StatusHistory> getHistoryByComplaint(int complaintId) {
        Session session = sf.openSession();
        List<StatusHistory> historyList = session.createQuery(
                "FROM StatusHistory WHERE complaint.complaintId = :complaintId",
                StatusHistory.class)
                .setParameter("complaintId", complaintId)
                .list();
        session.close();
        if (historyList.isEmpty())
            throw new ResourceNotFoundException("No history found for Complaint ID: " + complaintId);
        return historyList;
    }

    @Override
    public List<StatusHistory> getHistoryByAdmin(int adminId) {
        Session session = sf.openSession();
        List<StatusHistory> historyList = session.createQuery(
                "FROM StatusHistory WHERE changedByAdmin.adminId = :adminId",
                StatusHistory.class)
                .setParameter("adminId", adminId)
                .list();
        session.close();
        if (historyList.isEmpty())
            throw new ResourceNotFoundException("No history found for Admin ID: " + adminId);
        return historyList;
    }
}