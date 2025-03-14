package Swyp8.Team12.domain.ingredient.service;

import Swyp8.Team12.domain.ingredient.dto.IngredientRequestDTO;
import Swyp8.Team12.domain.ingredient.dto.IngredientResponseDTO;
import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.ingredient.repository.IngredientRepository;
import Swyp8.Team12.domain.user.entity.User;
import Swyp8.Team12.domain.user.repository.UserRepository;
import Swyp8.Team12.global.service.S3FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;

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
     * 새로운 재료 추가 (Base64 이미지만 사용)
     */
    @Transactional
    public IngredientResponseDTO addIngredient(IngredientRequestDTO requestDTO, Long userId) throws IOException {
        User user = getUserById(userId);
        
        // Base64 이미지 처리
        String imageUrl = null;
        if (requestDTO.getIngImage() != null && !requestDTO.getIngImage().isEmpty()) {
            imageUrl = s3FileService.uploadBase64Image(requestDTO.getIngImage());
        }
        
        Ingredient ingredient = Ingredient.builder()
                .user(user)
                .name(requestDTO.getName())
                .expiryDate(requestDTO.getExpiryDate())
                .category(requestDTO.getCategory())
                .quantity(requestDTO.getQuantity())
                .ingNum(requestDTO.getIngNum())
                .userMemo(requestDTO.getUserMemo())
                .ingImage(imageUrl) // S3에 업로드된 이미지 URL 저장
                .storageType(requestDTO.getStorageType())
                .build();
        
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return new IngredientResponseDTO(savedIngredient);
    }

    /**
     * 기존 재료 정보 수정 (Base64 이미지만 사용)
     */
    @Transactional
    public IngredientResponseDTO updateIngredient(int ingId, IngredientRequestDTO requestDTO, Long userId) throws IOException {
        User user = getUserById(userId);
        Ingredient ingredient = ingredientRepository.findByIngIdAndUserAndIsDeleteFalse(ingId, user)
                .orElseThrow(() -> new EntityNotFoundException("재료를 찾을 수 없습니다."));
        
        // 이미지 처리
        String imageUrl = ingredient.getIngImage(); // 기존 이미지 URL 유지
        
        // Base64 인코딩된 이미지가 있는 경우 업데이트
        if (requestDTO.getIngImage() != null && !requestDTO.getIngImage().isEmpty()) {
            imageUrl = s3FileService.uploadBase64Image(requestDTO.getIngImage());
        }
        
        // 재료 정보 업데이트
        ingredient.update(
                requestDTO.getName(),
                requestDTO.getExpiryDate(),
                requestDTO.getCategory(),
                requestDTO.getQuantity(),
                requestDTO.getIngNum(),
                requestDTO.getUserMemo(),
                imageUrl,
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