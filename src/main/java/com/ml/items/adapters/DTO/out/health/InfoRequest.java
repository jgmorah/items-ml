package com.ml.items.adapters.DTO.out.health;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoRequest {
    public InfoRequest(String statusCode, long count) {
        this.statusCode = statusCode;
        this.count = count;
    }

    @JsonProperty("total_request")
    private String statusCode;

    private long count;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
