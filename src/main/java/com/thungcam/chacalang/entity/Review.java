package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "reviews", schema = "thungcam_db", indexes = {
        @Index(name = "menu_id", columnList = "menu_id")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Size(max = 100)
    @Column(name = "reviewer_name", length = 100)
    private String reviewerName;

    @Size(max = 100)
    @Column(name = "reviewer_email", length = 100)
    private String reviewerEmail;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;

}