package com.projectx.foundit.controllers;

import com.projectx.foundit.model.Item;
import com.projectx.foundit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/insertitems")
    public ResponseEntity<Item> insertItem(@RequestBody Item item) {
        Item savedItem = itemService.insertItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);

    }
}
