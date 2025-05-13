package com.thungcam.chacalang.entity;

import com.thungcam.chacalang.enums.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "reservations", schema = "thungcam_db", indexes = {
        @Index(name = "branch_id", columnList = "branch_id")
})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Size(max = 100)
    @NotNull
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @NotNull
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @NotNull
    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;

    @NotNull
    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

//    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Lob
    @Column(name = "note")
    private String note;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}