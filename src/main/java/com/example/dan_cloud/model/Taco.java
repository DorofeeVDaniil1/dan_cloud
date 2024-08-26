package com.example.dan_cloud.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Taco {
    @NonNull
    @Size(min = 5,message = "Имени нужно минимум 5 букв")
    private String name;

    @NonNull
    @Size(min = 1,message = "Нужен минимум 1 ингридиент")
    private List<Ingredient> ingredients;

    public Taco() {

    }
}
