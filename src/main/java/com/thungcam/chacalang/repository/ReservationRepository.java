package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBranch_Id(Long branchId);
}
