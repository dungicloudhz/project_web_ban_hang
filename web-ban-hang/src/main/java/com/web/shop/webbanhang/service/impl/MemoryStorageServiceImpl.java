package com.web.shop.webbanhang.service.impl;

import com.web.shop.webbanhang.entity.MemoryStorage;
import com.web.shop.webbanhang.entity.MemoryStorage;
import com.web.shop.webbanhang.respository.MemoryStorageRepository;
import com.web.shop.webbanhang.respository.MemoryStorageRepository;
import com.web.shop.webbanhang.service.MemoryStorageService;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MemoryStorageServiceImpl implements MemoryStorageService {

    MemoryStorageRepository memoryStorageRepository;

    public MemoryStorageServiceImpl(MemoryStorageRepository memoryStorageRepository){
        this.memoryStorageRepository = memoryStorageRepository;
    }

    @Override
    public Page<MemoryStorage> findAll(Integer pageNumber) {
        Sort sort = Sort.by("memoryStorageId").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);

        return memoryStorageRepository.findAll(pageable);
    }

    @Override
    public List<MemoryStorage> findAll() {
        return memoryStorageRepository.findAll();
    }

    @Override
    public List<MemoryStorage> findAll(Sort sort) {
        return memoryStorageRepository.findAll(sort);
    }

    @Override
    public List<MemoryStorage> findAllById(Iterable<Long> longs) {
        return memoryStorageRepository.findAllById(longs);
    }

    @Override
    public <S extends MemoryStorage> List<S> saveAll(Iterable<S> entities) {
        return memoryStorageRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        memoryStorageRepository.flush();
    }

    @Override
    public <S extends MemoryStorage> S saveAndFlush(S entity) {
        return memoryStorageRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends MemoryStorage> List<S> saveAllAndFlush(Iterable<S> entities) {
        return memoryStorageRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<MemoryStorage> entities) {
        memoryStorageRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<MemoryStorage> entities) {
        memoryStorageRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        memoryStorageRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        memoryStorageRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public MemoryStorage getOne(Long aLong) {
        return memoryStorageRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public MemoryStorage getById(Long aLong) {
        return memoryStorageRepository.getById(aLong);
    }

    @Override
    public MemoryStorage getReferenceById(Long aLong) {
        return memoryStorageRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends MemoryStorage> List<S> findAll(Example<S> example) {
        return memoryStorageRepository.findAll(example);
    }

    @Override
    public <S extends MemoryStorage> List<S> findAll(Example<S> example, Sort sort) {
        return memoryStorageRepository.findAll(example, sort);
    }

    @Override
    public Page<MemoryStorage> findAll(Pageable pageable) {
        return memoryStorageRepository.findAll(pageable);
    }

    @Override
    public <S extends MemoryStorage> S save(S entity) {
        return memoryStorageRepository.save(entity);
    }

    @Override
    public Optional<MemoryStorage> findById(Long aLong) {
        return memoryStorageRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return memoryStorageRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return memoryStorageRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        memoryStorageRepository.deleteById(aLong);
    }

    @Override
    public void delete(MemoryStorage entity) {
        memoryStorageRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        memoryStorageRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends MemoryStorage> entities) {
        memoryStorageRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        memoryStorageRepository.deleteAll();
    }

    @Override
    public <S extends MemoryStorage> Optional<S> findOne(Example<S> example) {
        return memoryStorageRepository.findOne(example);
    }

    @Override
    public <S extends MemoryStorage> Page<S> findAll(Example<S> example, Pageable pageable) {
        return memoryStorageRepository.findAll(example, pageable);
    }

    @Override
    public <S extends MemoryStorage> long count(Example<S> example) {
        return memoryStorageRepository.count(example);
    }

    @Override
    public <S extends MemoryStorage> boolean exists(Example<S> example) {
        return memoryStorageRepository.exists(example);
    }

    @Override
    public <S extends MemoryStorage, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return memoryStorageRepository.findBy(example, queryFunction);
    }

    @Override
    public Page<MemoryStorage> findByMemoryStorageNameContaining(String memoryStorage, Pageable pageable) {
        return memoryStorageRepository.findByMemoryStorageNameContaining(memoryStorage,pageable);
    }

}
