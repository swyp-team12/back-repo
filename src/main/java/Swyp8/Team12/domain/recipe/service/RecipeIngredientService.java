package Swyp8.Team12.domain.recipe.service;

import Swyp8.Team12.domain.recipe.entity.RecipeIngredient;
import Swyp8.Team12.domain.recipe.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeIngredientService {

    public final RecipeIngredientRepository recipeIngredientRepository;

    public void save(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.save(recipeIngredient);
    }
}
