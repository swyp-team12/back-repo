package Swyp8.Team12.domain.recipe.dto;

import Swyp8.Team12.domain.recipe.entity.RecipeIngredient;
import lombok.Getter;

@Getter
public class RecipeIngredientDTO {

    private String recipeIngredientName;

    public RecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        recipeIngredientName = recipeIngredient.getName();
    }
}
