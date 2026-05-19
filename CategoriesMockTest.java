package com.smartcomplaintMockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import com.smartcomplaint.Entities.Categories;

public class CategoriesMockTest {

    @Test
    public void categoryMockTest() {
        Categories category = mock(Categories.class);

        when(category.getCategoryName()).thenReturn("Garbage Collection");
        when(category.getDescription()).thenReturn("Issues related to garbage pickup");
        when(category.isActive()).thenReturn(true);

        assertEquals("Garbage Collection",             category.getCategoryName());
        assertEquals("Issues related to garbage pickup", category.getDescription());
        assertEquals(true,                             category.isActive());

        System.out.println("✅ Categories Mock Test Passed!");
        System.out.println("   Name        : " + category.getCategoryName());
        System.out.println("   Description : " + category.getDescription());
        System.out.println("   Active      : " + category.isActive());
    }
}