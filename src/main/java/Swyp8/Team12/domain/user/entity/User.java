package Swyp8.Team12.domain.user.entity;

import Swyp8.Team12.domain.ingredient.entity.Ingredient;
import Swyp8.Team12.domain.recipe.entity.Recipe;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "social_id", length = 30, unique = true)
    private String socialId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "nickname", length = 30, unique = true)
    private String nickname;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Builder(builderMethodName = "kakaoUserBuilder")
    public User(String socialId, String nickname, String email, String profileImg) {
        this.socialId = socialId;
        this.nickname = nickname;
        this.email = email;
        this.profileImg = profileImg;
    }
}
