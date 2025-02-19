package Swyp8.Team12.domain.user.entity;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.recipe.entity.Recipe;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "social_id", length = 30, unique = true)
    private String socialId;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "user_nickname", length = 30, unique = true)
    private String userNickname;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;
}
