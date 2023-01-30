package com.web.shop.webbanhang.service.impl;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.Color;
import com.web.shop.webbanhang.respository.ColorRepository;
import com.web.shop.webbanhang.service.ColorService;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ColorServiceImpl implements ColorService {
    ColorRepository colorRepository;

    @Override
    public Page<Color> findAll(Integer pageNumber) {
        Sort sort = Sort.by("colorName").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);

        return colorRepository.findAll(pageable);
    }

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public List<Color> findAll(Sort sort) {
        return colorRepository.findAll(sort);
    }

    @Override
    public List<Color> findAllById(Iterable<Long> longs) {
        return colorRepository.findAllById(longs);
    }

    @Override
    public <S extends Color> List<S> saveAll(Iterable<S> entities) {
        return colorRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        colorRepository.flush();
    }

    @Override
    public <S extends Color> S saveAndFlush(S entity) {
        return colorRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Color> List<S> saveAllAndFlush(Iterable<S> entities) {
        return colorRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Color> entities) {
        colorRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Color> entities) {
        colorRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        colorRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        colorRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public Color getOne(Long aLong) {
        return colorRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public Color getById(Long aLong) {
        return colorRepository.getById(aLong);
    }

    @Override
    public Color getReferenceById(Long aLong) {
        return colorRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Color> List<S> findAll(Example<S> example) {
        return colorRepository.findAll(example);
    }

    @Override
    public <S extends Color> List<S> findAll(Example<S> example, Sort sort) {
        return colorRepository.findAll(example, sort);
    }

    @Override
    public Page<Color> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

    @Override
    public <S extends Color> S save(S entity) {
        return colorRepository.save(entity);
    }

    @Override
    public Optional<Color> findById(Long aLong) {
        return colorRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return colorRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return colorRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        colorRepository.deleteById(aLong);
    }

    @Override
    public void delete(Color entity) {
        colorRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        colorRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Color> entities) {
        colorRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        colorRepository.deleteAll();
    }

    @Override
    public <S extends Color> Optional<S> findOne(Example<S> example) {
        return colorRepository.findOne(example);
    }

    @Override
    public <S extends Color> Page<S> findAll(Example<S> example, Pageable pageable) {
        return colorRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Color> long count(Example<S> example) {
        return colorRepository.count(example);
    }

    @Override
    public <S extends Color> boolean exists(Example<S> example) {
        return colorRepository.exists(example);
    }

    @Override
    public <S extends Color, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return colorRepository.findBy(example, queryFunction);
    }

    @Override
    public Page<Color> findByColorNameContaining(String colorName, Pageable pageable) {
        return colorRepository.findByColorNameContaining(colorName, pageable);
    }
}
