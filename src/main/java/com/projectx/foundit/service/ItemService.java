package com.projectx.foundit.service;

import com.projectx.foundit.model.Item;
import com.projectx.foundit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item insertItem(Item item){
        return (Item) itemRepository.save(item);
    }

}
