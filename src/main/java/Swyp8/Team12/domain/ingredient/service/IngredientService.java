package Swyp8.Team12.domain.ingredient.service;

import Swyp8.Team12.domain.ingredient.dto.IngredientRequestDTO;
import Swyp8.Team12.domain.ingredient.dto.IngredientResponseDTO;
import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.ingredient.repository.IngredientRepository;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 ID로 모든 재료 목록 조회
     */
    public List<IngredientResponseDTO> getIngredientsByUserId(Long userId) {
        User user = getUserById(userId);
        List<Ingredient> ingredients = ingredientRepository.findByUserAndIsDeleteFalse(user);
        return ingredients.stream()
                .map(IngredientResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 재료 ID로 특정 재료 상세 정보 조회
     */
    public IngredientResponseDTO getIngredientById(int ingId, Long userId) {
        User user = getUserById(userId);
        Ingredient ingredient = ingredientRepository.findByIngIdAndUserAndIsDeleteFalse(ingId, user)
                .orElseThrow(() -> new EntityNotFoundException("재료를 찾을 수 없습니다."));
        
        return new IngredientResponseDTO(ingredient);
    }

    /**
     * 새로운 재료 추가
     */
    @Transactional
    public IngredientResponseDTO addIngredient(IngredientRequestDTO requestDTO, Long userId) {
        User user = getUserById(userId);
        
        Ingredient ingredient = Ingredient.builder()
                .user(user)
                .name(requestDTO.getName())
                .expiryDate(requestDTO.getExpiryDate())
                .category(requestDTO.getCategory())
                .quantity(requestDTO.getQuantity())
                .ingNum(requestDTO.getIngNum())
                .userMemo(requestDTO.getUserMemo())
                .ingImage(requestDTO.getIngImage())
                .storageType(requestDTO.getStorageType())
                .build();
        
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return new IngredientResponseDTO(savedIngredient);
    }

    /**
     * 기존 재료 정보 수정
     */
    @Transactional
    public IngredientResponseDTO updateIngredient(int ingId, IngredientRequestDTO requestDTO, Long userId) {
        User user = getUserById(userId);
        Ingredient ingredient = ingredientRepository.findByIngIdAndUserAndIsDeleteFalse(ingId, user)
                .orElseThrow(() -> new EntityNotFoundException("재료를 찾을 수 없습니다."));
        
        ingredient.update(
                requestDTO.getName(),
                requestDTO.getExpiryDate(),
                requestDTO.getCategory(),
                requestDTO.getQuantity(),
                requestDTO.getIngNum(),
                requestDTO.getUserMemo(),
                requestDTO.getIngImage(),
                requestDTO.getStorageType()
        );
        
        return new IngredientResponseDTO(ingredient);
    }

    /**
     * 재료 삭제 (soft delete)
     */
    @Transactional
    public void deleteIngredient(int ingId, Long userId) {
        User user = getUserById(userId);
        Ingredient ingredient = ingredientRepository.findByIngIdAndUserAndIsDeleteFalse(ingId, user)
                .orElseThrow(() -> new EntityNotFoundException("재료를 찾을 수 없습니다."));
        
        ingredient.delete();
    }

    /**
     * 사용자 ID로 사용자 엔티티 조회
     */
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
    }
}