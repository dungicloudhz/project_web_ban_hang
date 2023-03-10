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
@Table(name = "memoryStorages")
public class MemoryStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoryStorageId;

    @Column(name = "memoryStorageName")
    private Long memoryStorageName;

    @OneToMany(mappedBy = "memoryStorage")
    private Set<ProductDetail> productDetails;
}
