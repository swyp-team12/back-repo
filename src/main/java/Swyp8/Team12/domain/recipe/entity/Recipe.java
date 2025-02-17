package Swyp8.Team12.domain.recipe.entity;

import Swyp8.Team12.domain.user.entity.User;
import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipes_id")
    private int recipesId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "recipes_name", length = 50)
    private String recipesName;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_delete", columnDefinition = "varchar(1) default '0'")
    private String isDelete;

    @Column(name = "is_scrap")
    private String isScrap;

    @Column(name = "scrap_time")
    private Timestamp scrapTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

}
