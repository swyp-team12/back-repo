package Swyp8.Team12.domain.recipe.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipes_id")
    private int recipesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipes_name", length = 50, nullable = false)
    private String recipesName;

    @Column(name = "create_at", updatable = false)
    private Timestamp createAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete", columnDefinition = "TINYINT(1) default 0")
    private Boolean isDelete;

    @Column(name = "is_scrap", columnDefinition = "TINYINT(1) default 0")
    private Boolean isScrap;

    @Column(name = "scrap_time")
    private Timestamp scrapTime;
}
