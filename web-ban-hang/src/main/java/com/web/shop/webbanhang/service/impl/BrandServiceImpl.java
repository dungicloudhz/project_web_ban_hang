package com.web.shop.webbanhang.service.impl;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.respository.BrandRepository;
import com.web.shop.webbanhang.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;


    @Override
    public Page<Brand> findAll(Integer pageNumber) {
        Sort sort = Sort.by("brandName").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);

        return brandRepository.findAll(pageable);
    }

    @Override
    public Page<Brand> findByBrandNameContaining(String brandName, Pageable pageable) {
        return brandRepository.findByBrandNameContaining(brandName, pageable);
    }

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Brand> findAll(Sort sort) {
        return brandRepository.findAll(sort);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public List<Brand> findAllById(Iterable<Long> longs) {
        return brandRepository.findAllById(longs);
    }

    @Override
    public <S extends Brand> List<S> saveAll(Iterable<S> entities) {
        return brandRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        brandRepository.flush();
    }

    @Override
    public <S extends Brand> S saveAndFlush(S entity) {
        return brandRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Brand> List<S> saveAllAndFlush(Iterable<S> entities) {
        return brandRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Brand> entities) {
        brandRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Brand> entities) {
        brandRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        brandRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        brandRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public Brand getOne(Long aLong) {
        return brandRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public Brand getById(Long aLong) {
        return brandRepository.getById(aLong);
    }

    @Override
    public Brand getReferenceById(Long aLong) {
        return brandRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> example) {
        return brandRepository.findAll(example);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> example, Sort sort) {
        return brandRepository.findAll(example, sort);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public <S extends Brand> S save(S entity) {
        return brandRepository.save(entity);
    }

    @Override
    public Optional<Brand> findById(Long aLong) {
        return brandRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return brandRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return brandRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        brandRepository.deleteById(aLong);
    }

    @Override
    public void delete(Brand entity) {
        brandRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        brandRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Brand> entities) {
        brandRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        brandRepository.deleteAll();
    }

    @Override
    public <S extends Brand> Optional<S> findOne(Example<S> example) {
        return brandRepository.findOne(example);
    }

    @Override
    public <S extends Brand> Page<S> findAll(Example<S> example, Pageable pageable) {
        return brandRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Brand> long count(Example<S> example) {
        return brandRepository.count(example);
    }

    @Override
    public <S extends Brand> boolean exists(Example<S> example) {
        return brandRepository.exists(example);
    }

    @Override
    public <S extends Brand, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return brandRepository.findBy(example, queryFunction);
    }
}
