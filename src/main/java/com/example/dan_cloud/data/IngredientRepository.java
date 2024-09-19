package com.example.dan_cloud.data;

import com.example.dan_cloud.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    //используем iterable . так как это общий интерфейс от List, который нам возвращает query.
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
