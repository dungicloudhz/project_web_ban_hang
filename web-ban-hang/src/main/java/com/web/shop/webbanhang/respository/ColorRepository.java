package com.web.shop.webbanhang.respository;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Page<Color> findByColorNameContaining(String colorName, Pageable pageable);
}
