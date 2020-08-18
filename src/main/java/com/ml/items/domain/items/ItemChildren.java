package com.ml.items.domain.items;

import java.time.LocalDateTime;

public class ItemChildren {

    private String ItemId;
    private LocalDateTime stopTime;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }
}
