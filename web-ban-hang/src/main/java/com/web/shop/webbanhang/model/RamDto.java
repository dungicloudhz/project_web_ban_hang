package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RamDto {

    private Long ramId;

    @NotNull
    private Long sizeRam;

    private Boolean isEdit = false;
}
