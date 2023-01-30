package com.web.shop.webbanhang.service;

import com.web.shop.webbanhang.entity.ProductDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProductDetailService {


    Page<ProductDetail> findByProductDetailNameContaining(String productDetailName, Pageable pageable);

    List<ProductDetail> findByOutstanding(Boolean outstanding);

    List<ProductDetail> findByMostRecent(Boolean mostRecent);

    List<ProductDetail> findByBestseller(Boolean bestseller);

    List<ProductDetail> findByDiscountCurrent(Boolean discountCurrent);

    Page<ProductDetail> findByOutstanding(Boolean outstanding, Pageable pageable);

    Page<ProductDetail> findByMostRecent(Boolean mostRecent, Pageable pageable);

    Page<ProductDetail> findByBestseller(Boolean bestseller, Pageable pageable);

    Page<ProductDetail> findByDiscountCurrent(Boolean discountCurrent, Pageable pageable);

    List<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId);

    Page<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId, Pageable pageable);

    List<ProductDetail> findProductDetailByProduct_ProductIdAndMemoryStorage_MemoryStorageId(Long productId, Long memoryStorageId);

    List<ProductDetail> findByProduct_ProductId(Long productId);

    Page<ProductDetail> findByProduct_ProductId(Long productId, Pageable pageable);

    Page<ProductDetail> findAll(Integer pageNumber);

    Page<ProductDetail> findAll(Integer pageNumber, String sortField, String sortDir);

    List<ProductDetail> findAll();

    List<ProductDetail> findAll(Sort sort);

    List<ProductDetail> findAllById(Iterable<Long> longs);

    <S extends ProductDetail> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends ProductDetail> S saveAndFlush(S entity);

    <S extends ProductDetail> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<ProductDetail> entities);

    void deleteAllInBatch(Iterable<ProductDetail> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    ProductDetail getOne(Long aLong);

    @Deprecated
    ProductDetail getById(Long aLong);

    ProductDetail getReferenceById(Long aLong);

    <S extends ProductDetail> List<S> findAll(Example<S> example);

    <S extends ProductDetail> List<S> findAll(Example<S> example, Sort sort);

    Page<ProductDetail> findAll(Pageable pageable);

    <S extends ProductDetail> S save(S entity);

    Optional<ProductDetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(ProductDetail entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends ProductDetail> entities);

    void deleteAll();

    <S extends ProductDetail> Optional<S> findOne(Example<S> example);

    <S extends ProductDetail> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends ProductDetail> long count(Example<S> example);

    <S extends ProductDetail> boolean exists(Example<S> example);

    <S extends ProductDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

}
