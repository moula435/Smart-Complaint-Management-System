package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Users;

public class UsersMockTest {

    @Test
    public void userMockTest() {
        Users user = mock(Users.class);

        when(user.getFullName()).thenReturn("Ravi Kumar");
        when(user.getEmail()).thenReturn("ravi@gmail.com");
        when(user.getPhone()).thenReturn("9876543210");
        when(user.getAddress()).thenReturn("Hyderabad");
        when(user.getStatus()).thenReturn(Users.Status.active);

        assertEquals("Ravi Kumar",       user.getFullName());
        assertEquals("ravi@gmail.com",   user.getEmail());
        assertEquals("9876543210",       user.getPhone());
        assertEquals("Hyderabad",        user.getAddress());
        assertEquals(Users.Status.active, user.getStatus());

        System.out.println("✅ Users Mock Test Passed!");
        System.out.println("   Name    : " + user.getFullName());
        System.out.println("   Email   : " + user.getEmail());
        System.out.println("   Phone   : " + user.getPhone());
        System.out.println("   Address : " + user.getAddress());
        System.out.println("   Status  : " + user.getStatus());
    }
}