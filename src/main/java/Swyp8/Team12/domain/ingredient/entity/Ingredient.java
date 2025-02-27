package Swyp8.Team12.domain.ingredient.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "Ingredients", indexes = {
    @Index(name = "idx_expiry_date", columnList = "expiry_date"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_storage_type", columnList = "storage_type")
})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ing_id")
    private Long ingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "expiry_date", length = 8)
    private String expiryDate;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "ing_num")
    private Integer ingNum;

    @Column(name = "user_memo", length = 500)
    private String userMemo;

    @Column(name = "ing_image")
    private String ingImage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "storage_type", length = 10)
    private String storageType; // "냉장" 또는 "냉동" 값을 저장

    @Builder
    public Ingredient(User user, String name, String expiryDate, String category, 
                     String quantity, Integer ingNum, String userMemo, String ingImage,
                     String storageType) {
        this.user = user;
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = category;
        this.quantity = quantity;
        this.ingNum = ingNum;
        this.userMemo = userMemo;
        this.ingImage = ingImage;
        this.storageType = storageType;
    }

    // 업데이트 메서드
    public void update(String name, String expiryDate, String category, 
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

    // 삭제 메서드
    public void delete() {
        this.isDelete = true;
    }
}
