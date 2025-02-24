package Swyp8.Team12.domain.recipe.controller;

import Swyp8.Team12.domain.recipe.dto.RecipeResponseDTO;
import Swyp8.Team12.domain.recipe.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clova")
public class ClovaStudioController {

    private final ClovaStudioService clovaStudioService;

    @GetMapping("/recipe")
    public List<RecipeResponseDTO> getRecipe(@RequestParam List<String> ingredients) {
        String userInput = String.join(", ", ingredients);
        return clovaStudioService.getRecipe(userInput);
    }
}
