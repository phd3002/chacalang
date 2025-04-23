package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "cart", schema = "thungcam_db", indexes = {
        @Index(name = "user_id", columnList = "user_id")
})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "session_id", length = 100)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private com.thungcam.chacalang.entity.User user;

//    @ColumnDefault("'ACTIVE'")
    @Lob
    @Column(name = "status")
    private String status;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "cart")
    private Set<com.thungcam.chacalang.entity.CartItem> cartItems = new LinkedHashSet<>();

}