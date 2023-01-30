package com.web.shop.webbanhang.service;

import com.web.shop.webbanhang.entity.Ram;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RamService {

    Page<Ram> findAll(Integer pageNumber);

    List<Ram> findAll(Sort sort);

    List<Ram> findAll();

    List<Ram> findAllById(Iterable<Long> longs);

    <S extends Ram> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Ram> S saveAndFlush(S entity);

    <S extends Ram> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Ram> entities);

    void deleteAllInBatch(Iterable<Ram> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Ram getOne(Long aLong);

    @Deprecated
    Ram getById(Long aLong);

    Ram getReferenceById(Long aLong);

    <S extends Ram> List<S> findAll(Example<S> example);

    <S extends Ram> List<S> findAll(Example<S> example, Sort sort);

    Page<Ram> findAll(Pageable pageable);

    <S extends Ram> S save(S entity);

    Optional<Ram> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Ram entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Ram> entities);

    void deleteAll();

    <S extends Ram> Optional<S> findOne(Example<S> example);

    <S extends Ram> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Ram> long count(Example<S> example);

    <S extends Ram> boolean exists(Example<S> example);

    <S extends Ram, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
