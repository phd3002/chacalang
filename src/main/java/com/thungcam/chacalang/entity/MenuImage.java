package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Table(name = "menu_images", schema = "thungcam_db", indexes = {
        @Index(name = "menu_id", columnList = "menu_id")
})
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_id")
    private com.thungcam.chacalang.entity.Menu menu;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;
}