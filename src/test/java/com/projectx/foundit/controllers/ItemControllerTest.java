package com.projectx.foundit.controllers;

import com.projectx.foundit.model.Item;
import com.projectx.foundit.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

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
    void insertItem_shouldReturnCreatedItem() {
        when(itemService.insertItem(any(Item.class))).thenReturn(testItem);

        ResponseEntity<Item> response = itemController.insertItem(testItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Item", response.getBody().getItemName());
        assertEquals("Test Description", response.getBody().getDescription());
        verify(itemService).insertItem(any(Item.class));
    }

    @Test
    void getallItems_shouldReturnAllItems() {
        List<Item> items = Arrays.asList(testItem);
        when(itemService.getallItems()).thenReturn(items);

        ResponseEntity<?> response = itemController.getallItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Item> responseItems = (List<Item>) response.getBody();
        assertEquals(1, responseItems.size());
        assertEquals("Test Item", responseItems.get(0).getItemName());
        verify(itemService).getallItems();
    }

    @Test
    void getItemById_shouldReturnItem() {
        when(itemService.getItemById(1)).thenReturn(Optional.of(testItem));

        ResponseEntity<Item> response = itemController.getItemById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Item", response.getBody().getItemName());
        verify(itemService).getItemById(1);
    }

    @Test
    void getItemById_notFound_shouldReturnNotFound() {
        when(itemService.getItemById(999)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(itemService).getItemById(999);
    }

    @Test
    void updateItem_shouldReturnUpdatedItem() {
        Item updatedItem = new Item();
        updatedItem.setItemName("Updated Item");
        updatedItem.setDescription("Updated Description");

        when(itemService.updateItem(eq(1), any(Item.class))).thenReturn(updatedItem);

        ResponseEntity<?> response = itemController.updateItem(1, updatedItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Item", ((Item) response.getBody()).getItemName());
        verify(itemService).updateItem(eq(1), any(Item.class));
    }
}