package Swyp8.Team12.domain.recipe.dto;

import Swyp8.Team12.domain.recipe.entity.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RecipeDetailResponseDTO {
    private Long recipesId;
    private Long userId;
    private String recipesName;
    private String recipeContent;
    private Boolean isDelete;
    private Boolean isScrap;
    private Timestamp scrapTime;
    private List<RecipeIngredientDTO> recipeIngredients;
    private Timestamp createAt;
    private Timestamp updatedAt;

    public RecipeDetailResponseDTO(Recipe recipe) {
        this.recipesId = recipe.getRecipesId();
        this.userId = recipe.getUser().getId();
        this.recipesName = recipe.getRecipesName();
        this.recipeContent = recipe.getRecipeContent();
        this.isDelete = recipe.getIsDelete();
        this.isScrap = recipe.getIsScrap();
        this.scrapTime = recipe.getScrapTime();
        this.recipeIngredients = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeIngredientDTO::new)
                .collect(Collectors.toList());
        this.createAt = recipe.getCreatedAt();
        this.updatedAt = recipe.getUpdatedAt();
    }
}
