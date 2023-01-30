package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "temporaryId")
    private Long temporaryId;

    @ManyToOne
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Transient
    public float getSubtotal() {
        return (float) (this.productDetail.getUnitPrice()*quantity);
    }
}
