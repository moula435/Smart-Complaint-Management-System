package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Complaints;
import com.smartcomplaint.Entities.Users;
import com.smartcomplaint.Entities.Categories;

public class ComplaintsMockTest {

    @Test
    public void complaintMockTest() {
        Complaints complaint = mock(Complaints.class);
        Users user           = mock(Users.class);
        Categories category  = mock(Categories.class);

        when(user.getFullName()).thenReturn("Ravi Kumar");
        when(category.getCategoryName()).thenReturn("Water Supply");

        when(complaint.getTitle()).thenReturn("Water Leakage Near Main Road");
        when(complaint.getDescription()).thenReturn("Water pipe leakage near main road.");
        when(complaint.getLocation()).thenReturn("Main Road, Hyderabad");
        when(complaint.getStatus()).thenReturn(Complaints.Status.pending);
        when(complaint.getPriority()).thenReturn(Complaints.Priority.high);
        when(complaint.getUser()).thenReturn(user);
        when(complaint.getCategory()).thenReturn(category);

        assertEquals("Water Leakage Near Main Road", complaint.getTitle());
        assertEquals("Main Road, Hyderabad",         complaint.getLocation());
        assertEquals(Complaints.Status.pending,      complaint.getStatus());
        assertEquals(Complaints.Priority.high,       complaint.getPriority());
        assertEquals("Ravi Kumar",                   complaint.getUser().getFullName());
        assertEquals("Water Supply",                 complaint.getCategory().getCategoryName());

        System.out.println("✅ Complaints Mock Test Passed!");
        System.out.println("   Title    : " + complaint.getTitle());
        System.out.println("   Location : " + complaint.getLocation());
        System.out.println("   Status   : " + complaint.getStatus());
        System.out.println("   Priority : " + complaint.getPriority());
        System.out.println("   User     : " + complaint.getUser().getFullName());
        System.out.println("   Category : " + complaint.getCategory().getCategoryName());
    }
}