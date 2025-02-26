package Swyp8.Team12.domain.recipe.controller;

import Swyp8.Team12.domain.recipe.dto.RecipeResponseDTO;
import Swyp8.Team12.domain.recipe.service.ClovaStudioService;
import Swyp8.Team12.global.common.objectstorage.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clova")
public class ClovaStudioController {

    private final ClovaStudioService clovaStudioService;
    private final ObjectStorageService objectStorageService;

    @GetMapping("/recipe")
    public List<RecipeResponseDTO> getRecipe(@RequestParam List<String> ingredients) {
        String userInput = String.join(", ", ingredients);
        return clovaStudioService.getRecipe(userInput);
    }

}
