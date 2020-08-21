package com.ml.items.adapters.rest;

import com.ml.items.adapters.DTO.out.ItemResponseDto;
import com.ml.items.adapters.DTO.out.health.CustomHealth;
import com.ml.items.adapters.exception.ItemNotFoundException;
import com.ml.items.adapters.metrics.Metric;
import com.ml.items.adapters.metrics.MetricType;
import com.ml.items.adapters.utils.HealthCheckProcessor;
import com.ml.items.services.ItemsService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController

public class ItemsController {

    private static final String ERROR_MSG = "Item not found, id: ";
    /**
     * This metric doesnt work for multiple instances
     */
    public static HashMap<LocalDateTime, List<Metric>> httpTracker = new HashMap<>();

    /**
     * Prometheus counter in order to track some specific steps of our application - example http requests quantity metrics
     */
    Counter localApiCounter;
    private ItemsService itemsService;
    private HealthCheckProcessor healthCheckProcessor;

    @Autowired
    ItemsController(ItemsService itemsService, MeterRegistry prometheusRegistry, HealthCheckProcessor healthCheckProcessor) {
        this.itemsService = itemsService;
        this.localApiCounter = prometheusRegistry.counter("local_api_calls");
        this.healthCheckProcessor = healthCheckProcessor;
    }

    @Timed
    @GetMapping("/items/{itemId}")
    public ItemResponseDto getItem(@PathVariable String itemId) {

        LocalDateTime startTime = LocalDateTime.now();
        localApiCounter.increment();
        Metric.addMetric(MetricType.TOTAL_REQUEST, 1);
        ItemResponseDto response = ItemResponseDto.fromDomain(
                itemsService.getItem(itemId)
                        .orElseThrow(() -> {
                            Metric.addMetric(MetricType.RESPONSE_STATUS_CODE_ERROR, 1);
                            return new ItemNotFoundException(ERROR_MSG.concat(itemId));
                        })
        );
        Metric.addMetric(MetricType.AVG_RESPONSE_TIME, java.time.Duration.between(startTime, LocalDateTime.now()).toMillis());
        Metric.addMetric(MetricType.RESPONSE_STATUS_CODE_OK, 1);
        return response;

    }


    @GetMapping("/health")
    public List<CustomHealth> getHealth() {
        return healthCheckProcessor.getHealth();

    }

}
