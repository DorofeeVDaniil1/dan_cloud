package com.example.dan_cloud.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dan_cloud.data.IngredientRepository;
import com.example.dan_cloud.model.TacoOrder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import com.example.dan_cloud.model.Ingredient;
import com.example.dan_cloud.model.Ingredient.Type;
import com.example.dan_cloud.model.Taco;
@Slf4j
@Controller
@RequestMapping("/design")
//Хранение состояние собиираемого заказа
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }
// CТАРАЯ ВЕРСИЯ, БЕЗ БД
//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Гречская Тортилла", Type.WRAP),
//                new Ingredient("GREZ", "Грецкие орехи", Type.DETAIL),
//                new Ingredient("MINDAL", "Посыпка из миндальных орехов", Type.DETAIL),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );
//        Type[] types = Ingredient.Type.values();
//        //Фильтрация по типам
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType(ingredients, type));
//        }
//    }


    //Методы, аннотированные @ModelAttribute, выполняются перед каждым вызовом метода контроллера, если не указано иначе
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        log.info(ingredients.toString());
        Type[] types = Ingredient.Type.values();
        for (Type type: types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType((List<Ingredient>) ingredients,type));
        }
    }
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    @PostMapping
    //Чтобы выводить сведения об ошибках нужно указать Valid и Erorrs в передаваемых параметрах
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            //Выводим в терминал сведения об ошибках Именно Error
            log.error(errors.getFieldErrors().toString());
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
    private Iterable<Ingredient>  filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}