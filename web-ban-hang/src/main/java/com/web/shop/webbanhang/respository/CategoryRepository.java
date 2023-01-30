package com.web.shop.webbanhang.respository;

import com.web.shop.webbanhang.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
