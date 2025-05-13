package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "menu", schema = "thungcam_db", indexes = {
        @Index(name = "category_id", columnList = "category_id")
})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "weight")
    private Integer weight;

//    @ColumnDefault("0")
//    @Column(name = "stock")
//    private Integer stock;

    @Size(max = 255)
    @Column(name = "main_image")
    private String mainImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "menu")
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "menu")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "menu")
    private Set<MenuImage> menuImages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "menu")
    private Set<com.thungcam.chacalang.entity.Review> reviews = new LinkedHashSet<>();

//    @Column(name = "is_available")
//    private Boolean isAvailable = true;
}