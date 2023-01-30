package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.Order;
import com.web.shop.webbanhang.entity.OrderDetail;
import com.web.shop.webbanhang.entity.ProductDetail;
import com.web.shop.webbanhang.entity.User;
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
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductDetailService productDetailService;


    @Override
    public List<OrderDetail> findByOrder_OrderId(Long orderId) {
        return orderDetailRepository.findByOrder_OrderId(orderId);
    }

    @Override
    @Modifying
    @Query("UPDATE OrderDetail c SET c.temporaryId = ?1 WHERE c.order.orderId = ?2")
    public void updateTemporaryId(Long temporaryId,Long orderId) {
        orderDetailRepository.updateTemporaryId(temporaryId,orderId);
    }

    @Override
    @Modifying
    @Query("UPDATE OrderDetail c SET c.order.orderId = ?1 WHERE c.temporaryId = ?2")
    public void updateOrder(Long orderId, Long temporaryId) {
        orderDetailRepository.updateOrder(orderId, temporaryId);
    }

    @Override
    public List<OrderDetail> findByTemporaryId(Long temporaryId) {
        return orderDetailRepository.findByTemporaryId(temporaryId);
    }

    @Override
    public OrderDetail findByTemporaryIdAndProductDetail(Long temporaryId, ProductDetail productDetails) {
        return orderDetailRepository.findByTemporaryIdAndProductDetail(temporaryId, productDetails);
    }

    @Override
    @Modifying
    @Query("UPDATE OrderDetail c SET c.quantity = ?1 WHERE c.productDetail.productDetailId = ?2 AND c.temporaryId = ?3")
    public void updateQuantity(Integer quantity, Long productDetailId, Long temporaryId) {
        orderDetailRepository.updateQuantity(quantity, productDetailId, temporaryId);
    }

    @Override
    @Modifying
    @Query("DELETE FROM OrderDetail c WHERE c.temporaryId = ?1 AND c.productDetail.productDetailId = ?2")
    public void deleteByOrderAndProduct(Long temporaryId, Long productDetailId) {
        orderDetailRepository.deleteByOrderAndProduct(temporaryId, productDetailId);
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public List<OrderDetail> findAll(Sort sort) {
        return orderDetailRepository.findAll(sort);
    }

    @Override
    public List<OrderDetail> findAllById(Iterable<Long> longs) {
        return orderDetailRepository.findAllById(longs);
    }

    @Override
    public <S extends OrderDetail> List<S> saveAll(Iterable<S> entities) {
        return orderDetailRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        orderDetailRepository.flush();
    }

    @Override
    public <S extends OrderDetail> S saveAndFlush(S entity) {
        return orderDetailRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities) {
        return orderDetailRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<OrderDetail> entities) {
        orderDetailRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<OrderDetail> entities) {
        orderDetailRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        orderDetailRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        orderDetailRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public OrderDetail getOne(Long aLong) {
        return orderDetailRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public OrderDetail getById(Long aLong) {
        return orderDetailRepository.getById(aLong);
    }

    @Override
    public OrderDetail getReferenceById(Long aLong) {
        return orderDetailRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends OrderDetail> List<S> findAll(Example<S> example) {
        return orderDetailRepository.findAll(example);
    }

    @Override
    public <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort) {
        return orderDetailRepository.findAll(example, sort);
    }

    @Override
    public Page<OrderDetail> findAll(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public <S extends OrderDetail> S save(S entity) {
        return orderDetailRepository.save(entity);
    }

    @Override
    public Optional<OrderDetail> findById(Long aLong) {
        return orderDetailRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return orderDetailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return orderDetailRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        orderDetailRepository.deleteById(aLong);
    }

    @Override
    public void delete(OrderDetail entity) {
        orderDetailRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        orderDetailRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends OrderDetail> entities) {
        orderDetailRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        orderDetailRepository.deleteAll();
    }

    @Override
    public <S extends OrderDetail> Optional<S> findOne(Example<S> example) {
        return orderDetailRepository.findOne(example);
    }

    @Override
    public <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return orderDetailRepository.findAll(example, pageable);
    }

    @Override
    public <S extends OrderDetail> long count(Example<S> example) {
        return orderDetailRepository.count(example);
    }

    @Override
    public <S extends OrderDetail> boolean exists(Example<S> example) {
        return orderDetailRepository.exists(example);
    }

    @Override
    public <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderDetailRepository.findBy(example, queryFunction);
    }

    @Override
    public List<OrderDetail> listOrderDetails(Long temporaryId){
        return orderDetailRepository.findByTemporaryId(temporaryId);
    }

    @Override
    public Integer addProduct(Long productDatailId, Integer quantity, Long temporaryId) {
        Integer addedQuantity = quantity;

        ProductDetail product = productDetailService.findById(productDatailId).get();

        OrderDetail cartItem = orderDetailRepository.findByTemporaryIdAndProductDetail(temporaryId, product);

        if(cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new OrderDetail();
            cartItem.setQuantity(quantity);
            cartItem.setTemporaryId(temporaryId);
            cartItem.setProductDetail(product);
        }
        orderDetailRepository.save(cartItem);
        return addedQuantity;
    }

    @Override
    public float updateQuantity(Long productId, Integer quantity, Long temporaryId) {
        orderDetailRepository.updateQuantity(quantity, productId, temporaryId);
        ProductDetail product= productDetailService.findById(productId).get();
        float subtotal = (float) (product.getUnitPrice() * quantity);

        return subtotal;

    }

    @Override
    public void removeProduct(Long productId, Long temporaryId) {
        orderDetailRepository.deleteByOrderAndProduct(temporaryId, productId);
    }
}
