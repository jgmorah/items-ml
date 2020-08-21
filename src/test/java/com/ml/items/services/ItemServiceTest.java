package com.ml.items.services;

import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ports.ItemsRepository;
import org.junit.Assert;
import org.junit.Before;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    ItemsService itemsService;
    @Mock
    private ItemsRepository itemsRepository;
    private static final String ITEM_IDENTIFIER_TEST = "ID123";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindLocalItem() {
        when(itemsRepository.findLocalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(dummyItem());
        Optional<Item> result = itemsService.getItem(ITEM_IDENTIFIER_TEST);
        Assert.assertNotNull(result);
        verify(itemsRepository, times(1)).findLocalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(0)).findExternalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(0)).save(any());
    }

    @Test
    public void testFindExternalItem() {
        when(itemsRepository.findLocalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(Optional.empty());
        when(itemsRepository.findExternalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(dummyItem());
        Optional<Item> result = itemsService.getItem(ITEM_IDENTIFIER_TEST);
        Assert.assertNotNull(result);
        verify(itemsRepository, times(1)).findLocalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).findExternalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).save(any());
    }

    @Test
    public void testFindExternalAndThenInternalItem() {
        when(itemsRepository.findLocalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(Optional.empty());
        when(itemsRepository.findExternalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(dummyItem());
        Optional<Item> result = itemsService.getItem(ITEM_IDENTIFIER_TEST);
        Assert.assertNotNull(result);
        verify(itemsRepository, times(1)).findLocalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).findExternalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).save(result.get());
        when(itemsRepository.findLocalItemById(ITEM_IDENTIFIER_TEST)).thenReturn(dummyItem());
        result = itemsService.getItem(ITEM_IDENTIFIER_TEST);
        Assert.assertNotNull(result);
        verify(itemsRepository, times(2)).findLocalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).findExternalItemById(ITEM_IDENTIFIER_TEST);
        verify(itemsRepository, times(1)).save(any());
    }


    private Optional<Item> dummyItem() {
        Item item = new Item();
        item.setItemId(ITEM_IDENTIFIER_TEST);
        item.setPrice("100");
        item.setCategoryId("MLU1055");
        return Optional.of(item);
    }
}
