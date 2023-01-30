package com.web.shop.webbanhang.service.impl;

import com.web.shop.webbanhang.entity.Ram;
import com.web.shop.webbanhang.respository.RamRepository;
import com.web.shop.webbanhang.service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class RamServiceImpl implements RamService {
    @Autowired
    RamRepository ramRepository;


    @Override
    public Page<Ram> findAll(Integer pageNumber) {
        Sort sort = Sort.by("ramId").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);

        return ramRepository.findAll(pageable);
    }


    public RamServiceImpl(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }


    @Override
    public List<Ram> findAll(Sort sort) {
        return ramRepository.findAll(sort);
    }

    @Override
    public List<Ram> findAll() {
        return ramRepository.findAll();
    }

    @Override
    public List<Ram> findAllById(Iterable<Long> longs) {
        return ramRepository.findAllById(longs);
    }

    @Override
    public <S extends Ram> List<S> saveAll(Iterable<S> entities) {
        return ramRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        ramRepository.flush();
    }

    @Override
    public <S extends Ram> S saveAndFlush(S entity) {
        return ramRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Ram> List<S> saveAllAndFlush(Iterable<S> entities) {
        return ramRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Ram> entities) {
        ramRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Ram> entities) {
        ramRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        ramRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        ramRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public Ram getOne(Long aLong) {
        return ramRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public Ram getById(Long aLong) {
        return ramRepository.getById(aLong);
    }

    @Override
    public Ram getReferenceById(Long aLong) {
        return ramRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Ram> List<S> findAll(Example<S> example) {
        return ramRepository.findAll(example);
    }

    @Override
    public <S extends Ram> List<S> findAll(Example<S> example, Sort sort) {
        return ramRepository.findAll(example, sort);
    }

    @Override
    public Page<Ram> findAll(Pageable pageable) {
        return ramRepository.findAll(pageable);
    }

    @Override
    public <S extends Ram> S save(S entity) {
        return ramRepository.save(entity);
    }

    @Override
    public Optional<Ram> findById(Long aLong) {
        return ramRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return ramRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return ramRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        ramRepository.deleteById(aLong);
    }

    @Override
    public void delete(Ram entity) {
        ramRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        ramRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Ram> entities) {
        ramRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        ramRepository.deleteAll();
    }

    @Override
    public <S extends Ram> Optional<S> findOne(Example<S> example) {
        return ramRepository.findOne(example);
    }

    @Override
    public <S extends Ram> Page<S> findAll(Example<S> example, Pageable pageable) {
        return ramRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Ram> long count(Example<S> example) {
        return ramRepository.count(example);
    }

    @Override
    public <S extends Ram> boolean exists(Example<S> example) {
        return ramRepository.exists(example);
    }

    @Override
    public <S extends Ram, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return ramRepository.findBy(example, queryFunction);
    }
}
