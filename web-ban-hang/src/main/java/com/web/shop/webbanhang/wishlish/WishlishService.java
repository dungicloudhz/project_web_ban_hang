package com.web.shop.webbanhang.wishlish;

import com.web.shop.webbanhang.entity.Wishlish;
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

public interface WishlishService {
    Wishlish findByUser_UserIdAndProductDetail_ProductDetailId(Long userId, Long productDetailId);

    Integer addProduct(Long productDatailId, Long userId);

    List<Wishlish> findByUser_UserId(Long userId);

    @Modifying
    @Query("DELETE FROM Wishlish w WHERE w.user.userId = ?1 AND w.productDetail.productDetailId = ?2")
    void deleteByUser_UserIdAndProductDetail_ProductDetailId(Long userId, Long productDetailId);

    List<Wishlish> findAll();

    List<Wishlish> findAll(Sort sort);

    List<Wishlish> findAllById(Iterable<Long> longs);

    <S extends Wishlish> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Wishlish> S saveAndFlush(S entity);

    <S extends Wishlish> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Wishlish> entities);

    void deleteAllInBatch(Iterable<Wishlish> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Wishlish getOne(Long aLong);

    @Deprecated
    Wishlish getById(Long aLong);

    Wishlish getReferenceById(Long aLong);

    <S extends Wishlish> List<S> findAll(Example<S> example);

    <S extends Wishlish> List<S> findAll(Example<S> example, Sort sort);

    Page<Wishlish> findAll(Pageable pageable);

    <S extends Wishlish> S save(S entity);

    Optional<Wishlish> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Wishlish entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Wishlish> entities);

    void deleteAll();

    <S extends Wishlish> Optional<S> findOne(Example<S> example);

    <S extends Wishlish> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Wishlish> long count(Example<S> example);

    <S extends Wishlish> boolean exists(Example<S> example);

    <S extends Wishlish, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
