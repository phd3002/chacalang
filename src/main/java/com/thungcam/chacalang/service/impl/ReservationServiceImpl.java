package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.enums.ReservationStatus;
import com.thungcam.chacalang.repository.ReservationRepository;
import com.thungcam.chacalang.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        reservation.setStatus(ReservationStatus.PENDING);
        return reservationRepository.save(reservation);
    }

    @Override
    public void saveChange(Reservation reservation) {
        reservationRepository.save(reservation);
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

    @Override
    public List<Reservation> getReservationsByBranch(Long branchId) {
        return reservationRepository.findByBranch_Id(branchId);
    }

    @Override
    public Reservation updateStatus(Long id, String status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservation.setStatus(ReservationStatus.valueOf(status));
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

}
