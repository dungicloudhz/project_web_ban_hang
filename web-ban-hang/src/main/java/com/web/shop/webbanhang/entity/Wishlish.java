package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wishlishs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlishId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;
}
