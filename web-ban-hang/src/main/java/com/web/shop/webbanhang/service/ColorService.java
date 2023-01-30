package com.web.shop.webbanhang.service;

import com.web.shop.webbanhang.entity.Color;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ColorService {
    Page<Color> findAll(Integer pageNumber);

    List<Color> findAll();

    List<Color> findAll(Sort sort);

    List<Color> findAllById(Iterable<Long> longs);

    <S extends Color> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Color> S saveAndFlush(S entity);

    <S extends Color> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Color> entities);

    void deleteAllInBatch(Iterable<Color> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Color getOne(Long aLong);

    @Deprecated
    Color getById(Long aLong);

    Color getReferenceById(Long aLong);

    <S extends Color> List<S> findAll(Example<S> example);

    <S extends Color> List<S> findAll(Example<S> example, Sort sort);

    Page<Color> findAll(Pageable pageable);

    <S extends Color> S save(S entity);

    Optional<Color> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Color entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Color> entities);

    void deleteAll();

    <S extends Color> Optional<S> findOne(Example<S> example);

    <S extends Color> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Color> long count(Example<S> example);

    <S extends Color> boolean exists(Example<S> example);

    <S extends Color, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    Page<Color> findByColorNameContaining(String colorName, Pageable pageable);
}
