package com.web.shop.webbanhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "productName",columnDefinition = "nvarchar(100) not null")
    private String productName;
    @Column(name = "status",columnDefinition = "int")
    private Boolean status;
    @Column(length = 200)
    private String image;

    @Column(name = "description",columnDefinition = "nvarchar(100) not null")
    private String description;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductDetail> productDetails;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;
}
