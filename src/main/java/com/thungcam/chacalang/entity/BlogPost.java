package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "blog_posts", schema = "thungcam_db", uniqueConstraints = {
        @UniqueConstraint(name = "slug", columnNames = {"slug"})
})
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 200)
    @Column(name = "slug", length = 200)
    private String slug;

    @Lob
    @Column(name = "content")
    private String content;

    @Size(max = 255)
    @Column(name = "thumbnail")
    private String thumbnail;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}