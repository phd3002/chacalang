package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.User;

public interface ReviewService {
    void createReview(User user, Long orderId, int rating, String comment);
}
