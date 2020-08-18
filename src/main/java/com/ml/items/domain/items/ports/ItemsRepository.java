package com.ml.items.domain.items.ports;

import com.ml.items.domain.items.Item;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ItemsRepository {
    Optional<Item> findLocalItemById(String ItemId);

    Optional<Item> findExternalItemById(String ItemId);

    Item save(Item item);
}
