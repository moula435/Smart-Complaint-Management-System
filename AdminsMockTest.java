package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Admins;

public class AdminsMockTest {

    @Test
    public void adminMockTest() {
        Admins admin = mock(Admins.class);

        when(admin.getFullName()).thenReturn("Super Admin");
        when(admin.getEmail()).thenReturn("admin@complaint.com");
        when(admin.getPhone()).thenReturn("9999999999");
        when(admin.getRole()).thenReturn(Admins.Role.superadmin);

        assertEquals("Super Admin",          admin.getFullName());
        assertEquals("admin@complaint.com",  admin.getEmail());
        assertEquals("9999999999",           admin.getPhone());
        assertEquals(Admins.Role.superadmin, admin.getRole());

        System.out.println("✅ Admins Mock Test Passed!");
        System.out.println("   Name  : " + admin.getFullName());
        System.out.println("   Email : " + admin.getEmail());
        System.out.println("   Phone : " + admin.getPhone());
        System.out.println("   Role  : " + admin.getRole());
    }
}