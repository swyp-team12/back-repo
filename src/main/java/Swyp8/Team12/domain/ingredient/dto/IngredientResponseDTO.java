package Swyp8.Team12.domain.ingredient.dto;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class IngredientResponseDTO {
    private int ingId;
    private String name;
    private String expiryDate;
    private String category;
    private String quantity;
    private Integer ingNum;
    private String userMemo;
    private String ingImage;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String storageType;

    // 엔티티를 DTO로 변환하는 생성자
    public IngredientResponseDTO(Ingredient ingredient) {
        this.ingId = ingredient.getIngId();
        this.name = ingredient.getName();
        this.expiryDate = ingredient.getExpiryDate();
        this.category = ingredient.getCategory();
        this.quantity = ingredient.getQuantity();
        this.ingNum = ingredient.getIngNum();
        this.userMemo = ingredient.getUserMemo();
        this.ingImage = ingredient.getIngImage();
        this.createdAt = ingredient.getCreatedAt();
        this.updatedAt = ingredient.getUpdatedAt();
        this.storageType = ingredient.getStorageType();
    }

    @Override
    public String toString() {
        return "IngredientResponseDTO{" +
                "ingId=" + ingId +
                ", name='" + name + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", category='" + category + '\'' +
                ", quantity='" + quantity + '\'' +
                ", ingNum=" + ingNum +
                ", userMemo='" + userMemo + '\'' +
                ", ingImage='" + ingImage + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", storageType='" + storageType + '\'' +
                '}';
    }
}