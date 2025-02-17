package Swyp8.Team12.domain.user.entity;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.recipe.entity.Recipe;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "social_id", length = 30)
    private String socialId;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "user_nickname", length = 30)
    private String userNickname;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete", columnDefinition = "varchar(1) default '0'")
    private String isDelete;

    @OneToMany(mappedBy = "user")
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

}
