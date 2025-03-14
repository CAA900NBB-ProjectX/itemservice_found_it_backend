package com.projectx.foundit.service;

import com.projectx.foundit.commons.ItemNotFoundException;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.repository.ItemImageRepository;
import com.projectx.foundit.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private ItemImageService itemImageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PersistenceContext
    private EntityManager entityManager;


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

    @Transactional(readOnly = true)
    public List<Item> searchItems(String itemName, String locationFound, String description) {
        List<Item> results = new ArrayList<>();

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Item> query = cb.createQuery(Item.class);
            Root<Item> item = query.from(Item.class);
            List<Predicate> predicates = new ArrayList<>();

            if (itemName != null && !itemName.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(item.get("itemName")), "%" + itemName.toLowerCase() + "%"));
            }
            if (locationFound != null && !locationFound.trim().isEmpty()) {
                predicates.add(cb.like(item.get("locationFound"), "%" + locationFound + "%"));
            }
            if (description != null && !description.trim().isEmpty()) {
                predicates.add(cb.like(item.get("description"), "%" + description + "%"));
            }

            System.out.println("Query results: " + results.size() + " items found");
            for (Item i : results) {
                System.out.println("Item: " + i.getItemName());
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            results = entityManager.createQuery(query).getResultList();

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument provided: " + e.getMessage());
        } catch (PersistenceException e) {
            System.err.println("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }

        return results;
    }

    @Transactional(readOnly = true)
    public List<Item> searchItems2(String itemName, String locationFound, String description) {
        // Log the search request
        System.out.println("Searching for items with criteria - Name: " + itemName
                + ", Location: " + locationFound
                + ", Description: " + description);

        try {
            // Create a native SQL query for maximum compatibility with PostgreSQL
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM lostitems WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            // Add search conditions if parameters are provided
            if (itemName != null && !itemName.trim().isEmpty()) {
                sqlBuilder.append(" AND LOWER(item_name) LIKE LOWER(:itemName)");
                parameters.put("itemName", "%" + itemName.trim() + "%");
            }

            if (locationFound != null && !locationFound.trim().isEmpty()) {
                sqlBuilder.append(" AND LOWER(location_found) LIKE LOWER(:locationFound)");
                parameters.put("locationFound", "%" + locationFound.trim() + "%");
            }

            if (description != null && !description.trim().isEmpty()) {
                sqlBuilder.append(" AND LOWER(description) LIKE LOWER(:description)");
                parameters.put("description", "%" + description.trim() + "%");
            }

            // Create the native query
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Item.class);

            // Set parameters
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            // Execute query and get results
            @SuppressWarnings("unchecked")
            List<Item> results = query.getResultList();

            // Log results
            System.out.println("Search found " + results.size() + " items");
            for (Item item : results) {
                System.out.println("Found: ID=" + item.getItem_id() + ", Name=" + item.getItemName());
            }

            return results;

        } catch (Exception e) {
            System.err.println("Error searching for items: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
