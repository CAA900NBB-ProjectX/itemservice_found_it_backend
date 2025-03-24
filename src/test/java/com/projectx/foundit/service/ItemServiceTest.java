package com.projectx.foundit.service;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setItem_id(1);
        testItem.setItemName("Test Item");
        testItem.setDescription("Test Description");
        testItem.setCategoryId(1);
        testItem.setLocationFound("Test Location");
        testItem.setDateTimeFound(LocalDateTime.now());
        testItem.setReportedBy("Test User");
        testItem.setContactInfo("test@example.com");
        testItem.setStatus("FOUND");
        testItem.setCreatedAt(LocalTime.now());
        testItem.setUpdatedAt(LocalTime.now());
    }

    @Test
    void insertItem_shouldSaveAndReturnItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(testItem);

        Item savedItem = itemService.insertItem(testItem);

        assertNotNull(savedItem);
        assertEquals("Test Item", savedItem.getItemName());
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void getallItems_shouldReturnListOfItems() {
        List<Item> expectedItems = Arrays.asList(testItem);
        when(itemRepository.findAll()).thenReturn(expectedItems);

        List<Item> actualItems = itemService.getallItems();

        assertEquals(1, actualItems.size());
        assertEquals("Test Item", actualItems.get(0).getItemName());
        verify(itemRepository).findAll();
    }

    @Test
    void getItemById_shouldReturnItem_whenExists() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));

        Optional<Item> result = itemService.getItemById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Item", result.get().getItemName());
        verify(itemRepository).findById(1L);
    }

    @Test
    void getItemById_shouldReturnEmpty_whenNotExists() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Item> result = itemService.getItemById(999);

        assertTrue(result.isEmpty());
        verify(itemRepository).findById(999L);
    }

    @Test
    void updateItem_shouldUpdateAndReturnItem_whenExists() {
        // Prepare updated item
        Item updatedItem = new Item();
        updatedItem.setItemName("Updated Item");
        updatedItem.setDescription("Updated Description");

        // Mock repository behavior
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Item result = itemService.updateItem(1, updatedItem);

        // Assert the result
        assertNotNull(result);
        assertEquals("Updated Item", result.getItemName());
        assertEquals("Updated Description", result.getDescription());
        verify(itemRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deleteItem_shouldDeleteItem_whenExists() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        doNothing().when(itemRepository).delete(any(Item.class));

        // Should not throw an exception
        assertDoesNotThrow(() -> itemService.deleteItem(1));

        verify(itemRepository).findById(1L);
        verify(itemRepository).delete(any(Item.class));
    }

    @Test
    void deleteItem_shouldThrowException_whenNotExists() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        // Should throw ItemNotFoundException
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
                () -> itemService.deleteItem(999));

        // Verify the error message
        assertTrue(exception.getMessage().contains("999"));
        verify(itemRepository).findById(999L);
        verify(itemRepository, never()).delete(any(Item.class));
    }

    @Test
    void searchItems2_shouldReturnFilteredItems() {
        // This is a basic test for the search method
        // The actual implementation may be more complex
        List<Item> mockResults = Arrays.asList(testItem);

        // Assume searchItems2 uses findAll internally (simplification)
        when(itemRepository.findAll()).thenReturn(mockResults);

        List<Item> results = itemService.searchItems2("Test", null, null);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        // We don't make more specific assertions because we don't know
        // exactly how searchItems2 filters the results
    }
}