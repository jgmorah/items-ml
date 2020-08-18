package com.ml.items.adapters.retrofit;

import com.ml.items.adapters.DTO.in.ItemChildrenMLDto;
import com.ml.items.adapters.DTO.in.ItemMLDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface MELIService {
    @GET("items/{itemId}")
    Call<ItemMLDto> getItem(@Path("itemId") String itemId);

    @GET("items/{itemId}/children")
    Call<List<ItemChildrenMLDto>> getChildrens(@Path("itemId") String itemId);
}
