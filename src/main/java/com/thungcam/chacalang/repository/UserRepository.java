package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    User findByEmail(String email);

    User findByUsername(String username);

}
