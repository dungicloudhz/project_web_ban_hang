package com.web.shop.webbanhang.service;

import com.web.shop.webbanhang.entity.MemoryStorage;
import com.web.shop.webbanhang.entity.MemoryStorage;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface MemoryStorageService {
    Page<MemoryStorage> findAll(Integer pageNumber);

    List<MemoryStorage> findAll();

    List<MemoryStorage> findAll(Sort sort);

    List<MemoryStorage> findAllById(Iterable<Long> longs);

    <S extends MemoryStorage> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends MemoryStorage> S saveAndFlush(S entity);

    <S extends MemoryStorage> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<MemoryStorage> entities);

    void deleteAllInBatch(Iterable<MemoryStorage> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    MemoryStorage getOne(Long aLong);

    @Deprecated
    MemoryStorage getById(Long aLong);

    MemoryStorage getReferenceById(Long aLong);

    <S extends MemoryStorage> List<S> findAll(Example<S> example);

    <S extends MemoryStorage> List<S> findAll(Example<S> example, Sort sort);

    Page<MemoryStorage> findAll(Pageable pageable);

    <S extends MemoryStorage> S save(S entity);

    Optional<MemoryStorage> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(MemoryStorage entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends MemoryStorage> entities);

    void deleteAll();

    <S extends MemoryStorage> Optional<S> findOne(Example<S> example);

    <S extends MemoryStorage> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends MemoryStorage> long count(Example<S> example);

    <S extends MemoryStorage> boolean exists(Example<S> example);

    <S extends MemoryStorage, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    Page<MemoryStorage> findByMemoryStorageNameContaining(String memoryStorageName, Pageable pageable);
}
