package com.ml.items.services;

import com.ml.items.adapters.ItemsRepositoryImp;
import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ports.ItemsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemsService {


    ItemsRepository itemsRepository;

    public ItemsService() {
        this.itemsRepository = new ItemsRepositoryImp();
    }

    public Optional<Item> getItem(String itemId) {
        Optional<Item> item = itemsRepository.findLocalItemById(itemId);
        if (item.isEmpty()) {
            item = itemsRepository.findExternalItemById(itemId);
            item.map(v -> itemsRepository.save(v));
        }
        return item;

    }
}
