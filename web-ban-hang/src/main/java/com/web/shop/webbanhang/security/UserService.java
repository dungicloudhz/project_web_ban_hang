package com.web.shop.webbanhang.security;

import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.service.impl.UserNotFoundException;
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

public interface UserService {
    int MAX_FAILED_ATTEMPTS = 3;
    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    Page<User> findByUsernameContaining(String username, Pageable pageable);

    Page<User> findAll(Integer pageNumber);

    User findByUsername(String userName);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(String username);

    void lock(User user);

    boolean unlockWhenTimeExpired(User user);

    @Modifying
    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    void updateFailedAttempts(int failAttempts, String username);

    @Query("SELECT u FROM User u where u.username = :username")
    User getUserByUsername(String username);

    List<User> findAll();

    List<User> findAll(Sort sort);

    List<User> findAllById(Iterable<Long> longs);

    <S extends User> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends User> S saveAndFlush(S entity);

    <S extends User> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<User> entities);

    void deleteAllInBatch(Iterable<User> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    User getOne(Long aLong);

    @Deprecated
    User getById(Long aLong);

    User getReferenceById(Long aLong);

    <S extends User> List<S> findAll(Example<S> example);

    <S extends User> List<S> findAll(Example<S> example, Sort sort);

    Page<User> findAll(Pageable pageable);

    <S extends User> S save(S entity);

    Optional<User> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(User entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends User> entities);

    void deleteAll();

    <S extends User> Optional<S> findOne(Example<S> example);

    <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends User> long count(Example<S> example);

    <S extends User> boolean exists(Example<S> example);

    <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

}
