package com.ml.items.adapters.rest;

import com.ml.items.adapters.DTO.out.ItemResponseDto;
import com.ml.items.adapters.exception.ItemNotFoundException;
import com.ml.items.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

    private static final String errorMsg = "Item not found, id: ";

    private ItemsService itemsService;

    @Autowired
    ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/items/{itemId}")
    public ItemResponseDto getItem(@PathVariable String itemId) {

        return ItemResponseDto.fromDomain(itemsService.getItem(itemId).orElseThrow(() -> new ItemNotFoundException(errorMsg.concat(itemId))));

    }

}
