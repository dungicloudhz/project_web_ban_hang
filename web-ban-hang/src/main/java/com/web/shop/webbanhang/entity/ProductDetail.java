package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productDetail")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDetailId;
    @Column(name = "productDetailName")
    private String productDetailName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unitPrice")
    private Float unitPrice;
    @Column(length = 200)
    private String image;
    @Column(name = "description",columnDefinition = "nvarchar(255) not null")
    private String description;
    @Column(name = "discount")
    private Long discount;
    @Column(name = "width")
    private Float width;
    @Column(name = "height")
    private Float height;
    @Column(name = "haute")
    private Float haute;
    @Column(name = "weight")
    private Float weight;

    @Column(name = "outstanding")
    private Boolean outstanding;

    @Column(name = "most_recent")
    private Boolean mostRecent;

    @Column(name = "bestseller")
    private Boolean bestseller;

    @Column(name = "discount_current")
    private Boolean discountCurrent;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "colorId")
    private Color color;

    @OneToMany(mappedBy = "productDetail")
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "memoryStorageId")
    private MemoryStorage memoryStorage;

    @ManyToOne
    @JoinColumn(name = "ramId")
    private Ram ram;

    @OneToMany(mappedBy = "productDetail")
    private Set<Wishlish> wishlishes;

}
