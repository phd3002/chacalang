package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByBranch_IdOrderByCreatedAtDesc(Long branchId);

    List<Contact> findAllByOrderByCreatedAtDesc();
}

