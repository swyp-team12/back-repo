package Swyp8.Team12.domain.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCreateResponseDTO {
    private String name;
    private List<String> ingredient;
    private String recipe;

    public RecipeCreateResponseDTO(String name, List<String> ingredient, String recipe) {
        this.name = name;
        this.ingredient = ingredient;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "name='" + name + '\'' +
                ", ingredient=" + ingredient +
                ", recipe='" + recipe + '\'' +
                '}';
    }
}