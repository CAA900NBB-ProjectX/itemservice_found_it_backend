package com.projectx.foundit.controllers;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.dto.VerifyUserDto;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.model.Token;
import com.projectx.foundit.model.User;
import com.projectx.foundit.repository.ItemImageRepository;
import com.projectx.foundit.service.ItemImageService;
import com.projectx.foundit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
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

    private ItemHelper itemHelper = new ItemHelper();

    @PostMapping("/insertitems")
    public ResponseEntity<Item> insertItem(@RequestBody Item item) {
        item.setCreatedAt(LocalTime.now());
        item.setUpdatedAt(LocalTime.now());
        Item savedItem = itemService.insertItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);

    }
    @GetMapping(value = "/getallitems")
    public ResponseEntity<?> getallItems() {
        try {
            List<Item> users = itemService.getallItems();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

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

    @GetMapping(value = "/getitemimages/{imageId}")
    public ResponseEntity<Item> getImageById(@PathVariable int imageId) {
        Optional<ItemImage> itemImage = itemImageService.getImageById(imageId);

        if (itemImage.isPresent()) {
            try {
                return new ResponseEntity<>(itemImage.get().getItemID(), HttpStatus.OK);
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
//                TODO: User Details endpoint
//                Object userDetails = itemService.fetchUserDetails(userId);
                if (false) {
                    VerifyUserDto dto = new VerifyUserDto("username", "otpCode");
                    String result = ItemHelper.verifyUser(dto);
                    System.out.println(result);
                }


                Map<String, Object> response = new HashMap<>();
                response.put("item", item.get());
                response.put("userDetails", "userDetails");


                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "locationFound", required = false) String locationFound,
            @RequestParam(value = "description", required = false) String description) {

        List<Item> items = itemService.searchItems2(itemName, locationFound, description);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/getToken")
    public ResponseEntity<Token> getToken(@PathVariable int userId,@PathVariable String token){

        //TODO: get and validation segment
        return null;
    }

}
