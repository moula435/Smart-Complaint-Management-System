package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.StatusHistory;
import com.smartcomplaint.Entities.Complaints;
import com.smartcomplaint.Entities.Admins;

public class StatusHistoryMockTest {

    @Test
    public void statusHistoryMockTest() {
        StatusHistory history = mock(StatusHistory.class);
        Complaints complaint  = mock(Complaints.class);
        Admins admin          = mock(Admins.class);

        when(complaint.getTitle()).thenReturn("Power Cut Since 3 Days");
        when(admin.getFullName()).thenReturn("Super Admin");

        when(history.getOldStatus()).thenReturn(Complaints.Status.pending);
        when(history.getNewStatus()).thenReturn(Complaints.Status.in_progress);
        when(history.getRemarks()).thenReturn("Team assigned to fix the issue.");
        when(history.getComplaint()).thenReturn(complaint);
        when(history.getChangedByAdmin()).thenReturn(admin);

        assertEquals(Complaints.Status.pending,     history.getOldStatus());
        assertEquals(Complaints.Status.in_progress, history.getNewStatus());
        assertEquals("Team assigned to fix the issue.", history.getRemarks());
        assertEquals("Power Cut Since 3 Days",      history.getComplaint().getTitle());
        assertEquals("Super Admin",                 history.getChangedByAdmin().getFullName());

        System.out.println("✅ Status History Mock Test Passed!");
        System.out.println("   Old Status : " + history.getOldStatus());
        System.out.println("   New Status : " + history.getNewStatus());
        System.out.println("   Remarks    : " + history.getRemarks());
        System.out.println("   Complaint  : " + history.getComplaint().getTitle());
        System.out.println("   Admin      : " + history.getChangedByAdmin().getFullName());
    }
}
