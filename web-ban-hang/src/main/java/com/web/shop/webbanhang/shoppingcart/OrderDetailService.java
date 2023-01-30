package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.Order;
import com.web.shop.webbanhang.entity.OrderDetail;
import com.web.shop.webbanhang.entity.ProductDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderDetailService {

    List<OrderDetail> findByOrder_OrderId(Long orderId);

    @Modifying
    @Query("UPDATE OrderDetail c SET c.temporaryId = ?1 WHERE c.order.orderId = ?2")
    void updateTemporaryId(Long temporaryId,Long orderId);

    @Modifying
    @Query("UPDATE OrderDetail c SET c.order.orderId = ?1 WHERE c.temporaryId = ?2")
    void updateOrder(Long orderId, Long temporaryId);

    List<OrderDetail> findByTemporaryId(Long temporaryId);

    OrderDetail findByTemporaryIdAndProductDetail(Long temporaryId, ProductDetail productDetails);

    @Modifying
    @Query("UPDATE OrderDetail c SET c.quantity = ?1 WHERE c.productDetail.productDetailId = ?2 AND c.temporaryId = ?3")
    void updateQuantity(Integer quantity, Long productDetailId, Long temporaryId);

    @Modifying
    @Query("DELETE FROM OrderDetail c WHERE c.temporaryId = ?1 AND c.productDetail.productDetailId = ?2")
    void deleteByOrderAndProduct(Long temporaryId, Long productDetailId);

    List<OrderDetail> findAll();

    List<OrderDetail> findAll(Sort sort);

    List<OrderDetail> findAllById(Iterable<Long> longs);

    <S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends OrderDetail> S saveAndFlush(S entity);

    <S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<OrderDetail> entities);

    void deleteAllInBatch(Iterable<OrderDetail> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    OrderDetail getOne(Long aLong);

    @Deprecated
    OrderDetail getById(Long aLong);

    OrderDetail getReferenceById(Long aLong);

    <S extends OrderDetail> List<S> findAll(Example<S> example);

    <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort);

    Page<OrderDetail> findAll(Pageable pageable);

    <S extends OrderDetail> S save(S entity);

    Optional<OrderDetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(OrderDetail entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends OrderDetail> entities);

    void deleteAll();

    <S extends OrderDetail> Optional<S> findOne(Example<S> example);

    <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends OrderDetail> long count(Example<S> example);

    <S extends OrderDetail> boolean exists(Example<S> example);

    <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    List<OrderDetail> listOrderDetails(Long temporaryId);

    Integer addProduct(Long productDatailId, Integer quantity, Long temporaryId);

    float updateQuantity(Long productId, Integer quantity, Long temporaryId);

    void removeProduct(Long productId, Long temporaryId);
}
