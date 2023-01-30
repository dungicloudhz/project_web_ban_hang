package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RamDto {

    private Long ramId;

    private Long sizeRam;

    private Boolean isEdit = false;
}
