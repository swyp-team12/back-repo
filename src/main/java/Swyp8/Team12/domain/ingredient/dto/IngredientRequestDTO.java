package Swyp8.Team12.domain.ingredient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientRequestDTO {
    private String name;
    private String expiryDate;
    private String category;
    private String quantity;
    private Integer ingNum;
    private String userMemo;
    private String ingImage;
    private String storageType;

    public IngredientRequestDTO(String name, String expiryDate, String category, 
                               String quantity, Integer ingNum, String userMemo, 
                               String ingImage, String storageType) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = category;
        this.quantity = quantity;
        this.ingNum = ingNum;
        this.userMemo = userMemo;
        this.ingImage = ingImage;
        this.storageType = storageType;
    }
}