package com.projectx.foundit.service;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.repository.ItemImageRepository;
import com.projectx.foundit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private ItemImageService itemImageService;

    public Item insertItem(Item item) {
//        Item savedItem = itemRepository.save(item);
//        if (item.getImages()!= null) {
//            for (ItemImage image: item.getImages()) {
//                image.setImage(savedItem); ; // setItem(savedItem);
//                itemImageService.saveItemImage(image);
//            }
//        }
//        return savedItem;

//        return (Item) itemRepository.save(item);


        // First, save the images and get their IDs
        if (item.getImages() != null) {
            for (ItemImage image : item.getImages()) {
                // Save the image to the image table
                ItemImage savedImage = itemImageService.saveItemImage(image);
                // Set the saved image's ID in the item's image list
                item.setImageIdsList(Collections.singletonList(savedImage.getId()));
                image.setId(savedImage.getId());// setItemImageId(savedImage.getItemImageId());
            }
        }
        // Now, save the item with the image IDs as foreign keys
        return itemRepository.save(item);
    }

    public Optional<Item> getItemById(long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);

 /*       if(item != null) {
            Optional<ItemImage> imageId = itemImageRepository.findById(Long.valueOf(item.get().getItem_id()));

            for (int i = 0; i < item.get().getImageIdsList().size() ; i++) {
                item.get().setImageIdsList(Collections.singletonList(imageId.get().getItemID().getImageIdsList().get(i)));
            }

        }*/
        return itemRepository.findById(itemId);
    }

    public Item updateItem(long id, Item updatedItem) {
        Optional<Item> existingItem = itemRepository.findById(id);

        if (existingItem.isPresent()) {
            Item item = existingItem.get();

            if (updatedItem.getItemName() != null) {
                item.setItemName(updatedItem.getItemName());
            }
            if (updatedItem.getDescription() != null) {
                item.setDescription(updatedItem.getDescription());
            }
            if (updatedItem.getCategoryId() != null) {
                item.setCategoryId(updatedItem.getCategoryId());
            }
            if (updatedItem.getLocationFound() != null) {
                item.setLocationFound(updatedItem.getLocationFound());
            }
            if (updatedItem.getDateTimeFound() != null) {
                item.setDateTimeFound(updatedItem.getDateTimeFound());
            }
            if (updatedItem.getReportedBy() != null) {
                item.setReportedBy(updatedItem.getReportedBy());
            }
            if (updatedItem.getContactInfo() != null) {
                item.setContactInfo(updatedItem.getContactInfo());
            }
            if (updatedItem.getStatus() != null) {
                item.setStatus(updatedItem.getStatus());
            }

            item.setUpdatedAt(LocalTime.now());
            return itemRepository.save(item);
        } else {
            return null;
        }
    }

    public void deleteItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.delete(item.get());
        } else {
            throw new ItemNotFoundException("Item with ID " + id + " not found");
        }
    }


}
