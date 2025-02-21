package com.projectx.foundit.controllers;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.repository.ItemImageRepository;
import com.projectx.foundit.service.ItemImageService;
import com.projectx.foundit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemImageService itemImageService;
    @Autowired
    private ItemImageRepository itemImageRepository;

    @PostMapping("/insertitems")
    public ResponseEntity<Item> insertItem(@RequestBody Item item) {
        item.setCreatedAt(LocalTime.now());
        item.setUpdatedAt(LocalTime.now());
        Item savedItem = itemService.insertItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);

    }

    @GetMapping(value = "/getitems/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable int itemId) {
        Optional<Item> item = itemService.getItemById(itemId);

        if (item.isPresent()) {
            try {
                return new ResponseEntity<>(item.get(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return null;
    }

    @PutMapping("updateitem/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item updatedItem) {
        Item item = itemService.updateItem(id, updatedItem);

        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteitem/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.OK); // 204 No Content is standard for successful delete
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 if not found
        }
    }

    public ResponseEntity<ItemImage> getImageByID(@PathVariable long imageID) {
        Optional<ItemImage> itemimage = itemImageService.getImageById(imageID);

        if (itemimage.isPresent()) {
            return new ResponseEntity<>(itemimage.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getitemsbyuser/{userId}")
    public ResponseEntity<Object> getItemByUser(@PathVariable int userId) {
        Optional<Item> item = itemService.getItemById(userId);

        if (item.isPresent()) {
            try {
                // Fetch user details from another microservice
                Object userDetails = itemService.fetchUserDetails(userId);

                // Combine item data and user data
                Map<String, Object> response = new HashMap<>();
                response.put("item", item.get());
                response.put("userDetails", userDetails);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
