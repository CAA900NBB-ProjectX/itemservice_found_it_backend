package com.projectx.foundit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled("Disable integration tests until a proper test database is configured")
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setItemName("Integration Test Item");
        testItem.setDescription("Integration Test Description");
        testItem.setCategoryId(1);
        testItem.setLocationFound("Test Location");
        testItem.setDateTimeFound(LocalDateTime.now());
        testItem.setReportedBy("Test User");
        testItem.setContactInfo("integration-test@example.com");
        testItem.setStatus("FOUND");
        testItem.setCreatedAt(LocalTime.now());
        testItem.setUpdatedAt(LocalTime.now());
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
    }

    @Test
    void crudOperations() throws Exception {
        // Create an item
        String itemJson = objectMapper.writeValueAsString(testItem);

        String responseJson = mockMvc.perform(post("/item/insertitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", is("Integration Test Item")))
                .andExpect(jsonPath("$.description", is("Integration Test Description")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created item's ID
        Item createdItem = objectMapper.readValue(responseJson, Item.class);
        int itemId = createdItem.getItem_id();

        // Get the item by ID
        mockMvc.perform(get("/item/getitems/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", is("Integration Test Item")))
                .andExpect(jsonPath("$.description", is("Integration Test Description")));

        // Update the item
        createdItem.setItemName("Updated Integration Item");
        createdItem.setDescription("Updated Integration Description");

        mockMvc.perform(put("/item/updateitem/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", is("Updated Integration Item")))
                .andExpect(jsonPath("$.description", is("Updated Integration Description")));

        // Search for the item
        mockMvc.perform(get("/item/search")
                        .param("itemName", "Updated Integration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].itemName", is("Updated Integration Item")));

        // Delete the item
        mockMvc.perform(delete("/item/deleteitem/" + itemId))
                .andExpect(status().isOk());

        // Verify item is deleted
        mockMvc.perform(get("/item/getitems/" + itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllItems() throws Exception {
        // Save test item to repository
        itemRepository.save(testItem);

        // Get all items
        mockMvc.perform(get("/item/getallitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].itemName", hasItem("Integration Test Item")));
    }
}