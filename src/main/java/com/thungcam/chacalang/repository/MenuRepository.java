package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m FROM Menu m WHERE " +
            "(:categoryId IS NULL OR m.category.id = :categoryId) AND " +
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.price <= :maxPrice)")
    List<Menu> filterMenu(@Param("categoryId") Long categoryId,
                          @Param("minPrice") Double minPrice,
                          @Param("maxPrice") Double maxPrice);

}
