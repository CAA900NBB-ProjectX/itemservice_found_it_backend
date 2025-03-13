package com.projectx.foundit.service;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.repository.ItemImageRepository;
import com.projectx.foundit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.NoSuchElementException;
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
        try {
            Optional<Item> item = itemRepository.findById(itemId);

 /*       if(item != null) {
            Optional<ItemImage> imageId = itemImageRepository.findById(Long.valueOf(item.get().getItem_id()));

            for (int i = 0; i < item.get().getImageIdsList().size() ; i++) {
                item.get().setImageIdsList(Collections.singletonList(imageId.get().getItemID().getImageIdsList().get(i)));
            }

        }*/
            return itemRepository.findById(itemId);
        } catch (Exception e) {
            System.err.println("An error occurred while fetching the item: " + e.getMessage());
            return Optional.empty(); // Return an empty optional on failure
        }
    }

    public Item updateItem(long id, Item updatedItem) {

        try {
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
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            System.err.println("Data integrity issue: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }

        return null;
    }

    public void deleteItem(long id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                itemRepository.delete(item.get());
            } else {
                throw new ItemNotFoundException("Item with ID " + id + " not found");
            }
        } catch (DataIntegrityViolationException e) {
            System.err.println("Failed to delete item due to data integrity constraints: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while deleting the item: " + e.getMessage());
        }
    }

}
