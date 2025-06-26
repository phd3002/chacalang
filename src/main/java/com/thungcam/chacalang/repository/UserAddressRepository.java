package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    @EntityGraph(attributePaths = {"district", "ward"})
    List<UserAddress> findAllByUserOrderByIsDefaultDesc(User user);

    @Query("SELECT a FROM UserAddress a " +
            "LEFT JOIN FETCH a.district " +
            "LEFT JOIN FETCH a.ward " +
            "WHERE a.id = :id AND a.user = :user")
    Optional<UserAddress> findByIdAndUser(@Param("id") Long id, @Param("user") User user);


    List<UserAddress> findAllByUserOrderByIsDefaultDescUpdatedAtDesc(User user);

    List<UserAddress> findAllByUserAndIsDefault(User user, boolean isDefault);

    List<UserAddress> findAllByUser(User user);

//    UserAddress findByUserAndIsDefaultTrue(User user, boolean isDefault);

}
