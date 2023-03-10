package com.web.shop.webbanhang.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private Long productDetailId;
    private String productDetailName;
    private Integer quantity;
    private Float unitPrice;
    private String image;
    private String description;
    private Long discount;
    private Long memoryStorageName;
    private Float width;
    private Float height;
    private Float weight;
    private Integer style;
    private Float haute;

    private Long productId;

    private Long colorId;
    private String colorName;

    private Long memoryStorageId;

    private Long ramId;
    private Long sizeRam;
    private String brand;

//    private MultipartFile imageFile;

    private Boolean isEdit = false;
}
