package com.ml.items.adapters.metrics;

import com.ml.items.adapters.rest.ItemsController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Metric {
    private MetricType type;
    private long qty;

    public Metric(MetricType type, long qty) {
        this.type = type;
        this.qty = qty;
    }

    public MetricType getType() {
        return type;
    }

    public void setType(MetricType type) {
        this.type = type;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public static ConcurrentHashMap<LocalDateTime, List<Metric>> addMetric(MetricType type, long qty) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (ItemsController.httpTracker.containsKey(now)) {
            List<Metric> metrics = ItemsController.httpTracker.get(now);
            metrics.add(new Metric(type, qty));

        } else {
            ItemsController.httpTracker.put(now, new ArrayList<>(Collections.singletonList(new Metric(type, qty))));
        }
        return ItemsController.httpTracker;
    }

}
