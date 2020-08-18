package com.ml.items.adapters.DTO.in;

import com.google.gson.annotations.SerializedName;
import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ItemChildren;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemChildrenMLDto {

    private String id;
    @SerializedName("stop_time")
    private String StopTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String stopTime) {
        StopTime = stopTime;
    }

    public static ItemChildren toDomain(ItemChildrenMLDto dto) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        ItemChildren itemChildren = new ItemChildren();
        itemChildren.setItemId(dto.id);
        itemChildren.setStopTime(LocalDateTime.parse(dto.getStopTime(), inputFormatter));

        return itemChildren;
    }
}
