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
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;
    @Column(name = "colorName",columnDefinition = "nvarchar(50) not null")
    private String colorName;
    @Column(name = "colorCode",columnDefinition = "nvarchar(10) not null")
    private String colorCode;
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "color")
    private Set<ProductDetail> productDetails;
}
