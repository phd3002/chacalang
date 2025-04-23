package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "categories", schema = "thungcam_db", uniqueConstraints = {
        @UniqueConstraint(name = "slug", columnNames = {"slug"})
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "slug", length = 100)
    private String slug;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "category")
    private Set<com.thungcam.chacalang.entity.Menu> menus = new LinkedHashSet<>();

}