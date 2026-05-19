package com.smartcomplaint.ServiceImpl;

import com.smartcomplaint.Entities.ComplaintAttachments;
import com.smartcomplaint.Exceptions.InvalidInputException;
import com.smartcomplaint.Exceptions.ResourceNotFoundException;
import com.smartcomplaint.Service.ComplaintAttachmentsService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class ComplaintAttachmentsServiceImpl implements ComplaintAttachmentsService {

    SessionFactory sf = new Configuration()
            .configure("config.xml")
            .buildSessionFactory();

    @Override
    public ComplaintAttachments addAttachment(ComplaintAttachments attachment) {
        if (attachment.getFileName() == null || attachment.getFileName().isEmpty())
            throw new InvalidInputException("File name cannot be empty!");
        if (attachment.getFilePath() == null || attachment.getFilePath().isEmpty())
            throw new InvalidInputException("File path cannot be empty!");
        if (attachment.getComplaint() == null)
            throw new InvalidInputException("Complaint must be provided!");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(attachment);
        tx.commit();
        session.close();
        return attachment;
    }

    @Override
    public ComplaintAttachments getAttachmentById(int attachmentId) {
        Session session = sf.openSession();
        ComplaintAttachments attachment = session.get(ComplaintAttachments.class, attachmentId);
        session.close();
        if (attachment == null)
            throw new ResourceNotFoundException("Attachment not found with ID: " + attachmentId);
        return attachment;
    }

    @Override
    public List<ComplaintAttachments> getAttachmentsByComplaint(int complaintId) {
        Session session = sf.openSession();
        List<ComplaintAttachments> attachments = session.createQuery(
                "FROM ComplaintAttachments WHERE complaint.complaintId = :complaintId",
                ComplaintAttachments.class)
                .setParameter("complaintId", complaintId)
                .list();
        session.close();
        if (attachments.isEmpty())
            throw new ResourceNotFoundException("No attachments found for Complaint ID: " + complaintId);
        return attachments;
    }

    @Override
    public void deleteAttachment(int attachmentId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        ComplaintAttachments attachment = session.get(ComplaintAttachments.class, attachmentId);
        if (attachment == null)
            throw new ResourceNotFoundException("Attachment not found with ID: " + attachmentId);
        session.remove(attachment);
        tx.commit();
        session.close();
    }
}