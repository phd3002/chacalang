package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation save(Reservation reservation);

    void saveChange(Reservation reservation);

    List<Reservation> getAll();

    List<Reservation> getByBranchId(Long branchId);

    Reservation getById(Long id);

    List<Reservation> getReservationsByBranch(Long branchId);

    Reservation updateStatus(Long id, String status);

    void delete(Long id);
}
