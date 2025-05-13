package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllByUserOrderByIsDefaultDesc(User user);

    Optional<UserAddress> findByIdAndUser(Long id, User user);

    List<UserAddress> findAllByUserOrderByIsDefaultDescUpdatedAtDesc(User user);
}
