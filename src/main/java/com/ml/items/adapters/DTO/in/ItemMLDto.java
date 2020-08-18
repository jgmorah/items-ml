package com.ml.items.adapters.DTO.in;

import com.google.gson.annotations.SerializedName;
import com.ml.items.domain.items.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemMLDto {

    private String id;
    private String title;
    @SerializedName("category_id")
    private String categoryId;
    private String price;
    @SerializedName("start_time")
    private String StartTime;
    @SerializedName("stop_time")
    private String StopTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String stopTime) {
        StopTime = stopTime;
    }

    public static Item toDomain(ItemMLDto dto) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Item item = new Item();
        item.setItemId(dto.id);
        item.setTitle(dto.title);
        item.setCategoryId(dto.categoryId);
        item.setPrice(dto.price);
        item.setStartTime(LocalDateTime.parse(dto.getStartTime(), inputFormatter));
        item.setStopTime(LocalDateTime.parse(dto.getStopTime(), inputFormatter));

        return item;
    }
}
