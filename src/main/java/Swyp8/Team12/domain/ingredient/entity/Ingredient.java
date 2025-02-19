package Swyp8.Team12.domain.ingredient.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ing_id")
    private int ingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "ing_num")
    private Integer ingNum;

    @Column(name = "user_memo")
    private String userMemo;

    @Column(name = "ing_image")
    private String ingImage;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete;
}
