package com.projectx.foundit.service;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item insertItem(Item item){
        return (Item) itemRepository.save(item);
    }

    public Optional<Item> getItemById(long itemId) {
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
            throw new ItemNotFoundException("Item with ID " + id + " not found"); // Example: Throwing an exception
        }
    }


}
