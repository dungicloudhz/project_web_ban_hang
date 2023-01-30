package com.web.shop.webbanhang.service.impl;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.ProductDetail;
import com.web.shop.webbanhang.respository.ProductDetailRepository;
import com.web.shop.webbanhang.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public Page<ProductDetail> findByProductDetailNameContaining(String productDetailName, Pageable pageable) {
        return productDetailRepository.findByProductDetailNameContaining(productDetailName, pageable);
    }

    @Override
    public List<ProductDetail> findByOutstanding(Boolean outstanding) {
        return productDetailRepository.findByOutstanding(outstanding);
    }

    @Override
    public List<ProductDetail> findByMostRecent(Boolean mostRecent) {
        return productDetailRepository.findByMostRecent(mostRecent);
    }

    @Override
    public List<ProductDetail> findByBestseller(Boolean bestseller) {
        return productDetailRepository.findByBestseller(bestseller);
    }

    @Override
    public List<ProductDetail> findByDiscountCurrent(Boolean discountCurrent) {
        return productDetailRepository.findByDiscountCurrent(discountCurrent);
    }

    @Override
    public Page<ProductDetail> findByOutstanding(Boolean outstanding, Pageable pageable) {
        return productDetailRepository.findByOutstanding(outstanding, pageable);
    }

    @Override
    public Page<ProductDetail> findByMostRecent(Boolean mostRecent, Pageable pageable) {
        return productDetailRepository.findByMostRecent(mostRecent, pageable);
    }

    @Override
    public Page<ProductDetail> findByBestseller(Boolean bestseller, Pageable pageable) {
        return productDetailRepository.findByBestseller(bestseller, pageable);
    }

    @Override
    public Page<ProductDetail> findByDiscountCurrent(Boolean discountCurrent, Pageable pageable) {
        return productDetailRepository.findByDiscountCurrent(discountCurrent, pageable);
    }

    @Override
    public List<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId) {
        return productDetailRepository.findProductDetailByProduct_Category_CategoryId(categoryId);
    }

    @Override
    public Page<ProductDetail> findProductDetailByProduct_Category_CategoryId(Long categoryId, Pageable pageable) {
        return productDetailRepository.findProductDetailByProduct_Category_CategoryId(categoryId, pageable);
    }

    @Override
    public List<ProductDetail> findProductDetailByProduct_ProductIdAndMemoryStorage_MemoryStorageId(Long productId, Long memoryStorageId) {
        return productDetailRepository.findProductDetailByProduct_ProductIdAndMemoryStorage_MemoryStorageId(productId, memoryStorageId);
    }

    @Override
    public List<ProductDetail> findByProduct_ProductId(Long productId) {
        return productDetailRepository.findByProduct_ProductId(productId);
    }

    @Override
    public Page<ProductDetail> findByProduct_ProductId(Long productId, Pageable pageable) {
        return productDetailRepository.findByProduct_ProductId(productId, pageable);
    }

    @Override
    public Page<ProductDetail> findAll(Integer pageNumber) {
        Sort sort = Sort.by("product_productName").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,9,sort);
        return productDetailRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDetail> findAll(Integer pageNumber,String sortField,String sortDir) {
        Sort sort = Sort.by(sortField);
        if(sortDir.equals("asc")){
            sort.ascending();
        }
        if(sortDir.equals("desc")){
            sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNumber - 1,9,sort);
        return productDetailRepository.findAll(pageable);
    }

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    @Override
    public List<ProductDetail> findAll(Sort sort) {
        return productDetailRepository.findAll(sort);
    }

    @Override
    public List<ProductDetail> findAllById(Iterable<Long> longs) {
        return productDetailRepository.findAllById(longs);
    }

    @Override
    public <S extends ProductDetail> List<S> saveAll(Iterable<S> entities) {
        return productDetailRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        productDetailRepository.flush();
    }

    @Override
    public <S extends ProductDetail> S saveAndFlush(S entity) {
        return productDetailRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends ProductDetail> List<S> saveAllAndFlush(Iterable<S> entities) {
        return productDetailRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<ProductDetail> entities) {
        productDetailRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<ProductDetail> entities) {
        productDetailRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        productDetailRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        productDetailRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public ProductDetail getOne(Long aLong) {
        return productDetailRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public ProductDetail getById(Long aLong) {
        return productDetailRepository.getById(aLong);
    }

    @Override
    public ProductDetail getReferenceById(Long aLong) {
        return productDetailRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends ProductDetail> List<S> findAll(Example<S> example) {
        return productDetailRepository.findAll(example);
    }

    @Override
    public <S extends ProductDetail> List<S> findAll(Example<S> example, Sort sort) {
        return productDetailRepository.findAll(example, sort);
    }

    @Override
    public Page<ProductDetail> findAll(Pageable pageable) {
        return productDetailRepository.findAll(pageable);
    }

    @Override
    public <S extends ProductDetail> S save(S entity) {
        return productDetailRepository.save(entity);
    }

    @Override
    public Optional<ProductDetail> findById(Long aLong) {
        return productDetailRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return productDetailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return productDetailRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        productDetailRepository.deleteById(aLong);
    }

    @Override
    public void delete(ProductDetail entity) {
        productDetailRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        productDetailRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends ProductDetail> entities) {
        productDetailRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        productDetailRepository.deleteAll();
    }

    @Override
    public <S extends ProductDetail> Optional<S> findOne(Example<S> example) {
        return productDetailRepository.findOne(example);
    }

    @Override
    public <S extends ProductDetail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return productDetailRepository.findAll(example, pageable);
    }

    @Override
    public <S extends ProductDetail> long count(Example<S> example) {
        return productDetailRepository.count(example);
    }

    @Override
    public <S extends ProductDetail> boolean exists(Example<S> example) {
        return productDetailRepository.exists(example);
    }

    @Override
    public <S extends ProductDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return productDetailRepository.findBy(example, queryFunction);
    }
}
