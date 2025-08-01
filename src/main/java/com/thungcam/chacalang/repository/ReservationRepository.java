package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBranch_Id(Long branchId);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.branch.id = :branchId AND r.reservationDate = :date")
    Long countByBranchIdAndDate(@Param("branchId") Long branchId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.branch.id = :branchId AND r.createdAt >= :fromDate AND r.createdAt <= :toDate")
    long countByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    long countByStatusAndBranchId(ReservationStatus status, Long branchId);

    long countByStatusInAndBranchId(Collection<ReservationStatus> status, Long branchId);

    @Query("SELECT r FROM Reservation r WHERE r.branch.id = :branchId AND r.reservationDate = :date")
    List<Reservation> findTodayReservation(@Param("branchId") Long branchId, @Param("date") LocalDate today);

}
