package com.ml.items.adapters.utils;

import com.ml.items.adapters.DTO.out.health.CustomHealth;
import com.ml.items.adapters.metrics.Metric;
import com.ml.items.adapters.metrics.MetricType;
import com.ml.items.adapters.rest.ItemsController;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HealthCheckProcessor {

    public List<CustomHealth> getHealth() {

        Iterator<Map.Entry<java.time.LocalDateTime, List<Metric>>> it = ItemsController.httpTracker.entrySet().iterator();
        List<CustomHealth> result = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<java.time.LocalDateTime, List<Metric>> entry = it.next();
            List<Metric> metricsPerMinute = entry.getValue();
            Map<MetricType, List<Metric>> groupedMap = grouper(metricsPerMinute);
            HashMap<MetricType, Long> calculatedMetrics = calculate(groupedMap);
            result.add(CustomHealth.processSet(entry.getKey(), calculatedMetrics));
        }

        return result;
    }

    private Map<MetricType, List<Metric>> grouper(List<Metric> metrics) {
        return metrics.stream().collect(Collectors.groupingBy(Metric::getType));
    }

    private HashMap<MetricType, Long> calculate(Map<MetricType, List<Metric>> groupedMap) {
        HashMap<MetricType, Long> result = new HashMap<>();

        for (Map.Entry<MetricType, List<Metric>> entry : groupedMap.entrySet()) {
            double value;
            if (entry.getKey().equals(MetricType.AVG_API_RESPONSE_TIME) || entry.getKey().equals(MetricType.AVG_RESPONSE_TIME)) {    //average
                value = entry.getValue().stream().mapToInt(v -> (int) v.getQty()).average().orElse(0);
            } else {
                value = entry.getValue().stream().mapToInt(v -> (int) v.getQty()).sum();
            }
            result.put(entry.getKey(), (long) value);
        }
        return result;
    }
}
