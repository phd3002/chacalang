package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "branches", schema = "thungcam_db")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

//    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "branch")
    private Set<com.thungcam.chacalang.entity.Reservation> reservations = new LinkedHashSet<>();

}