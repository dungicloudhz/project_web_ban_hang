package com.web.shop.webbanhang.wishlish;

import com.web.shop.webbanhang.entity.Wishlish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlish, Long> {
    public List<Wishlish> findByUser_UserId(Long userId);

    public Wishlish findByUser_UserIdAndProductDetail_ProductDetailId(Long userId,Long productDetailId);

    @Query("DELETE FROM Wishlish w WHERE w.user.userId = ?1 AND w.productDetail.productDetailId = ?2")
    @Modifying
    public void deleteByUser_UserIdAndProductDetail_ProductDetailId(Long userId,Long productDetailId);
}
