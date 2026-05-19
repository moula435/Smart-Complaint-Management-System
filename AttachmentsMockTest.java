package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.ComplaintAttachments;
import com.smartcomplaint.Entities.Complaints;

public class AttachmentsMockTest {

    @Test
    public void attachmentMockTest() {
        ComplaintAttachments attachment = mock(ComplaintAttachments.class);
        Complaints complaint            = mock(Complaints.class);

        when(complaint.getTitle()).thenReturn("Pothole on Highway");

        when(attachment.getFileName()).thenReturn("pothole_photo.jpg");
        when(attachment.getFilePath()).thenReturn("/uploads/complaints/pothole_photo.jpg");
        when(attachment.getFileType()).thenReturn(ComplaintAttachments.FileType.image);
        when(attachment.getFileFormat()).thenReturn("jpg");
        when(attachment.getFileSizeKb()).thenReturn(300);
        when(attachment.getComplaint()).thenReturn(complaint);

        assertEquals("pothole_photo.jpg",                      attachment.getFileName());
        assertEquals("/uploads/complaints/pothole_photo.jpg",  attachment.getFilePath());
        assertEquals(ComplaintAttachments.FileType.image,      attachment.getFileType());
        assertEquals("jpg",                                    attachment.getFileFormat());
        assertEquals(300,                                      attachment.getFileSizeKb());
        assertEquals("Pothole on Highway",                     attachment.getComplaint().getTitle());

        System.out.println("✅ Attachments Mock Test Passed!");
        System.out.println("   File Name  : " + attachment.getFileName());
        System.out.println("   File Path  : " + attachment.getFilePath());
        System.out.println("   File Type  : " + attachment.getFileType());
        System.out.println("   File Format: " + attachment.getFileFormat());
        System.out.println("   File Size  : " + attachment.getFileSizeKb() + " KB");
        System.out.println("   Complaint  : " + attachment.getComplaint().getTitle());
    }
}