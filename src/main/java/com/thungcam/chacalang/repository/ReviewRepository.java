package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByOrder_Id(Long orderId);
}

