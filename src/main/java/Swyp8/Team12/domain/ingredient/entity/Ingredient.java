package Swyp8.Team12.domain.ingredient.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ing_id")
    private int ingId;

    @Column(name = "user_id2")
    private int userId2;

    @Column(name = "name")
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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete", columnDefinition = "varchar(1) default '0'")
    private String isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id2", insertable = false, updatable = false)
    private User user;

}
