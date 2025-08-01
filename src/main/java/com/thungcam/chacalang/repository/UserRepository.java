package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role.id = :roleId")
    List<User> findByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT u FROM User u WHERE u.branch.id = :branchId AND u.role.id = :roleId")
    List<User> findByBranchIdAndRoleId(@Param("branchId") Long branchId, @Param("roleId") Long roleId);

    // Lấy user theo branch và roleId
    List<User> findByBranch_IdAndRole_Id(Long branchId, Long roleId);

    int countByBranchIdAndRole_Name(Long branchId, String roleCode);

}
