package com.projectx.foundit.controllers;

import com.projectx.foundit.dto.VerifyUserDto;
import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemHelper {

    @Value("${api.base.url}")
    private static String baseUrl;

    @Value("${api.port}")
    private static String port;

    private final RestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ItemHelper() {
        restTemplate = null;
    }

    public static String verifyUser(VerifyUserDto verifyUserDto) {
        RestTemplate restTemplate = new RestTemplate();

        String url = baseUrl + ":" + port + "/auth/verify";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VerifyUserDto> request = new HttpEntity<>(verifyUserDto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String callEndpoint() {
        String url = baseUrl + ":" + port + "/auth/verify";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Failed to call endpoint: " + e.getMessage());
            return null;
        }
    }

//    public List<Item> searchItems(String itemName, String locationFound, String description) {
//        List<Item> results = new ArrayList<>();
//
//        try {
//            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//            CriteriaQuery<Item> query = cb.createQuery(Item.class);
//            Root<Item> item = query.from(Item.class);
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (itemName != null && !itemName.trim().isEmpty()) {
//                predicates.add(cb.like(item.get("itemName"), "%" + itemName + "%"));
//            }
//            if (locationFound != null && !locationFound.trim().isEmpty()) {
//                predicates.add(cb.like(item.get("locationFound"), "%" + locationFound + "%"));
//            }
//            if (description != null && !description.trim().isEmpty()) {
//                predicates.add(cb.like(item.get("description"), "%" + description + "%"));
//            }
//
//            query.where(cb.and(predicates.toArray(new Predicate[0])));
//            results = entityManager.createQuery(query).getResultList();
//
//        } catch (IllegalArgumentException e) {
//            System.err.println("Invalid argument provided: " + e.getMessage());
//        } catch (PersistenceException e) {
//            System.err.println("Database error occurred: " + e.getMessage());
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred: " + e.getMessage());
//        }
//
//        return results;
//    }

}

