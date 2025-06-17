package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation save(Reservation reservation);

    Reservation saveChange(Reservation reservation);

    List<Reservation> getAll();

    List<Reservation> getByBranchId(Long branchId);

    Reservation getById(Long id);
}
