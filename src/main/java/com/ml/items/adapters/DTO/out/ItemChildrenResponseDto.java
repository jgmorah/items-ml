package com.ml.items.adapters.DTO.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ml.items.domain.items.ItemChildren;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemChildrenResponseDto {
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("stop_time")
    private LocalDateTime stopTime;

    public ItemChildrenResponseDto(String itemId, LocalDateTime stopTime) {
        this.itemId = itemId;
        this.stopTime = stopTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        itemId = itemId;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public static List<ItemChildrenResponseDto> fromDomain(List<ItemChildren> itemChildren) {
        List<ItemChildrenResponseDto> dto = new ArrayList<>();
        itemChildren.forEach(domainItemChild -> dto.add(new ItemChildrenResponseDto(domainItemChild.getItemId(), domainItemChild.getStopTime())));

        return dto;
    }
}
