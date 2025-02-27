package Swyp8.Team12.domain.ingredient.repository;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    
    // 사용자 ID로 모든 재료 조회 (삭제되지 않은 것만)
    List<Ingredient> findByUserAndIsDeleteFalse(User user);
    
    // 재료 ID와 사용자 ID로 특정 재료 조회 (삭제되지 않은 것만)
    Optional<Ingredient> findByIngIdAndUserAndIsDeleteFalse(int ingId, User user);
    
    // 유통기한이 임박한 재료 조회
    List<Ingredient> findByUserAndExpiryDateLessThanEqualAndIsDeleteFalse(User user, String expiryDate);
    
    // 카테고리별 재료 조회
    List<Ingredient> findByUserAndCategoryAndIsDeleteFalse(User user, String category);
    
    // 보관 타입별 재료 조회 (냉장/냉동)
    List<Ingredient> findByUserAndStorageTypeAndIsDeleteFalse(User user, String storageType);
}