package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.repository.ReservationRepository;
import com.thungcam.chacalang.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getByBranchId(Long branchId) {
        return reservationRepository.findByBranch_Id(branchId);
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

}
