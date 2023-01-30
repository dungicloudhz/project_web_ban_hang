package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser_UserId(Long userId, Pageable pageable);

    Page<Order> findByOrderCodeContaining(String orderCode,Pageable pageable);
}
