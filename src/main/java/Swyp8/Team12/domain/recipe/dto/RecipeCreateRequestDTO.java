package Swyp8.Team12.domain.recipe.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RecipeCreateRequestDTO {
    private List<String> ingredients;

    public RecipeCreateRequestDTO(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
