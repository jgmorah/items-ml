package com.ml.items.domain.items;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Item implements Serializable {
    private String itemId;
    private String title;
    private String categoryId;
    private String price;
    private LocalDateTime StartTime;
    private LocalDateTime StopTime;
    private List<ItemChildren> children;

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

    public LocalDateTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        StartTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return StopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        StopTime = stopTime;
    }

    public List<ItemChildren> getChildren() {
        return children;
    }

    public void setChildren(List<ItemChildren> children) {
        this.children = children;
    }
}
