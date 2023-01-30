package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.Order;
import com.web.shop.webbanhang.entity.OrderDetail;
import com.web.shop.webbanhang.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    public List<OrderDetail> findByTemporaryId(Long temporaryId);

    public OrderDetail findByTemporaryIdAndProductDetail(Long temporaryId, ProductDetail productDetails);

    public List<OrderDetail> findByOrder_OrderId(Long orderId);

    @Query("UPDATE OrderDetail c SET c.quantity = ?1 WHERE c.productDetail.productDetailId = ?2 AND c.temporaryId = ?3")
    @Modifying
    public void updateQuantity(Integer quantity,Long productDetailId,Long temporaryId);

    @Query("UPDATE OrderDetail c SET c.order.orderId = ?1 WHERE c.temporaryId = ?2")
    @Modifying
    public void updateOrder(Long orderId,Long temporaryId);

    @Query("UPDATE OrderDetail c SET c.temporaryId = ?1 WHERE c.order.orderId = ?2")
    @Modifying
    public void updateTemporaryId(Long temporaryId,Long orderId);

    @Query("DELETE FROM OrderDetail c WHERE c.temporaryId = ?1 AND c.productDetail.productDetailId = ?2")
    @Modifying
    public void deleteByOrderAndProduct(Long temporaryId,Long productDetailId);
}
