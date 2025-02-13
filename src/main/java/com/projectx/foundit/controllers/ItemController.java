package com.projectx.foundit.controllers;

import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import com.projectx.foundit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/insertitems")
    public ResponseEntity<Item> insertItem(@RequestBody Item item) {
        item.setCreatedAt(LocalTime.now());  // Set created_at before saving
        item.setUpdatedAt(LocalTime.now());
        Item savedItem = itemService.insertItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);

    }

    @GetMapping("/getitems/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable int itemId) {
        Optional<Item> item = itemService.getItemById(itemId);

        if (item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
