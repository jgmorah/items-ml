package com.ml.items.adapters.DTO.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ml.items.domain.items.Item;

import java.time.LocalDateTime;
import java.util.List;

public class ItemResponseDto {

    @JsonProperty("item_id")
    private String itemId;
    private String title;
    @JsonProperty("category_id")
    private String categoryId;
    private String price;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private List<ItemChildrenResponseDto> children;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        startTime = startTime;
    }

    @JsonProperty("stop_time")
    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        stopTime = stopTime;
    }

    public List<ItemChildrenResponseDto> getChildren() {
        return children;
    }

    public void setChildren(List<ItemChildrenResponseDto> children) {
        this.children = children;
    }

    public static ItemResponseDto fromDomain(Item item) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.itemId = item.getItemId();
        dto.title = item.getTitle();
        dto.categoryId = item.getCategoryId();
        dto.price = item.getPrice();
        dto.startTime = item.getStartTime();
        dto.stopTime = item.getStopTime();
        dto.children = ItemChildrenResponseDto.fromDomain(item.getChildren());

        return dto;
    }
}
