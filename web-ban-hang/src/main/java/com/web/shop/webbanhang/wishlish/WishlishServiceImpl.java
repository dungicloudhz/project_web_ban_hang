package com.web.shop.webbanhang.wishlish;

import com.web.shop.webbanhang.entity.OrderDetail;
import com.web.shop.webbanhang.entity.ProductDetail;
import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.entity.Wishlish;
import com.web.shop.webbanhang.security.UserService;
import com.web.shop.webbanhang.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class WishlishServiceImpl implements WishlishService{
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private UserService userService;

    @Override
    public Wishlish findByUser_UserIdAndProductDetail_ProductDetailId(Long userId, Long productDetailId) {
        return wishlistRepository.findByUser_UserIdAndProductDetail_ProductDetailId(userId, productDetailId);
    }

    @Override
    public Integer addProduct(Long productDatailId, Long userId) {

        ProductDetail product = productDetailService.findById(productDatailId).get();

        User user = userService.findById(userId).get();

        Wishlish cartItem = wishlistRepository.findByUser_UserIdAndProductDetail_ProductDetailId(userId,productDatailId);

        if(cartItem != null) {
            cartItem.setUser(user);
            cartItem.setProductDetail(product);
        } else {
            cartItem = new Wishlish();
            cartItem.setUser(user);
            cartItem.setProductDetail(product);
        }
        wishlistRepository.save(cartItem);
        return 1;
    }


    @Override
    public List<Wishlish> findByUser_UserId(Long userId) {
        return wishlistRepository.findByUser_UserId(userId);
    }

    @Override
    @Modifying
    @Query("DELETE FROM Wishlish w WHERE w.user.userId = ?1 AND w.productDetail.productDetailId = ?2")
    public void deleteByUser_UserIdAndProductDetail_ProductDetailId(Long userId, Long productDetailId) {
        wishlistRepository.deleteByUser_UserIdAndProductDetail_ProductDetailId(userId, productDetailId);
    }

    @Override
    public List<Wishlish> findAll() {
        return wishlistRepository.findAll();
    }

    @Override
    public List<Wishlish> findAll(Sort sort) {
        return wishlistRepository.findAll(sort);
    }

    @Override
    public List<Wishlish> findAllById(Iterable<Long> longs) {
        return wishlistRepository.findAllById(longs);
    }

    @Override
    public <S extends Wishlish> List<S> saveAll(Iterable<S> entities) {
        return wishlistRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        wishlistRepository.flush();
    }

    @Override
    public <S extends Wishlish> S saveAndFlush(S entity) {
        return wishlistRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Wishlish> List<S> saveAllAndFlush(Iterable<S> entities) {
        return wishlistRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Wishlish> entities) {
        wishlistRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Wishlish> entities) {
        wishlistRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        wishlistRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        wishlistRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public Wishlish getOne(Long aLong) {
        return wishlistRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public Wishlish getById(Long aLong) {
        return wishlistRepository.getById(aLong);
    }

    @Override
    public Wishlish getReferenceById(Long aLong) {
        return wishlistRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Wishlish> List<S> findAll(Example<S> example) {
        return wishlistRepository.findAll(example);
    }

    @Override
    public <S extends Wishlish> List<S> findAll(Example<S> example, Sort sort) {
        return wishlistRepository.findAll(example, sort);
    }

    @Override
    public Page<Wishlish> findAll(Pageable pageable) {
        return wishlistRepository.findAll(pageable);
    }

    @Override
    public <S extends Wishlish> S save(S entity) {
        return wishlistRepository.save(entity);
    }

    @Override
    public Optional<Wishlish> findById(Long aLong) {
        return wishlistRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return wishlistRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return wishlistRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        wishlistRepository.deleteById(aLong);
    }

    @Override
    public void delete(Wishlish entity) {
        wishlistRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        wishlistRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Wishlish> entities) {
        wishlistRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        wishlistRepository.deleteAll();
    }

    @Override
    public <S extends Wishlish> Optional<S> findOne(Example<S> example) {
        return wishlistRepository.findOne(example);
    }

    @Override
    public <S extends Wishlish> Page<S> findAll(Example<S> example, Pageable pageable) {
        return wishlistRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Wishlish> long count(Example<S> example) {
        return wishlistRepository.count(example);
    }

    @Override
    public <S extends Wishlish> boolean exists(Example<S> example) {
        return wishlistRepository.exists(example);
    }

    @Override
    public <S extends Wishlish, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return wishlistRepository.findBy(example, queryFunction);
    }
}
