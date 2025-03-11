package Swyp8.Team12.domain.recipe.controller;

import Swyp8.Team12.domain.recipe.dto.RecipeCreateRequestDTO;
import Swyp8.Team12.domain.recipe.dto.RecipeCreateResponseDTO;
import Swyp8.Team12.domain.recipe.dto.RecipeDetailResponseDTO;
import Swyp8.Team12.domain.recipe.service.ClovaStudioService;
import Swyp8.Team12.domain.recipe.service.RecipeService;
import Swyp8.Team12.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {

    private final ClovaStudioService clovaStudioService;
    private final RecipeService recipeService;

    /**
     *
     * 레시피 생성 & 저장
     *
     */
    @PostMapping("/clova")
    public ResponseEntity<ApiResponse<List<RecipeCreateResponseDTO>>> getRecipe(@AuthenticationPrincipal Long userId,
                                                                                @RequestBody RecipeCreateRequestDTO recipeCreateRequestDTO) {
        String userInput = String.join(", ", recipeCreateRequestDTO.getIngredients());
        return ResponseEntity.ok().body(ApiResponse.successResponse(clovaStudioService.getRecipe(userInput, userId)));
    }

    /**
     *
     * 레시피 단건 조회
     *
     */
    @GetMapping("/{recipeId}")
    public ResponseEntity<ApiResponse<RecipeDetailResponseDTO>> findRecipe(@PathVariable Long recipeId) {
        RecipeDetailResponseDTO recipeDetail = recipeService.findRecipe(recipeId);
        return ResponseEntity.ok().body(ApiResponse.successResponse(recipeDetail));
    }


    /**
     *
     * 레시피 리스트 조회
     *
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RecipeDetailResponseDTO>>> findAllRecipes(@AuthenticationPrincipal Long userId) {
        List<RecipeDetailResponseDTO> recipes = recipeService.findAllRecipes(userId);
        return ResponseEntity.ok().body(ApiResponse.successResponse(recipes));
    }

    /**
     *
     * 레시피 스크랩 API
     *
     */
    @PostMapping("/{recipeId}/scrap")
    public ResponseEntity<ApiResponse<?>> scrapRecipe(@PathVariable Long recipeId, @AuthenticationPrincipal Long userId) {
        recipeService.scrapRecipe(recipeId);
        return ResponseEntity.ok().body(ApiResponse.successWithMessage("스크랩 성공"));
    }

    /**
     *
     * 레시피 스크랩 취소 API
     *
     */
    @DeleteMapping("/{recipeId}/scrap")
    public ResponseEntity<ApiResponse<?>> cancelScrap(@PathVariable Long recipeId,@AuthenticationPrincipal Long userId) {
        recipeService.cancelScrap(recipeId);
        return ResponseEntity.ok().body(ApiResponse.successWithMessage("스크랩 취소 성공"));
    }

    /**
     *
     * 스크랩 리스트 조회 API
     *
     */
    @GetMapping("/scrap")
    public ResponseEntity<ApiResponse<List<RecipeDetailResponseDTO>>> getScrapRecipes(@AuthenticationPrincipal Long userId) {
        List<RecipeDetailResponseDTO> scrapRecipes = recipeService.getScrapRecipes(userId);
        return ResponseEntity.ok().body(ApiResponse.successResponse(scrapRecipes));
    }

}
