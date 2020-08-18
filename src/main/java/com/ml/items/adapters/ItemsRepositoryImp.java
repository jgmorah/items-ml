package com.ml.items.adapters;

import com.ml.items.adapters.DTO.in.ItemChildrenMLDto;
import com.ml.items.adapters.DTO.in.ItemMLDto;
import com.ml.items.adapters.retrofit.MELIService;
import com.ml.items.adapters.retrofit.RetrofitClientBuilder;
import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ports.ItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ItemsRepositoryImp implements ItemsRepository {
    Logger logger = LoggerFactory.getLogger(ItemsRepositoryImp.class);
    MELIService serviceMELI;

    public ItemsRepositoryImp() {
        RetrofitClientBuilder retrofitClientBuilder = new RetrofitClientBuilder();
        this.serviceMELI = retrofitClientBuilder.getClient().create(MELIService.class);
    }

    @Override
    public Optional<Item> findLocalItemById(String ItemId) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> findExternalItemById(String ItemId) {

        Call<ItemMLDto> getItem = serviceMELI.getItem(ItemId);
        Call<List<ItemChildrenMLDto>> getChildrens = serviceMELI.getChildrens(ItemId);

        try {
            Response<List<ItemChildrenMLDto>> childrens = getChildrens.execute();


            return Optional.of(ItemMLDto.toDomain(getItem.execute().body()))
                    .map((item) -> {
                                item.setChildren(childrens.body()
                                        .stream()
                                        .map(ItemChildrenMLDto::toDomain)
                                        .collect(Collectors.toList()));
                                return item;
                            }
                    );

        } catch (Exception ignored) {
            logger.error("error trying to get item from external resource, itemId {}", ItemId);
        }
        return Optional.empty();
    }

    @Override
    public Item save(Item item) {
        return null;
    }


}
