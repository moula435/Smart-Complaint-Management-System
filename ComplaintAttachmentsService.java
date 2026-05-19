package com.smartcomplaint.Service;

import com.smartcomplaint.Entities.ComplaintAttachments;
import java.util.List;

public interface ComplaintAttachmentsService {

    // Create
    ComplaintAttachments addAttachment(ComplaintAttachments attachment);

    // Read
    ComplaintAttachments getAttachmentById(int attachmentId);
    List<ComplaintAttachments> getAttachmentsByComplaint(int complaintId);

    // Delete
    void deleteAttachment(int attachmentId);
}