package com.projectx.foundit.service;

import com.projectx.foundit.model.ItemImage;
import com.projectx.foundit.repository.ItemImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemImageService {

    @Autowired
    private ItemImageRepository itemImageRepository;

    public ItemImage saveItemImage(ItemImage itemImage) {
        return itemImageRepository.save(itemImage);
    }

    public Optional<ItemImage> getImageById(long imageId) {
        return itemImageRepository.findById(imageId);
    }

}