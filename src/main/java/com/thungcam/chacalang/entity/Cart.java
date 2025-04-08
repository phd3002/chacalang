package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public com.thungcam.chacalang.entity.User getUser() {
        return user;
    }

    public void setUser(com.thungcam.chacalang.entity.User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<com.thungcam.chacalang.entity.CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<com.thungcam.chacalang.entity.CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}