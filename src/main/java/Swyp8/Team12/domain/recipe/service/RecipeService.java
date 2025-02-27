package Swyp8.Team12.domain.recipe.service;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.recipe.dto.RecipeCreateResponseDTO;
import Swyp8.Team12.domain.recipe.dto.RecipeDetailResponseDTO;
import Swyp8.Team12.domain.recipe.entity.Recipe;
import Swyp8.Team12.domain.recipe.entity.RecipeIngredient;
import Swyp8.Team12.domain.recipe.repository.RecipeRepository;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final RecipeIngredientService recipeIngredientService;

    public void save(List<RecipeCreateResponseDTO> recipeList, Long userId) {
        User user = userService.getUserById(userId); // 사용자 정보 미리 가져오기

        for (RecipeCreateResponseDTO recipeDTO : recipeList) {
            Recipe recipe = Recipe.builder()
                    .user(user)
                    .recipesName(recipeDTO.getName())
                    .recipeContent(recipeDTO.getRecipe()) // 레시피 내용 추가
                    .isDelete(false) // 기본값 설정
                    .isScrap(false) // 기본값 설정
                    .build();
            recipeRepository.save(recipe); // RecipeRepository를 통해 저장

            List<RecipeIngredient> recipeIngredients = recipeDTO.getIngredient().stream()
                    .map(ingredientName -> RecipeIngredient.builder()
                            .name(ingredientName)
                            .recipe(recipe)
                            .build())
                    .collect(Collectors.toList());

            for (RecipeIngredient recipeIngredient : recipeIngredients) {
                recipeIngredientService.save(recipeIngredient);
            }
        }
    }

    public RecipeDetailResponseDTO findRecipe (Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다. id: " + recipeId));
        return new RecipeDetailResponseDTO(recipe);
    }

    public List<RecipeDetailResponseDTO> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeDetailResponseDTO::new)
                .collect(Collectors.toList());
    }


    public void scrapRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다. id: " + recipeId));
        recipe.scrap();
    }

    public void cancelScrap(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다. id: " + recipeId));
        recipe.cancelScrap();
    }

    public List<RecipeDetailResponseDTO> getScrapRecipes(Long userId) {
        List<Recipe> recipes = recipeRepository.findByIsScrapTrueAndUser_Id(userId);
        return recipes.stream()
                .map(RecipeDetailResponseDTO::new)
                .collect(Collectors.toList());
    }
}
