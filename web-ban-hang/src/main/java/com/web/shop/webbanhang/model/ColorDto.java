package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {
    private Long colorId;
    @NotEmpty
    private String colorName;
    @NotEmpty
    private String colorCode;

    private String image;
    private Boolean isEdit = false;
}
