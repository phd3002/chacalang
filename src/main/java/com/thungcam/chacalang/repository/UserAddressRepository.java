package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllByUserOrderByIsDefaultDesc(User user);

    Optional<UserAddress> findByIdAndUser(Long id, User user);

    List<UserAddress> findAllByUserOrderByIsDefaultDescUpdatedAtDesc(User user);

    List<UserAddress> findAllByUserAndIsDefault(User user, boolean isDefault);

    List<UserAddress> findAllByUser(User user);

//    UserAddress findByUserAndIsDefaultTrue(User user, boolean isDefault);

}
