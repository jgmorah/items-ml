package com.ml.items.adapters.DTO.out.health;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ml.items.adapters.metrics.MetricType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomHealth {

    private LocalDateTime date;
    @JsonProperty("avg_response_time")
    private long averageResponseTime;
    @JsonProperty("total_request")
    private long totalRequest;
    @JsonProperty("avg_response_time_api_calls")
    private long averageResponseTimeApiCalls;
    @JsonProperty("total_count_api_calls")
    private long totalCountApiCalls;
    @JsonProperty("info_request")
    private List<InfoRequest> infoRequest;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(long averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public long getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(long totalRequest) {
        this.totalRequest = totalRequest;
    }

    public long getAverageResponseTimeApiCalls() {
        return averageResponseTimeApiCalls;
    }

    public void setAverageResponseTimeApiCalls(long averageResponseTimeApiCalls) {
        this.averageResponseTimeApiCalls = averageResponseTimeApiCalls;
    }

    public long getTotalCountApiCalls() {
        return totalCountApiCalls;
    }

    public void setTotalCountApiCalls(long totalCountApiCalls) {
        this.totalCountApiCalls = totalCountApiCalls;
    }

    public List<InfoRequest> getInfoRequest() {
        return infoRequest;
    }

    public void setInfoRequest(List<InfoRequest> infoRequest) {
        this.infoRequest = infoRequest;
    }

    public static CustomHealth processSet(LocalDateTime dateTime, HashMap<MetricType, Long> metrics) {
        CustomHealth dto = new CustomHealth();
        dto.date = dateTime;
        dto.averageResponseTime = metrics.getOrDefault(MetricType.AVG_RESPONSE_TIME, -1L);
        dto.averageResponseTimeApiCalls = metrics.getOrDefault(MetricType.AVG_API_RESPONSE_TIME, -1L);
        dto.totalCountApiCalls = metrics.getOrDefault(MetricType.API_CALLS, -1L);
        dto.totalRequest = metrics.getOrDefault(MetricType.TOTAL_REQUEST, -1L);
        dto.infoRequest = new ArrayList<>();
        dto.infoRequest.add(new InfoRequest("200", metrics.getOrDefault(MetricType.RESPONSE_STATUS_CODE_OK, -1L)));
        dto.infoRequest.add(new InfoRequest("500", metrics.getOrDefault(MetricType.RESPONSE_STATUS_CODE_ERROR, -1L)));

        return dto;
    }

}
