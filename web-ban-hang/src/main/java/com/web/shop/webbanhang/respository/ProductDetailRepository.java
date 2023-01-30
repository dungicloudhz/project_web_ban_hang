package com.web.shop.webbanhang.respository;

import com.web.shop.webbanhang.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findByProductDetailNameContaining(String productDetailName,Pageable pageable);
    Page<ProductDetail> findByProduct_ProductId(Long productId, Pageable pageable);
    Page<ProductDetail> findByOutstanding(Boolean outstanding, Pageable pageable);
    Page<ProductDetail> findByMostRecent(Boolean mostRecent, Pageable pageable);
    Page<ProductDetail> findByBestseller(Boolean bestseller, Pageable pageable);
    Page<ProductDetail> findByDiscountCurrent(Boolean discountCurrent, Pageable pageable);
    List<ProductDetail> findByOutstanding(Boolean outstanding);
    List<ProductDetail> findByMostRecent(Boolean mostRecent);
    List<ProductDetail> findByBestseller(Boolean bestseller);
    List<ProductDetail> findByDiscountCurrent(Boolean discountCurrent);
    List<ProductDetail> findByProduct_ProductId(Long productId);
    List<ProductDetail> findProductDetailByProduct_ProductIdAndMemoryStorage_MemoryStorageId(Long productId,Long memoryStorageId);
    Page<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId,Pageable pageable);
    List<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId);
}
