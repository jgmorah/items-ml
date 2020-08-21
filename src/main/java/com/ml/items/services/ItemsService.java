package com.ml.items.services;

import com.ml.items.adapters.metrics.Metric;
import com.ml.items.adapters.metrics.MetricType;
import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ports.ItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemsService {

    Logger logger = LoggerFactory.getLogger(ItemsService.class);

    private ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    /**
     * Logic to ask for item information into database, if it's not present then go to call an external APIs
     *
     * @param itemId
     * @return
     */
    public Optional<Item> getItem(String itemId) {
        Optional<Item> item = itemsRepository.findLocalItemById(itemId);
        if (item.isEmpty()) {
            logger.info("Getting item information from external API");
            Metric.addMetric(MetricType.API_CALLS, 1);
            LocalDateTime startTime = LocalDateTime.now();
            item = itemsRepository.findExternalItemById(itemId);
            Metric.addMetric(MetricType.AVG_API_RESPONSE_TIME, java.time.Duration.between(startTime, LocalDateTime.now()).toMillis());
            item.ifPresent(i -> itemsRepository.save(i));
        }
        return item;

    }
}
