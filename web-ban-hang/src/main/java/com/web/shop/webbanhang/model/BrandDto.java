package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    private Long brandId;
    private String brandName;
    private String image;
    private String description;
    private Boolean isEdit = false;
}
