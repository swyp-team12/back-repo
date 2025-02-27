package Swyp8.Team12.domain.recipe.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipes_id")
    private Long recipesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipes_name", length = 50, nullable = false)
    private String recipesName;

    @Column(name = "recipe_content", columnDefinition = "TEXT", nullable = false)
    private String recipeContent;  // 레시피 내용을 저장할 필드 추가

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "is_scrap")
    private Boolean isScrap;

    @Column(name = "scrap_time")
    private Timestamp scrapTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients;

    @Column(name = "create_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

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

    @Builder
    public Recipe(User user, String recipesName, String recipeContent, Boolean isDelete, Boolean isScrap) {
        this.user = user;
        this.recipesName = recipesName;
        this.recipeContent = recipeContent;
        this.isDelete = isDelete;
        this.isScrap = isScrap;
    }

    public void scrap() {
        this.isScrap = true;
    }

    public void cancelScrap() {
        this.isScrap = false;
    }


}
