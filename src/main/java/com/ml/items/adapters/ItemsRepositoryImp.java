package com.ml.items.adapters;

import com.google.gson.Gson;
import com.ml.items.adapters.DTO.in.ItemChildrenMLDto;
import com.ml.items.adapters.DTO.in.ItemMLDto;
import com.ml.items.adapters.retrofit.MELIService;
import com.ml.items.adapters.retrofit.RetrofitClientBuilder;
import com.ml.items.domain.items.Item;
import com.ml.items.domain.items.ItemChildren;
import com.ml.items.domain.items.ports.ItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemsRepositoryImp implements ItemsRepository {
    private static final String INSERT_ITEM = "INSERT INTO items VALUES (?,?::json);";
    private static final String SELECT_ITEM = "SELECT details FROM items WHERE id = ?;";

    Logger logger = LoggerFactory.getLogger(ItemsRepositoryImp.class);

    private MELIService serviceMELI;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Gson gson;

    public ItemsRepositoryImp() {
        this.serviceMELI = new RetrofitClientBuilder().getClient().create(MELIService.class);

        this.gson = new Gson();
    }

    @Override
    public Optional<Item> findLocalItemById(String ItemId) {
        try {
            String details = jdbcTemplate.queryForObject(SELECT_ITEM
                    , new Object[]{ItemId}, String.class);
            return Optional.of(gson.fromJson(details, Item.class));
        } catch (DataAccessException ex) {
            logger.warn("no item in local db, itemId {}", ItemId);
            return Optional.<Item>empty();
        }

    }

    @Override
    public Optional<Item> findExternalItemById(String ItemId) {
        Call<ItemMLDto> getItem = serviceMELI.getItem(ItemId);
        try {
            return Optional.of(ItemMLDto.toDomain(getItem.execute().body()))
                    .map((item) -> {
                        item.setChildren(getChildren(ItemId));
                        return item;
                    });
        } catch (Exception ignored) {
            logger.error("error trying to get item from external resource, itemId {}", ItemId);
        }
        return Optional.empty();
    }

    private List<ItemChildren> getChildren(String ItemId) {
        Call<List<ItemChildrenMLDto>> getChildren = serviceMELI.getChildrens(ItemId);
        try {
            return getChildren.execute().body()
                    .stream()
                    .map(ItemChildrenMLDto::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
            logger.error("error trying to get children from external resource, itemId {}", ItemId);
        }
        return new ArrayList<>();
    }

    @Override
    public void save(Item item) {

        try {
            jdbcTemplate.update(INSERT_ITEM, item.getItemId(), gson.toJson(item));
        } catch (Exception e) {
            logger.error("error saving local item  {}", item.getItemId());
        }
    }


}
