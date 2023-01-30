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
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Long categoryId;

    @Column(name = "CategoryName",length = 50,columnDefinition = "nvarchar(50) not null")
    private String name;

    @Column(name = "Slug",length = 50,columnDefinition = "nvarchar(50) not null")
    private String slug;

    @Column(name = "Description",length = 100,columnDefinition = "nvarchar(100) not null")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
