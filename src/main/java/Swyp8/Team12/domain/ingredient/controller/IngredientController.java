package Swyp8.Team12.domain.ingredient.controller;

import Swyp8.Team12.domain.ingredient.dto.IngredientRequestDTO;
import Swyp8.Team12.domain.ingredient.dto.IngredientResponseDTO;
import Swyp8.Team12.domain.ingredient.service.IngredientService;
import Swyp8.Team12.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * 사용자의 모든 재료 목록 조회
     * 인증된 사용자의 ID를 기반으로 재료 목록을 반환합니다.
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getIngredientList(
            @AuthenticationPrincipal Long userId) {
        List<IngredientResponseDTO> ingredients = ingredientService.getIngredientsByUserId(userId);
        return ResponseEntity.ok()
                .body(ApiResponse.successResponse(ingredients));
    }

    /**
     * 특정 재료 상세 정보 조회
     * 재료 ID를 기반으로 특정 재료의 상세 정보를 반환합니다.
     */
    @GetMapping("/{ingId}")
    public ResponseEntity<ApiResponse<?>> getIngredientDetail(
            @PathVariable int ingId,
            @AuthenticationPrincipal Long userId) {
        IngredientResponseDTO ingredient = ingredientService.getIngredientById(ingId, userId);
        return ResponseEntity.ok()
                .body(ApiResponse.successResponse(ingredient));
    }

    /**
     *
     * 새로운 재료 추가
     *
     */
    @PostMapping
    public ResponseEntity<ApiResponse<?>> addIngredient(
            @RequestBody IngredientRequestDTO requestDTO,
            @AuthenticationPrincipal Long userId) {
        log.info("서비스 시작전");
        IngredientResponseDTO savedIngredient = ingredientService.addIngredient(requestDTO, userId);
        log.info("서비스 종료");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successWithDataAndMessage(savedIngredient, "재료 추가 성공"));
    }

    /**
     * 기존 재료 정보 수정
     */
    @PutMapping("/{ingId}")
    public ResponseEntity<ApiResponse<?>> updateIngredient(
            @PathVariable int ingId,
            @RequestBody IngredientRequestDTO requestDTO,
            @AuthenticationPrincipal Long userId) {
        IngredientResponseDTO updatedIngredient = ingredientService.updateIngredient(ingId, requestDTO, userId);
        return ResponseEntity.ok()
                .body(ApiResponse.successWithDataAndMessage(updatedIngredient, "재료 정보 수정 성공"));
    }

    /**
     * 재료 삭제 (soft delete)
     */
    @DeleteMapping("/{ingId}")
    public ResponseEntity<ApiResponse<?>> deleteIngredient(
            @PathVariable int ingId,
            @AuthenticationPrincipal Long userId) {
        ingredientService.deleteIngredient(ingId, userId);
        return ResponseEntity.ok()
                .body(ApiResponse.successWithMessage("재료 삭제 성공"));
    }
}