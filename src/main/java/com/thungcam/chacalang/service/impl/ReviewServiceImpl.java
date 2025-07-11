package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.Review;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.repository.ReviewRepository;
import com.thungcam.chacalang.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;

    private final ReviewRepository reviewRepository;

    public void createReview(User user, Long orderId, int rating, String comment) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        if (reviewRepository.existsByOrder_Id(orderId)) {
            throw new RuntimeException("Đơn này đã được đánh giá!");
        }

        Review review = new Review();
        review.setOrder(order); // Gán order vào review
        review.setReviewerName(user.getFullName());
        review.setReviewerEmail(user.getEmail());
        review.setRating(rating);
        review.setContent(comment);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        if (order.getStatus() != OrderStatus.COMPLETED) {
            order.setStatus(OrderStatus.COMPLETED);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        }
    }

}
