package Swyp8.Team12.domain.recipe.repository;

import Swyp8.Team12.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByIsScrapTrueAndUser_Id(Long userId);
}